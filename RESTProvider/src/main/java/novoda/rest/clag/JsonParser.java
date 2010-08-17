
package novoda.rest.clag;

import novoda.rest.exception.ParserException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public abstract class JsonParser<T> implements Parser<T> {
    public static ObjectMapper mapper = new ObjectMapper();

    @Override
    public T parse(InputStream in) throws ParserException {
        try {
            return parse(mapper.readTree(in));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ParserException("can not parse response");
    }

    public abstract T parse(JsonNode node);
}
