
package novoda.rest.services;

import novoda.rest.UriRequestMap;
import novoda.rest.database.UriTableCreator;
import novoda.rest.parsers.Node;
import novoda.rest.providers.ModularProvider;
import novoda.rest.utils.AndroidHttpClient;

import android.app.IntentService;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

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
            ContentProviderClient client = getContentResolver().acquireContentProviderClient(uri);
            ModularProvider provider = (ModularProvider) client.getLocalContentProvider();
            try {
                Node<?> root = httpClient.execute(getRequest(uri, INSERT, getQueryParams(uri,
                        projection, selection, selectionArg, sortOrder)), getParser(uri));
                insertNodeIntoDatabase(root, provider);
            } catch (IOException e) {
                Log.e(TAG, "something went wrong", e);
            }
        }
        getBaseContext().getContentResolver().notifyChange(uri, null);
        getBaseContext().sendBroadcast(new Intent("novoda.rest.action.QUERY_COMPLETE"));
    }

    private void insertNodeIntoDatabase(Node<?> root, ModularProvider provider) {
        final int size = root.getCount();
        for (int i = 0; i < size; i++) {
            Node<?> current = root.getNode(i);

            provider.create(UriTableCreator.fromNode(current));
            
            ContentValues values = current.getContentValue();
            current.onPreInsert(values);
            Node<?> f = current.getParent();

            if (f != null) {
                Log.i(TAG, "child: " + f.getIdFieldName());
                Log.i(TAG, "child: " + f.getDatabaseId());
                values.put(f.getIdFieldName(), f.getDatabaseId());
            }

            Log.i(TAG, values.toString());
            long id = provider.insert(current.getTableName(), values);
            current.onPostInsert(id);

            if (id > 0) {
                for (Node<?> child : current.getChildren()) {
                    insertNodeIntoDatabase(child, provider);
                }
            }
        }
    }

    private static void setupHttpClient() {
        httpClient = AndroidHttpClient.newInstance("Android/RESTProvider");
    }
}
