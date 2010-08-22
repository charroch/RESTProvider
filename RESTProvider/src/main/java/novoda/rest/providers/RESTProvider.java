
package novoda.rest.providers;

import novoda.rest.context.QueryCallInfo;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.services.RESTCallService;
import novoda.rest.utils.Logger;
import novoda.rest.utils.UriUtils;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Service;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class RESTProvider extends ContentProvider implements IRESTProvider {

    private static final String METADATA_NAME = "novoda.rest";

    protected ProviderMetaData metaData;

    ModularSQLiteOpenHelper db;

    IRESTProvider remoteProvider;

    private ProviderInfo providerInfo;

    @Override
    public boolean onCreate() {
        try {
            providerInfo = getProviderInfo();
            metaData = getMetaData();

            if (metaData.clag != null) {
                // create clag wrapper;
                //remoteProvider = new ClagProvider();
                Log.i("TSA", "tgus " + metaData.clag.endpoint);
                Intent intent = new Intent(getContext(), getService().getClass());
                intent.putExtra("clag", metaData.clag);
                getContext().startService(intent);
            }
            return true;
        } catch (Exception e){
            Log.e("TS", "something went wrong", e);
        }
        return false;
    }

    private ProviderMetaData getMetaData() throws XmlPullParserException, IOException {
        final XmlResourceParser xml = providerInfo.loadXmlMetaData(
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
                return getContext().getPackageManager().resolveContentProvider(info.authority, PackageManager.GET_META_DATA);
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
        if (UriUtils.isItem(getBaseURI(), uri)) {

        }
        return null;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        return 0;
    }

    @Override
    public Service getService() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (metaData == null || metaData.serviceClassName == null) {
            throw new IllegalStateException("No metadata attached to the provider,"
                    + " have you provided a meta-data tag in the manifest?");
        }
        return (Service) Class.forName(metaData.serviceClassName).newInstance();
    }

    @Override
    public void query(QueryCallInfo query) {
    }

    /**
     * Method which returns the authority of the provider. This is useful for
     * default URI mapping. If your authority differs from conventions (i.e.
     * content://<authority>/somevalue/<tablename> intead of
     * content://<authority>/<tableName>) overwrite this method.
     * 
     * @return the authority of this provider, by default the one defined in the
     *         manifest for this provider
     */
    public String getBaseURI() {
        return "";
    }
}
