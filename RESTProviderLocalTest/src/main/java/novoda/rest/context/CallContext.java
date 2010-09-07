
package novoda.rest.context;

import java.io.IOException;
import java.io.InputStream;

import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.exception.ParserException;
import novoda.rest.utils.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import android.content.Context;
import android.util.Log;

/**
 * @author acsia
 */
public abstract class CallContext<T> implements ICallContext<T> {

    private static final String USER_AGENT = "android/RESTProvider";

    protected ModularSQLiteOpenHelper dbHelper;

    private Context context;

    private CallInfo info;

    private HttpClient client;

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
    public T call() throws Exception {
        return getHttpClient().execute(getRequest(info), this);
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        return parse(getInputStreamFromResponse(response));
    }

    // Control GZip and so forth.
    private InputStream getInputStreamFromResponse(HttpResponse response)
            throws IllegalStateException, IOException {
        if (response != null) {
            return response.getEntity().getContent();
        }
        return null;
    }

    public T getData() {
        try {
            return call();
        } catch (Exception e) {
            throw new ParserException("can t parser");
        }
    }
}
