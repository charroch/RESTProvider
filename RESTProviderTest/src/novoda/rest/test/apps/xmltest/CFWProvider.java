package novoda.rest.test.apps.xmltest;

import android.net.Uri;

import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.providers.ModularProvider;
import novoda.rest.services.RESTCallService;

public class CFWProvider extends ModularProvider {

    @Override
    protected RESTCallService getService() {
        return new CFWService();
    }

    @Override
    protected SQLiteTableCreator getTableCreator(Uri uri) {
        return null;
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

}
