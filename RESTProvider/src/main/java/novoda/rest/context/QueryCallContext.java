
package novoda.rest.context;

import java.io.IOException;

import novoda.rest.database.CachingStrategy;
import novoda.rest.database.DatabaseUtils;
import novoda.rest.database.SQLiteTableCreatorWrapper;
import novoda.rest.database.UriTableCreator;
import novoda.rest.net.ETag;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.NodeParser;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Pair;

public abstract class QueryCallContext<T> extends CallContext<Node<T>> {

    public QueryCallContext(Context context) {
        super(context);
    }

    @Override
    public Node<T> call() {
        onStart();
        final HttpUriRequest request = getRequest(getCallInfo());
        final NodeParser<?> parser = getParser();
        final CachingStrategy strategy = getCachingStrategy();

        onPreCall(request, parser);

        try {
            Node<T> rootNode = (Node<T>)getHttpClient().execute(request, parser);

            if (parser.getStatusCode() == HttpStatus.SC_NOT_MODIFIED) {
                return null;
            } else {
                onPostCall(rootNode);

                if (strategy.onNewResults(this) == CachingStrategy.REPLACE
                        && dbHelper.isTableCreated(rootNode.getTableName())) {

                    final Pair<String, String[]> where = strategy.getWhereClause(this);
                    dbHelper.getWritableDatabase().delete(rootNode.getTableName(), where.first,
                            where.second);

                }

                insertNodeIntoDatabase(rootNode);
                onFinish();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void onStart() {
        // TODO
    }

    private void onFinish() {
        // TODO
    }

    private void onPostCall(Node<T> rootNode) {
        // TODO
    }

    private void onPreCall(HttpUriRequest request, NodeParser<?> parser) {
        // TODO
    }

    public abstract CachingStrategy getCachingStrategy();

    public abstract NodeParser<?> getParser();

    protected synchronized ETag getEtag() {
        return DatabaseUtils.etagForQuery(getDBHelper().getReadableDatabase(), getCallInfo().url);
    }

    private void insertNodeIntoDatabase(Node<?> child2) {
        final int size = child2.getCount();
        for (int i = 0; i < size; i++) {
            Node<?> current = child2.getNode(i);

            SQLiteTableCreatorWrapper creator = new SQLiteTableCreatorWrapper(UriTableCreator
                    .fromNode(current));

            onPreTableCreate(creator);

            dbHelper.createTable(creator);

            ContentValues values = current.getContentValue();
            current.onPreInsert(values);
            Node<T> f = (Node<T>)current.getParent();

            if (f != null) {
                values.put(f.getIdFieldName(), f.getDatabaseId());
            }

            onPreInsert(child2.getOptions().insertUri, values);

            long id = dbHelper.getWritableDatabase().insert(current.getTableName(), "", values);
            current.onPostInsert(id);
            if (id > 0) {
                for (Node<?> child : current.getChildren()) {
                    insertNodeIntoDatabase(child);
                }
            }
        }
    }

    private void onPreInsert(Uri insertUri, ContentValues values) {
    }

    private void onPreTableCreate(SQLiteTableCreatorWrapper creator) {
    }
}
