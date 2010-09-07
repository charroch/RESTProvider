
package novoda.rest.providers;

import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.intents.HttpServiceIntent;
import novoda.rest.system.IOCLoader;
import novoda.rest.utils.UriUtils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.net.Uri;

public class RESTProvider extends ContentProvider implements IRESTProvider {

    ModularSQLiteOpenHelper db;

    IRESTProvider remoteProvider;

    private IOCLoader loader;

    @Override
    public boolean onCreate() {
        loader = IOCLoader.getInstance(getContext());
        return true;
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri arg0, ContentValues arg1) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        Cursor cursor = db.getReadableDatabase().query(getTableName(uri), projection, selection,
                selectionArgs, null, null, sortOrder);
        
        return cursor;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        return 0;
    }

    @Override
    public ServiceInfo getService() {
        return loader.getServiceInfo();
    }

    public void startService(HttpServiceIntent intent) {
        getContext().startService(intent.getIntent());
    }

    public String getBaseURI() {
        return "";
    }

    public String getTableName(Uri uri) {
        return UriUtils.getTableName(uri);
    }

    public Uri getHttpUri(Uri uri) {
        return uri.buildUpon().scheme("http").build();
    }

    public Intent getHttpIntent(Uri uri, int type) {
        return null;
    }
}
