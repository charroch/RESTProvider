
package novoda.rest.context;

import novoda.rest.clag.Parser;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.services.CallInfo;
import novoda.rest.utils.AndroidHttpClient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.Callable;

/**
 * @author acsia
 */
public abstract class CallContext<T> implements Callable<CallResult>, Comparable<CallContext<T>>,
        Parser<T> {

    private static final String USER_AGENT = "android/RESTProvider";

    protected ModularSQLiteOpenHelper dbHelper;

    private Context context;

    private CallInfo info;

    protected HttpContext httpContext;

    private android.net.http.AndroidHttpClient client;

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
                client.enableCurlLogging("curl", Log.DEBUG);
            }
            return client;
        } catch (RuntimeException e) {
            return AndroidHttpClient.newInstance(USER_AGENT);
        }
    }

    public void close() {
        if (client != null)
            client.close();
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

}
