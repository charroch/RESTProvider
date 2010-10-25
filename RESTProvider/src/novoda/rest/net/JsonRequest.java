
package novoda.rest.net;

import novoda.rest.concurrent.RequestCallable;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.BufferedHttpEntity;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public abstract class JsonRequest extends RequestCallable<JsonNode> {

    public static ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonNode handleResponse(HttpResponse response) throws ClientProtocolException,
            IOException {
        if (response == null) {
            throw new IllegalStateException("response can not be null");
        }
        BufferedHttpEntity ent = new BufferedHttpEntity(response.getEntity());
        try {
            return mapper.readTree(ent.getContent());
        } finally {
            ent.consumeContent();
        }
    }

}
