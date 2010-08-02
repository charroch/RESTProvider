
package novoda.rest.parsers.json;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;

import novoda.rest.exception.ParserException;
import novoda.rest.parsers.NodeParser;
import novoda.rest.parsers.Node.Options;

public class JsonNodeParser extends NodeParser<JsonNodeObject> {
    
    static ObjectMapper oMapper = new ObjectMapper();

    @Override
    public JsonNodeObject parse(InputStream response, Options options) throws ParserException {
        
        return null;
    }

}
