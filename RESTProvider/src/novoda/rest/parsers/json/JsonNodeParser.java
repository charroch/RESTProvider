
package novoda.rest.parsers.json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import novoda.rest.exception.ParserException;
import novoda.rest.parsers.NodeParser;
import novoda.rest.parsers.Node.ParsingOptions;

public class JsonNodeParser extends NodeParser<JsonNodeObject> {
    
	static ObjectMapper oMapper = new ObjectMapper();
    
    @Override
    public JsonNodeObject parse(InputStream response, ParsingOptions options) throws ParserException {
        try {
            JsonNode node = oMapper.readTree(response);
            JsonNodeObject o = new JsonNodeObject(node);
            if (options != null)
            	o.applyOptions(options);
            return o;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ParserException("unknown error");
    }
}
