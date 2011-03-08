
package novoda.rest.services;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import novoda.rest.command.Command;
import novoda.rest.database.Persister;
import novoda.rest.exception.ParserException;
import novoda.rest.net.AndroidHttpClient;
import novoda.rest.net.UserAgent;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.json.JsonNodeParser;
import novoda.rest.system.IOCLoader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;
import android.util.TimingLogger;

public abstract class HttpExecutorService extends Service {
    private static final String TAG = HttpService.class.getSimpleName();

    /*
     * In order to enable ensure you run "setprop log.tag.HttpService VERBOSE"
     * in adb
     */
    private TimingLogger logger = new TimingLogger(TAG, "lifecycle");

    private static final int CORE_POOL_SIZE = 5;

    private static final int MAXIMUM_POOL_SIZE = 128;

    private static final int KEEP_ALIVE = 10;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "HttpExecutorService #" + mCount.getAndIncrement());
        }
    };

    private ResultReceiver receiver;

    private AndroidHttpClient client;

    private static final int STATUS_FINISHED = 0;

    private static final int STATUS_ERROR = 1;

    private static final String USER_AGENT = new UserAgent.Builder().with("RESTProvider").build();

    @Override
    public void onCreate() {
        if (client == null) {
            client = getHttpClient();
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                client.enableCurlLogging(TAG, Log.VERBOSE);
            }
        }
        super.onCreate();
    }

    protected void onFinishCall() {

        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            logger.dumpToLog();
        }
        if (receiver != null)
            receiver.send(STATUS_FINISHED, Bundle.EMPTY);
    }

    /* package */AndroidHttpClient getHttpClient() {
        return AndroidHttpClient.newInstance(USER_AGENT, getBaseContext());
    }

    /* package */void setHttpClient(AndroidHttpClient client) {
        this.client = client;
    }

    protected void onPreCall(HttpUriRequest request, HttpContext context) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            logger.reset(TAG, "lifecycle");
            logger.addSplit("on pre call: " + request.getRequestLine().toString());
        }
    }

    protected void onPostCall(HttpResponse response, HttpContext context) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            logger.addSplit("on post call: " + response.getStatusLine().toString());
        }
    }

    protected void onThrowable(Exception e) {
        if (Log.isLoggable(TAG, Log.ERROR)) {
            Log.e(TAG, "an error occured against intent: ", e);
            Log.e(TAG, e.getMessage());
        }
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.e(TAG, "full stack trace:", e);
        }

        if (receiver != null) {
            final Bundle bundle = new Bundle();
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
        }
    }

    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<Runnable>(10);

    private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);

    private static final int MESSAGE_RECEIVED_REQUEST = 0x1;

    private static final int MESSAGE_TIMEOUT_AFTER_FIRST_CALL = 0x2;

    // 5 Minutes
    public static final long SERVICE_LIFESPAN = 1000 * 60;

    private LifecycleHandler handler = new LifecycleHandler();

    private class LifecycleHandler extends Handler {
        private long lastCall = 0L;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVED_REQUEST:
                    lastCall = System.currentTimeMillis();

                    sendEmptyMessageDelayed(MESSAGE_TIMEOUT_AFTER_FIRST_CALL, SERVICE_LIFESPAN);
                    break;
                case MESSAGE_TIMEOUT_AFTER_FIRST_CALL:
                    // If we have not received another call in the last 5
                    // minutes
                    if (System.currentTimeMillis() - lastCall > SERVICE_LIFESPAN) {
                        Log.i("TEST", "stoping service");
                        stopSelf();
                    } else {
                        sendEmptyMessageDelayed(MESSAGE_TIMEOUT_AFTER_FIRST_CALL, SERVICE_LIFESPAN);
                    }
                    break;
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        HttpGet httpget = new HttpGet(intent.getData().toString());
        HttpRequestExecution t = new HttpRequestExecution(client, httpget, intent);
        sExecutor.submit(t);
        handler.sendEmptyMessage(MESSAGE_RECEIVED_REQUEST);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private interface ExecutionListener {
        public void onPreCall();

        public void onPostCall();

        public void onResponse(HttpResponse responce) throws IOException;

        public void onThrowable(Throwable e);
    }

    private class HttpRequestExecution implements Runnable {

        AndroidHttpClient client;

        private HttpUriRequest request;

        private Intent intent;

        public HttpRequestExecution(AndroidHttpClient client, HttpUriRequest request, Intent intent) {
            this.client = client;
            this.request = request;
            this.intent = intent;
        }

        private static final String EXTRA_STATUS_RECEIVER = "novoda.rest.extra.STATUS_RECEIVER";

        @Override
        public void run() {
            receiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
            TimingLogger logg = new TimingLogger(TAG, "request");
            try {
                onPreCall(request, null);
                logg.addSplit("onpre");
                HttpResponse response = client.execute(request);
                logg.addSplit("onpost");

                onPostCall(response, null);
                logg.addSplit("on p");
                onHandleResponse(response, null);
                logg.addSplit("on handle response");
            } catch (Exception e) {
                onThrowable(e);
            } finally {
                onFinishCall();
                logg.dumpToLog();
            }
        }

        protected void onHandleResponse(HttpResponse response, HttpContext context) {
            Command command = getCommand(intent);
            command.setData(getData(response, context));
            if (command instanceof Persister) {
                // ModularSQLiteOpenHelper db = new
                // ModularSQLiteOpenHelper(getApplicationContext());
                // ((Persister) command).setPersister(db);
            }
            command.execute();
        }

        private Node<?> getData(HttpResponse response, HttpContext context) {
            int type = IOCLoader.getInstance(getApplicationContext()).getFormat();
            switch (type) {
                case JSON:
                    return getJsonNode(response, context);
            }
            throw new IllegalArgumentException("can not find format");
        }

        private Node<?> getJsonNode(HttpResponse response, HttpContext context) {
            JsonNodeParser parser = new JsonNodeParser();
            try {
                return parser.handleResponse(response);
            } catch (ClientProtocolException e) {
            } catch (IOException e) {
            } finally {
            }
            throw new ParserException("can not parse stream");
        }
    }

    protected abstract Command getCommand(Intent intent);

    private static final int JSON = 0;
}
