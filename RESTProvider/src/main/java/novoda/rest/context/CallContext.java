
package novoda.rest.context;

import java.util.List;

import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.services.CallInfo;
import novoda.rest.utils.AndroidHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class CallContext {

    private static final String USER_AGENT = "android/RESTProvider";

    protected ModularSQLiteOpenHelper dbHelper;

    private Context context;

    private CallInfo info;
    
    protected HttpContext httpContext;

    public abstract HttpUriRequest getRequest(CallInfo info);

    public abstract List<NameValuePair> getParams(CallInfo info);

    public abstract CallResult execute();

    public void setDbHelper(ModularSQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public final SQLiteOpenHelper getDBHelper() {
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
            return android.net.http.AndroidHttpClient.newInstance(USER_AGENT, getContext());
        } catch (RuntimeException e) {
            return AndroidHttpClient.newInstance(USER_AGENT);
        }
    }

    protected void setGzipEncoding(boolean active) {
    }

    public void setCallInfo(CallInfo info) {
        this.info = info;
    }

    public CallInfo getCallInfo() {
        return info;
    }
}
