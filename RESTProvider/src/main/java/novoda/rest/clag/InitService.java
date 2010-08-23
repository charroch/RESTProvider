
package novoda.rest.clag;

import novoda.rest.context.CallContext;
import novoda.rest.context.CallInfo;
import novoda.rest.context.CallResult;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.exception.ParserException;
import novoda.rest.providers.ClagMetaData;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.codehaus.jackson.JsonNode;

import android.content.Context;

import java.io.InputStream;

public class InitService extends CallContext<JsonNode> {

    private ClagMetaData data;

    public InitService(ClagMetaData data, Context context) {
        super(context);
        this.data = data;
    }

    @Override
    public HttpUriRequest getRequest(CallInfo info) {
        return new HttpGet(data.endpoint);
    }

    @Override
    public CallResult call() throws Exception {
        HttpResponse response = getHttpClient().execute(getRequest(null));
        ServiceDescriptionParser parser = new ServiceDescriptionParser();
        try {
            ServiceDescription d = parser.parse(response.getEntity().getContent());
            for (SQLiteTableCreator c : d.schemas) {
                getDBHelper().createTable(c);
            }
        } finally {
            response.getEntity().consumeContent();
        }
        close();
        return null;
    }

    @Override
    public JsonNode parse(InputStream in) throws ParserException {
        return null;
    }
}
