package novoda.rest.providers;

import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.services.RESTCallService;

import android.net.Uri;

public class TestProvider extends ModularProvider {

    @Override
    protected RESTCallService getService() {
        return null;
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
