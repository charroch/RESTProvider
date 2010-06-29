
package novoda.rest.cursors.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import novoda.rest.RESTProvider;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class XMLCursorTest {

    @Mock
    HttpResponse response;

    @Mock
    HttpEntity entity;

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        RESTProvider.DEBUG = false;
        when(response.getEntity()).thenReturn(entity);
    }

    @Test
    public void shouldParseSimpleXML() throws Exception {
        SimpleXMLCursor c = new SimpleXMLCursor.Builder().withFieldID("integer").withRootNode(
                "array").withNodeName("array_item").create();

        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simple.xml")));

        c.handleResponse(response);

        // Simple testing of columns and count
        assertTrue(Arrays.asList(c.getColumnNames()).contains("string"));
        assertTrue(Arrays.asList(c.getColumnNames()).contains("integer"));
        assertEquals(2, c.getCount());

        // testing the cursor actually works
        assertTrue(c.moveToNext());
        assertEquals(c.getInt(c.getColumnIndex("integer")), 1);
        assertEquals(c.getString(c.getColumnIndex("string")), "string");

        assertTrue(c.moveToNext());
        assertEquals(c.getInt(c.getColumnIndex("integer")), 2);
        assertEquals(c.getString(c.getColumnIndex("string")), "another");
    }

    @Test
    public void shouldMapFieldIDToString() throws Exception {
        SimpleXMLCursor c = new SimpleXMLCursor.Builder().withFieldID("integer", true)
                .withRootNode("array").withNodeName("array_item").create();
        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simple.xml")));
        c.handleResponse(response);

        // testing the cursor actually works
        assertTrue(c.moveToNext());
        assertEquals(c.getInt(c.getColumnIndex("_id")), 1);
        assertEquals(c.getString(c.getColumnIndex("string")), "string");

    }

    @Test
    public void shouldMapFieldsToString() throws Exception {
        SimpleXMLCursor c = new SimpleXMLCursor.Builder().withFieldID("integer").withRootNode(
                "array").withNodeName("array_item").withMappedField("value", "string").create();
        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simple.xml")));
        c.handleResponse(response);

        // testing the cursor actually works
        assertTrue(c.moveToNext());
        assertEquals(c.getInt(c.getColumnIndex("integer")), 1);
        assertEquals(c.getString(c.getColumnIndex("value")), "string");
    }
}
