
package novoda.rest.services;

import novoda.rest.context.QueryCallInfo;
import novoda.rest.context.command.Command;
import novoda.rest.context.command.QueryCommand;
import novoda.rest.parsers.json.JsonNodeObject;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;

public class HttpServiceInvoker extends HttpService {

    public static final String CONTENT_TYPE_JSON = "application/json";

    /*
     * We can have several content type for XML. For instance application/xml or
     * text/xml or text/rss+xml etc... as such we only check for XML prefix
     */
    public static final String CONTENT_TYPE_XML_SUFFIX = "xml";

    public static final int JSON = 0;

    public static final int XML = 1;

    @Override
    protected void onHandleResponse(HttpResponse response, HttpContext context) {
        Intent intent = getIntent().getParcelableExtra("intent");
        Command c = getCommand(intent, 0);
        InputStream in;
        try {
            in = response.getEntity().getContent();
            JsonNode node = new ObjectMapper().readTree(in);
            JsonNodeObject obj = new JsonNodeObject(node);
            c.execute(obj);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Command<?> getCommand(Intent intent, int type) {
        //QueryCallInfo info = intent.getParcelableExtra("callinfo");
        return new QueryCommand<JsonNode>();
    }
    
    private QueryCommand<?> getQueryCommand(QueryCallInfo info, int type) {
        return new QueryCommand<JsonNode>();
    }
}
