
package novoda.rest.services;

import novoda.rest.UriRequestMap;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.providers.ModularProvider;
import novoda.rest.utils.AndroidHttpClient;
import novoda.rest.utils.DatabaseUtils;

import android.app.IntentService;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Intent;
import android.database.AbstractCursor;
import android.net.Uri;
import android.os.Bundle;

public abstract class RESTCallService extends IntentService implements UriRequestMap {

    private static final String TAG = RESTCallService.class.getSimpleName();

    public RESTCallService() {
        this(TAG);
    }
    
    public RESTCallService(String name) {
        super(name);
    }

    public static final String BUNDLE_SORT_ORDER = "sortOrder";

    public static final String BUNDLE_SELECTION_ARG = "selectionArg";

    public static final String BUNDLE_SELECTION = "selection";

    public static final String BUNDLE_PROJECTION = "projection";

    public static final String ACTION_QUERY = "novoda.rest.action.ACTION_QUERY";

    public static final String ACTION_UPDATE = "novoda.rest.action.ACTION_UPDATE";

    public static final String ACTION_INSERT = "novoda.rest.action.ACTION_INSERT";

    public static final String ACTION_DELETE = "novoda.rest.action.ACTION_DELETE";

    protected static AndroidHttpClient httpClient;

    static {
        setupHttpClient();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        Uri uri = intent.getData();
        String action = intent.getAction();

        if (action.equals(ACTION_QUERY)) {
            
        	final String[] projection = bundle.getStringArray(BUNDLE_PROJECTION);
            final String selection = bundle.getString(BUNDLE_SELECTION);
            final String[] selectionArg = bundle.getStringArray(BUNDLE_SELECTION_ARG);
            final String sortOrder = bundle.getString(BUNDLE_SORT_ORDER);
            
            //try {
                AbstractCursor cursor = null;
//                AbstractCursor cursor =new RESTMarhalelr( httpClient.execute(getRequest(uri, UriRequestMap.QUERY,
//                        getQueryParams(uri, projection, selection, selectionArg, sortOrder)),
//                        getQueryHandler(uri))).getCursor();
//                
               // marshaller.getChildren();
                
                ContentProviderClient client = getContentResolver().acquireContentProviderClient(uri);
                ModularProvider provider = (ModularProvider) client.getLocalContentProvider();
                
                if (cursor instanceof SQLiteTableCreator) {
                    provider.create((SQLiteTableCreator) cursor);
                }
                
                ContentValues values = new ContentValues(cursor.getColumnCount());
                while (cursor.moveToNext()){
                    DatabaseUtils.cursorRowToContentValues(cursor, values);
                    getContentResolver().insert(uri, values);
                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        getBaseContext().sendBroadcast(new Intent("novoda.rest.action.QUERY_COMPLETE"));
    }

    private static void setupHttpClient() {
        httpClient = AndroidHttpClient.newInstance("Android/RESTProvider");
    }
}
