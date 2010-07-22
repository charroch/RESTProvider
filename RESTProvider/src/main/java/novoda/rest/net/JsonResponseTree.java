
package novoda.rest.net;

import novoda.rest.database.SQLiteInserter;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResponseTree extends ResponseTree {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public JsonResponseTree() {
    }

    public static final class Builder {
        private String rootNode;

        private String nodeName;

        public Map<String, String> mapper = new HashMap<String, String>();

        private List<Builder> children;

        public Builder withRootNode(final String rootNode) {
            this.rootNode = rootNode;
            return this;
        }

        public Builder withNodeName(final String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public Builder withChildren(Builder... childs) {
            children.addAll(Arrays.asList(childs));
            return this;
        }

        public Builder withMappedField(final String original, final String newValue) {
            mapper.put(original, newValue);
            return this;
        }
        
        public Builder withUri(final Uri uri) {
            return this;
        }

        public ResponseTree create(InputStream in, Uri uri) throws JsonProcessingException, IOException {
            JsonNode node = objectMapper.readTree(in);
            node.path(rootNode);
            
            JsonResponseTree tree = new JsonResponseTree();
            
            JsonInserter d = new DefaultJsonInserter(node);
            
            Node<JsonInserter> nodew = new Node<JsonInserter>(d);
            nodew.setUri(uri);
            
            tree.setRootElement(nodew);
            
            for (Builder b : children){
                JsonInserter d2 = new DefaultJsonInserter(node.path(b.nodeName));
                Node<JsonInserter> n2 = new Node<JsonInserter>(d2);
                nodew.addChild(n2);
            }
            
            return null;
        }
        
        private void populate(Node<JsonInserter> root, Builder child, JsonNode node) {
            for (Builder b : children){
                JsonNode node1 = node.path(b.nodeName);
                JsonInserter d2 = new DefaultJsonInserter(node1);
                Node<JsonInserter> n2 = new Node<JsonInserter>(d2);
                root.addChild(n2);
                populate(n2, b, node1);
            }
        }
    }
}
