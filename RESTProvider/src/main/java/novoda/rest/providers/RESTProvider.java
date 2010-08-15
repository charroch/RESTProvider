
package novoda.rest.providers;

import novoda.rest.context.QueryCallInfo;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.utils.Logger;
import novoda.rest.utils.UriUtils;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Service;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.net.Uri;

import java.io.IOException;

public class RESTProvider extends ContentProvider implements IRESTProvider {

    private static final String METADATA_NAME = "novoda.rest";

    protected ProviderMetaData metaData;

    private Logger log;

    ModularSQLiteOpenHelper db;

    IRESTProvider remoteProvider;

    private ProviderInfo providerInfo;

    @Override
    public boolean onCreate() {
        log = Logger.getLogger(this.getClass());
        try {
            providerInfo = getContext().getPackageManager().resolveContentProvider(
                    this.getClass().getCanonicalName(), PackageManager.GET_META_DATA);

            final XmlResourceParser xml = providerInfo.loadXmlMetaData(getContext()
                    .getPackageManager(), METADATA_NAME);

            metaData = ProviderMetaData.loadFromXML(xml);

            if (metaData.clag != null) {
                // create clag wrapper;
                remoteProvider = new ClagProvider();
            }

        } catch (XmlPullParserException e) {
            if (Logger.isErrorEnabled()) {
                log.error("metada not well defined", e);
            }
        } catch (IOException e) {
            if (Logger.isWarnEnabled()) {
                log.warn("no metadata defined");
            }
        } catch (NullPointerException e) {
            if (Logger.isWarnEnabled()) {
                log.warn("no metadata defined");
            }
        }
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
        
        if (UriUtils.isItem(getBaseURI(), uri)) {
            
        }
        return null;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        return 0;
    }

    @Override
    public Service getService() {
        if (metaData == null) {
            throw new IllegalStateException("No metadata attached to the provider,"
                    + " have you provided a meta-data tag in the manifest?");
        }
        try {
            // TODO check service within the manifest to ensure it responds to
            // the correct intents?
            return (Service) Class.forName(metaData.serviceClassName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can not load service class");
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
        return providerInfo.authority;
    }
}
