
package novoda.rest.net;

import org.codehaus.jackson.JsonNode;

import novoda.rest.net.JsonResponseTree.Builder;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;

public abstract class ResponseTreeFactory {
    
    public abstract ResponseTree parse(InputStream in, Uri uri);
    
    public void load(InputStream in, Builder builder) {
        //ResponseTree tree = new this.parse(in, uri);
        
    }
    
    //public Node<>

    public static final class Builder {
        private String rootNode;

        private String nodeName;

        private Uri uri;

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

        private void populate(Node<JsonInserter> root, Builder child, JsonNode node) {
            for (Builder b : children) {
                JsonNode node1 = node.path(b.nodeName);
                JsonInserter d2 = new DefaultJsonInserter(node1);
                Node<JsonInserter> n2 = new Node<JsonInserter>(d2);
                root.addChild(n2);
                populate(n2, b, node1);
            }
        }
    }
}

class JsonTreeFactory extends ResponseTreeFactory {

    @Override
    public ResponseTree parse(InputStream in, Uri uri) {
        return null;
    }
}
