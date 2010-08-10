
package novoda.rest.context;

import novoda.rest.database.DatabaseUtils;
import novoda.rest.services.CallInfo;
import novoda.rest.services.ETag;
import novoda.rest.services.QueryCallInfo;
import novoda.rest.utils.AndroidHttpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public abstract class CallContext {

    private static final String USER_AGENT = "android/RESTProvider";

    private SQLiteOpenHelper dbHelper;

    private Context context;

    private CallInfo info;

    public abstract HttpUriRequest getRequest(CallInfo info);

    public abstract List<NameValuePair> getParams(CallInfo info);

    public abstract CallResult execute();

    public void setDbHelper(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public final SQLiteOpenHelper getDbHelper() {
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

    protected synchronized ETag getEtag() {
        if (this instanceof QueryCallContext) {
            return DatabaseUtils.etagForQuery(dbHelper.getReadableDatabase(),
                    ((QueryCallInfo) info).url);
        }
        throw new IllegalStateException("ETag is only available for Query calls");
    }

    public void setInfo(CallInfo info) {
        this.info = info;
    }

    public CallInfo getInfo() {
        return info;
    }
}
