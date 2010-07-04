
package novoda.rest.cursors;

import novoda.rest.cursors.CursorBuilder.Builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CursorController {
    
    public static class CursorParams<T extends ResponseCursor> {
        public String rootName;

        public String fieldId;

        public String nodeName = null;

        public Map<String, String> mapper = new HashMap<String, String>();

        public boolean withAutoId = false;

        public List<ResponseCursor> withChildren;
        
        public List<Builder<T>> withChil;
        
        public void apply(CursorController cursor) {
            
        }
    }
}
