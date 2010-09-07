
package novoda.rest.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MappedHashMapTest {

    @Test
    public void testSimpleMapping() throws Exception {
        MappedHashMap<String, String> t = new MappedHashMap<String, String>();
        t.put("1", "v1");
        t.put("2", "v2");
        
        Map<String, String> m = new HashMap<String, String>();
        m.put("_1", "1");
        
        t.setMapper(m);
        
        assertEquals("v1", t.get("_1"));
        assertEquals("v1", t.getFromOriginalKey("1"));
    }

}
