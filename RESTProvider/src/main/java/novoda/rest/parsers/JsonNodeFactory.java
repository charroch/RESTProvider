package novoda.rest.parsers;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;


public class JsonNodeFactory extends NodeFactory<JsonNodeObject> {

    private static ObjectMapper mapper = new ObjectMapper();
    
    private static final String TAG = JsonNodeFactory.class.getSimpleName();

    @Override
    public JsonNodeObject readValue(InputStream in, novoda.rest.parsers.NodeFactory.Options options) {
        try {
            JsonNode node = mapper.readTree(in);
            if (options.rootNode != null) {
                node = node.path(options.rootNode);
                JsonNodeObject n = new JsonNodeObject(node);                
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return null;
    }
}
