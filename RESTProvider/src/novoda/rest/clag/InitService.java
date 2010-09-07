
package novoda.rest.clag;

import java.io.IOException;
import java.io.InputStream;

import novoda.rest.configuration.ClagMetaData;
import novoda.rest.context.CallContext;
import novoda.rest.context.CallInfo;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.exception.ParserException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public class InitService extends CallContext<JsonNode> {

    private ClagMetaData data;

    private static ObjectMapper mapper = new ObjectMapper();

    public InitService(ClagMetaData data, Context context) {
        super(context);
        this.data = data;
    }

    @Override
    public
     HttpUriRequest getRequest(final CallInfo info) {
        return new HttpGet(data.endpoint);
    }

    @Override
    public JsonNode parse(InputStream in) throws ParserException {
        try {
            return mapper.readTree(in);
        } catch (JsonProcessingException e) {
            throw new ParserException("JSON processing exception");
        } catch (IOException e) {
            throw new ParserException("IO Exception");
        }
    }

    @Override
    public void handle(CallInfo info, JsonNode data, SQLiteOpenHelper db) {
        ServiceDescriptionParser parser = new ServiceDescriptionParser();
        ServiceDescription d = parser.parse(data);
        for (SQLiteTableCreator c : d.schemas) {
            getDBHelper().createTable(c);
        }
        close();
    }
}
