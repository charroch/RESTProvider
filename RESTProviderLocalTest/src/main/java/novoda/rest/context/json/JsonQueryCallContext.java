package novoda.rest.context.json;

import java.io.InputStream;

import org.apache.http.client.methods.HttpUriRequest;
import org.codehaus.jackson.JsonNode;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import novoda.rest.context.CallInfo;
import novoda.rest.context.QueryCallContext;
import novoda.rest.database.CachingStrategy;
import novoda.rest.exception.ParserException;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.NodeParser;

public class JsonQueryCallContext extends QueryCallContext<JsonNode> {

    public JsonQueryCallContext(Context context) {
        super(context);
    }

    @Override
    public CachingStrategy getCachingStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NodeParser<?> getParser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HttpUriRequest getRequest(CallInfo info) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handle(CallInfo info, Node<JsonNode> data, SQLiteOpenHelper db) {
        // TODO Auto-generated method stub

    }

    @Override
    public Node<JsonNode> parse(InputStream in) throws ParserException {
        return null;
    }

}
