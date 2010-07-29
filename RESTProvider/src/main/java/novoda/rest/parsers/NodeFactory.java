
package novoda.rest.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public abstract class NodeFactory<T extends Node<?>> implements ResponseHandler<Node<?>> {

    public static class Options {
        public String rootNode;

        public String nodeName;

        public Map<String, String> mapper = new HashMap<String, String>();

        public List<Options> children = new ArrayList<Options>();
    }

    public abstract Node<?> readValue(InputStream in, Options options);

    @Override
    public Node<?> handleResponse(HttpResponse response) throws ClientProtocolException,
            IOException {
        return readValue(response.getEntity().getContent(), null);
    }

}
