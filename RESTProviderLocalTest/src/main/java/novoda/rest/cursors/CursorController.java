
package novoda.rest.cursors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CursorController {
    
    public static class CursorParams {
        public String rootName;

        public String fieldId;

        public String nodeName = null;

        public Map<String, String> mapper = new HashMap<String, String>();

        public boolean withAutoId = false;

        public List<ResponseCursor> withChildren;
        
        public void apply(CursorController cursor) {
        }
    }
}
