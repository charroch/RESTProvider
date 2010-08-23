
package novoda.rest.context;

import novoda.rest.clag.Parser;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.utils.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author acsia
 */
public abstract class CallContext<T> implements Callable<CallResult>, Comparable<CallContext<T>>,
        Parser<T>, ResponseHandler<T> {

    private static final String USER_AGENT = "android/RESTProvider";

    protected ModularSQLiteOpenHelper dbHelper;

    private Context context;

    private CallInfo info;

    protected HttpContext httpContext;

    private HttpClient client;

    public abstract HttpUriRequest getRequest(CallInfo info);

    public CallContext(final Context context) {
        setContext(context);
    }

    public void setDbHelper(ModularSQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public final ModularSQLiteOpenHelper getDBHelper() {
        if (dbHelper == null) {
            dbHelper = new ModularSQLiteOpenHelper(getContext());
        }
        return dbHelper;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    public final Context getContext() {
        return context;
    }

    protected HttpClient getHttpClient() {
        try {
            if (client == null) {
                client = android.net.http.AndroidHttpClient.newInstance(USER_AGENT, getContext());
                ((android.net.http.AndroidHttpClient)client).enableCurlLogging("curl", Log.DEBUG);
            }
        } catch (RuntimeException e) {
            if (client == null) {
                client = AndroidHttpClient.newInstance(USER_AGENT);
            }
        }
        return client;
    }

    public void close() {
        if (client != null)
            client.getConnectionManager().shutdown();
    }

    protected void setGzipEncoding(boolean active) {
    }

    public void setCallInfo(CallInfo info) {
        this.info = info;
    }

    public CallInfo getCallInfo() {
        return info;
    }

    protected void finish() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int compareTo(CallContext<T> another) {
        return getCallInfo().compareTo(another.getCallInfo());
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

        return null;
    }

    public void handle(T response) {
        // handling of response
    }
}
