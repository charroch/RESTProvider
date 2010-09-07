
package novoda.rest.providers;

import novoda.rest.RESTIntent;
import novoda.rest.configuration.ProviderMetaData;
import novoda.rest.context.CallInfo;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.intents.HttpServiceIntent;
import novoda.rest.services.HttpService;
import novoda.rest.system.IOCLoader;
import novoda.rest.utils.UriUtils;

import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.net.Uri;

import java.io.IOException;
import java.util.List;

public class RESTProvider extends ContentProvider implements IRESTProvider {

    private static final String METADATA_NAME = "novoda.rest";

    protected ProviderMetaData metaData;

    ModularSQLiteOpenHelper db;

    IRESTProvider remoteProvider;

    private ProviderInfo providerInfo;

    private IOCLoader loader;

    @Override
    public boolean onCreate() {
        loader = new IOCLoader(getContext());
        try {
            metaData = getMetaData();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private ProviderMetaData getMetaData() throws XmlPullParserException, IOException {
        final XmlResourceParser xml = getProviderInfo().loadXmlMetaData(
                getContext().getPackageManager(), METADATA_NAME);
        return ProviderMetaData.loadFromXML(xml);
    }

    protected ProviderInfo getProviderInfo() {
        // Get all providers associated with this process id. This might not
        // work with multi-process calls.
        List<ProviderInfo> providerInfo = getContext().getPackageManager().queryContentProviders(
                getContext().getPackageManager().getNameForUid(android.os.Process.myUid()),
                android.os.Process.myUid(), 0);
        for (ProviderInfo info : providerInfo) {
            if (getClass().getName().equals(info.name)) {
                return getContext().getPackageManager().resolveContentProvider(info.authority,
                        PackageManager.GET_META_DATA);
            }
        }
        return null;
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
        return loader.getServiceInfo(metaData.serviceClassName);
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
