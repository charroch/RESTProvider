
package novoda.rest.services;

import novoda.rest.UriRequestMap;
import novoda.rest.database.CachingStrategy;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.database.SQLiteTableCreatorWrapper;
import novoda.rest.database.UriTableCreator;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.NodeParser;
import novoda.rest.utils.AndroidHttpClient;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpUriRequest;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class RESTCallService extends IntentService implements UriRequestMap,
        CachingStrategy {

    private static final String TAG = RESTCallService.class.getSimpleName();

    public static final String BUNDLE_SORT_ORDER = "sortOrder";

    public static final String BUNDLE_SELECTION_ARG = "selectionArg";

    public static final String BUNDLE_SELECTION = "selection";

    public static final String BUNDLE_PROJECTION = "projection";

    public static final String ACTION_QUERY = "novoda.rest.action.ACTION_QUERY";

    public static final String ACTION_UPDATE = "novoda.rest.action.ACTION_UPDATE";

    public static final String ACTION_INSERT = "novoda.rest.action.ACTION_INSERT";

    public static final String ACTION_DELETE = "novoda.rest.action.ACTION_DELETE";

    static {
        setupHttpClient();
    }

    private static void setupHttpClient() {
        httpClient = AndroidHttpClient.newInstance("Android/RESTProvider");
    }

    public RESTCallService() {
        this(TAG);
    }

    public RESTCallService(String name) {
        super(name);
    }

    protected static AndroidHttpClient httpClient;

    private ModularSQLiteOpenHelper dbHelper;

    @Override
    public void onCreate() {
        if (dbHelper == null) {
            dbHelper = new ModularSQLiteOpenHelper(getBaseContext());
        }
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bundle = intent.getExtras();

        final Uri uri = intent.getData();
        final String action = intent.getAction();

        if (action.equals(ACTION_QUERY)) {
            final String[] projection = bundle.getStringArray(BUNDLE_PROJECTION);
            final String selection = bundle.getString(BUNDLE_SELECTION);
            final String[] selectionArg = bundle.getStringArray(BUNDLE_SELECTION_ARG);
            final String sortOrder = bundle.getString(BUNDLE_SORT_ORDER);
            try {
                QueryCallInfo info = null;

                HttpUriRequest request = getRequest(uri, INSERT, getQueryParams(uri, projection,
                        selection, selectionArg, sortOrder));

                NodeParser<?> parser = getParser(uri);

                onPreCall(info, request, parser);

                Node<?> root = httpClient.execute(request, parser);

                if (parser.getStatusCode() == HttpStatus.SC_NOT_MODIFIED) {
                    contentNotModified();
                } else {
                    onPostCall(info, root);

                    if (onNewResults(uri) == CachingStrategy.REPLACE
                            && dbHelper.isTableCreated(root.getTableName())) {
                        dbHelper.getWritableDatabase().delete(root.getTableName(), null, null);
                    }

                    insertNodeIntoDatabase(root);

                    if (listener != null) {
                        listener.onFinish(uri);
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "something went wrong", e);
            }
        }
        getBaseContext().getContentResolver().notifyChange(uri, null);
        getBaseContext().sendBroadcast(new Intent("novoda.rest.action.QUERY_COMPLETE"));
    }

    private void contentNotModified() {
    }

    private void onPostCall(QueryCallInfo info, Node<?> root) {
        // TODO Auto-generated method stub

    }

    private void onPreCall(QueryCallInfo info, HttpUriRequest request, NodeParser<?> parser) {
//        String etag = info.etag.etag;
//        String lastModified = info.etag.lastModified;
//
//        request.setHeader(new BasicHeader(ETag.IF_NONE_MATCH, etag));
//        request.setHeader(new BasicHeader(ETag.LAST_MODIFIED, lastModified));
        // TODO Auto-generated method stub
        // TODO set ETag, set GZip

    }

    @Override
    public void onDestroy() {
        httpClient.close();
        super.onDestroy();
    }

    private void insertNodeIntoDatabase(Node<?> root) {
        final int size = root.getCount();
        for (int i = 0; i < size; i++) {
            Node<?> current = root.getNode(i);

            SQLiteTableCreatorWrapper creator = new SQLiteTableCreatorWrapper(UriTableCreator
                    .fromNode(current));

            if (listener != null) {
                listener.onPreTableCreate(creator);
                Log.i(TAG, Arrays.toString(creator.getTableFields()));
                Log.i(TAG, creator + " ");
            }

            dbHelper.createTable(creator);

            ContentValues values = current.getContentValue();
            current.onPreInsert(values);
            Node<?> f = current.getParent();

            if (f != null) {
                values.put(f.getIdFieldName(), f.getDatabaseId());
            }

            if (listener != null) {
                listener.onPreInsert(root.getOptions().insertUri, values);
            }

            long id = dbHelper.getWritableDatabase().insert(current.getTableName(), "", values);
            current.onPostInsert(id);
            if (id > 0) {
                for (Node<?> child : current.getChildren()) {
                    insertNodeIntoDatabase(child);
                }
            }
        }
    }

    public void setInsertTransactionListener(InsertTransactionListener listener) {
        this.listener = listener;
    }

    private InsertTransactionListener listener;

    public void addNodeProcessor(NodeProcessor processor) {
        this.addNodeProcessor(-1, processor);
    }

    public void addNodeProcessor(int index, NodeProcessor processor) {
        if (nodeProcessor == null) {
            nodeProcessor = new ArrayList<NodeProcessor>();
        }
        if (index < 0) {
            nodeProcessor.add(processor);
        } else {
            nodeProcessor.add(index, processor);
        }
    }

    private List<NodeProcessor> nodeProcessor;
}
