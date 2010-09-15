
package novoda.rest.utils;

import static org.junit.Assert.*;
import novoda.mixml.XMLNode;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class XMLMiXPathTest {

    @Test
    public void testSimplePath() throws Exception {
        XMLNode path = new XMLNode();
        
        path.parse(
                new FileInputStream(new File("src/test/resources/simple.xml")));
        
        XMLMiXPath p = new XMLMiXPath();
        assertEquals(path.path("response").path("status").getAsInt(), 0);
        assertEquals(2, p.parse(path, "response/test/value/").getAsInt());
        assertEquals("another", p.parse(path, "response/array/[1]").path("string").getAsString());
    }
}
