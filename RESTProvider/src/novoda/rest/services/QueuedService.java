
package novoda.rest.services;

import novoda.rest.utils.Logger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class QueuedService extends Service {
    public static final String TAG = QueuedService.class.getSimpleName();

    private static final int CORE_POOL_SIZE = 5;

    private static final int MAXIMUM_POOL_SIZE = 128;

    private static final int KEEP_ALIVE = 10;

    private static final int MESSAGE_RECEIVED_REQUEST = 0x1;

    private static final int MESSAGE_TIMEOUT_AFTER_FIRST_CALL = 0x2;

    /*
     * Service lifespan for around 3 minutes. The idea being that we should keep
     * the service up for 3 minutes before killing self.
     */
    public static final long SERVICE_LIFESPAN = 1000 * 60 * 3;

    private LifecycleHandler handler = new LifecycleHandler();

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "QueuedService #" + mCount.getAndIncrement());
            thread.setPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            return thread;
        }
    };

    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<Runnable>(10);

    private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sExecutor.submit(getCallable(intent));
        handler.sendEmptyMessage(MESSAGE_RECEIVED_REQUEST);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        sExecutor.shutdownNow();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

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
                    if (System.currentTimeMillis() - lastCall > SERVICE_LIFESPAN
                            && sWorkQueue.isEmpty()) {
                        if (Logger.isInfoEnabled()) {
                            Log.i(TAG, "stoping service");
                        }
                        stopSelf();
                    } else {
                        sendEmptyMessageDelayed(MESSAGE_TIMEOUT_AFTER_FIRST_CALL, SERVICE_LIFESPAN);
                    }
                    break;
            }
        }
    }

    protected abstract <T> Callable<T> getCallable(final Intent intent);
}
