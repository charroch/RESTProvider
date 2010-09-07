
package novoda.rest.clag;

import java.io.InputStream;

import org.apache.http.client.methods.HttpUriRequest;
import org.codehaus.jackson.JsonNode;

import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import novoda.rest.context.CallContext;
import novoda.rest.context.CallInfo;
import novoda.rest.context.QueryCallContext;
import novoda.rest.context.QueryCallContextTest;
import novoda.rest.database.CachingStrategy;
import novoda.rest.exception.ParserException;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.NodeParser;
import novoda.rest.parsers.json.JsonNodeParser;
import novoda.rest.services.RESTCallService;
import novoda.rest.services.RemoteCallService;

public class ClagRemoteCallService extends RemoteCallService {

    @Override
    public CallContext<?> getCallContext(CallInfo info) {

//        if (RESTCallService.ACTION_QUERY.equals(info.action)) {
//            return new QueryCallContext(getBaseContext()) {
//
//                @Override
//                public CachingStrategy getCachingStrategy() {
//                    // TODO Auto-generated method stub
//                    return null;
//                }
//
//                @Override
//                public NodeParser<?> getParser() {
//                    return new JsonNodeParser.Builder().withNodeName("test").build(
//                            JsonNodeParser.class);
//                }
//
//                @Override
//                public HttpUriRequest getRequest(CallInfo info) {
//                    // TODO Auto-generated method stub
//                    return null;
//                }
//
//                @Override
//                public void handle(CallInfo info, Node<?> data, SQLiteOpenHelper db) {
//                    // TODO Auto-generated method stub
//
//                }
//
//                @Override
//                public Node<?> parse(InputStream in) throws ParserException {
//                    return null;
//                }
//
//            };
//        }
        return null;
    }

    @Override
    public QueryCallContext<JsonNode> getQueryCallContext(Uri uri) {
        return null;
    }

}
