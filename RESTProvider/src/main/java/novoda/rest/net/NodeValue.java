package novoda.rest.net;

import novoda.rest.database.SQLiteInserter;
import novoda.rest.parsers.SQLiteParser;

import java.util.HashMap;
import java.util.Map;

public abstract class NodeValue implements SQLiteInserter, SQLiteParser {
    
    public class feature {
        String rootNode;
        String nodeName;
        Map<String, String> mapper = new HashMap<String, String>();
    }
    
    NodeValue() {
    }
    
    NodeValue(NodeValue.feature feature) {
    }
    
    public abstract NodeValue path(String string);
    public abstract NodeValue path(int index);
}
