
package novoda.rest.providers;

import novoda.rest.clag.provider.ClagProvider;
import novoda.rest.configuration.ProviderMetaData;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.intents.HttpServiceIntent;
import novoda.rest.system.IOCLoader;
import novoda.rest.utils.UriUtils;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class RESTProvider extends ContentProvider implements ContentProviderDelegator {

    SQLiteOpenHelper db;

    private IOCLoader loader;

    private ProviderMetaData metaData;

    private ContentProviderDelegate delegate;

    @Override
    public boolean onCreate() {
        loader = IOCLoader.getInstance(getContext());
        metaData = loader.getMetaData();
        delegate = getDelegate();
        db = getSQLiteOpenHelper();
//        for (SQLiteTableCreator creator : metaData.sqlite.tables) {
//            db.createTable(creator);
//        }
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        if (UriUtils.isItem(uri)) {
            selection = "_id=?";
            selectionArgs = new String[] {
                uri.getLastPathSegment()
            };
        }
        Cursor cursor = db.getReadableDatabase().query(getTableName(uri), projection, selection,
                selectionArgs, null, null, sortOrder);
        onQuery(uri, projection, selection, selectionArgs, sortOrder);
        return cursor;
    }

    protected void onQuery(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        final HttpServiceIntent intent = delegate.query(uri, projection, selection, selectionArgs,
                sortOrder);
        if (intent != null) {
            startService(intent);
        }
    }

    @Override
    public ServiceInfo getService() {
        return loader.getServiceInfo();
    }

    @Override
    public void startService(HttpServiceIntent intent) {
        Intent in = intent.getIntent();
        in.setClassName(getContext(), getService().name);
        getContext().startService(in);
    }

    public String getBaseURI() {
        return "";
    }

    public String getTableName(Uri uri) {
        return UriUtils.getTableName(uri);
    }

    public ContentProviderDelegate getDelegate() {
        if (metaData.isClag()) {
            delegate = new ClagProvider(metaData.clag);
        }
        return new DefaultContentProviderDelegate(getContext());
    }

    protected SQLiteOpenHelper getSQLiteOpenHelper() {
        return new ModularSQLiteOpenHelper(getContext());
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("not implemented!");
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArg) {
        throw new UnsupportedOperationException("not implemented!");
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArg) {
        throw new UnsupportedOperationException("not implemented!");
    }
}
