
package novoda.rest.cursors.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@RunWith(MockitoJUnitRunner.class)
public class DOMCursorTest {

    @Mock
    HttpResponse response;

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLame() throws Exception {
    }

    public void testSimpleDOM() throws Exception {
        String xml = "<root><field>field</field><field2>field2</field2></root>";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes());
        Document doc = db.parse(bis);
        Element rootElement = doc.getDocumentElement();
        NodeList array = rootElement.getElementsByTagName("root");

        int numSections = array.getLength();
        for (int i = 0; i < numSections; i++) {

            Element section = (Element)array.item(i); // A <sect1>

            String title = section.getChildNodes().item(0).getNodeName();
            System.out.println(title);
        }

        when(response.getEntity()).thenReturn(
                new StringEntity("<root>\n\t<field>field</field>\n\t</root>"));
        DOMCursor cursor = new DOMCursor.Builder().withRootField("root").create();
        cursor.handleResponse(response);
        assertTrue(cursor.moveToFirst());
        assertEquals("field", cursor.getColumnNames()[0]);
        assertEquals("field", cursor.getString(cursor.getColumnIndex("field")));
    }
}
