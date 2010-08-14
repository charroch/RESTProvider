
package novoda.rest.providers;

import novoda.rest.utils.Logger;

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

    private ProviderMetaData metaData;

    private Logger log;

    @Override
    public boolean onCreate() {
        log = Logger.getLogger(this.getClass());
        try {
            final ProviderInfo info = getContext().getPackageManager().resolveContentProvider(
                    this.getClass().getCanonicalName(), PackageManager.GET_META_DATA);

            final XmlResourceParser xml = info.loadXmlMetaData(getContext().getPackageManager(),
                    METADATA_NAME);
            metaData = ProviderMetaData.loadFromXML(xml);
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
    public String getType(Uri arg0) {
        return null;
    }

    @Override
    public Uri insert(Uri arg0, ContentValues arg1) {
        return null;
    }

    @Override
    public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3, String arg4) {
        return null;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        return 0;
    }

    @Override
    public final Service getService() {
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
}
