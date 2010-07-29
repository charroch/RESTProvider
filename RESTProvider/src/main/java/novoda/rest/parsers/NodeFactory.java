
package novoda.rest.parsers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NodeFactory {

    public static class Options {
        public String rootNode;

        public String nodeName;

        public Map<String, String> mapper = new HashMap<String, String>();

        public List<Options> children = new ArrayList<Options>();
    }

    public static Node<?> parse(InputStream in) {
        return null;
    };

    public static Node<?> parse(InputStream in, Options option) {
        return null;
    };
}
