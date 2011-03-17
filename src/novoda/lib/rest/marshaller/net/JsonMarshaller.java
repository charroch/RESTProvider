
package novoda.lib.rest.marshaller.net;

import com.google.gson.stream.JsonReader;

import novoda.lib.rest.marshaller.MarshallingException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonMarshaller extends HttpResponseMarshaller<JsonReader> {
    @Override
    protected JsonReader marshall(InputStream content) throws IOException, MarshallingException {
        return new JsonReader(new InputStreamReader(content, "UTF-8"));
    }
}
