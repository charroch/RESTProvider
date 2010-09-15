
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

import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class XMLCursorTest {

    @Mock
    HttpResponse response;

    @Mock
    HttpEntity entity;

    @Mock
    Uri uri;

    @Mock
    Uri childUri;

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        RESTProvider.DEBUG = false;
        when(response.getEntity()).thenReturn(entity);
    }

    @Test
    public void shouldParseSimpleXML() throws Exception {
        SimpleXMLCursor c = new SimpleXMLCursor.Builder().withFieldID("integer")
                .withRootNode("array").withNodeName("array_item").create();

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
        SimpleXMLCursor c = new SimpleXMLCursor.Builder().withFieldID("integer")
                .withRootNode("array").withNodeName("array_item")
                .withMappedField("value", "string").create();
        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simple.xml")));
        c.handleResponse(response);

        // testing the cursor actually works
        assertTrue(c.moveToNext());
        assertEquals(c.getInt(c.getColumnIndex("integer")), 1);
        assertEquals(c.getString(c.getColumnIndex("value")), "string");
    }

    @Test
    public void shouldParseSimpleObjects() throws Exception {
        SimpleXMLCursor c1 = new SimpleXMLCursor.Builder().withRootNode("response").create();
        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simpleSingle.xml")));
        c1.handleResponse(response);

        assertEquals(c1.getCount(), 1);
        assertTrue(c1.moveToFirst());
        assertEquals(c1.getString(c1.getColumnIndex("msg")), "message");
    }

    @Test
    public void shouldAddID() throws Exception {
        SimpleXMLCursor c = new SimpleXMLCursor.Builder().withFieldID("integer")
                .withRootNode("array").withNodeName("array_item").withAutoID().create();
        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simple.xml")));
        c.handleResponse(response);
        assertTrue(c.getColumnIndex("_id") > 0);
        c.moveToFirst();
        assertEquals(c.getInt(c.getColumnIndex("_id")), 0);
    }

    @Test
    public void shouldParseSimpeObject() throws Exception {
        SimpleXMLCursor c = new SimpleXMLCursor.Builder().withRootNode("response").withAutoID()
                .create();
        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simple.xml")));
        c.handleResponse(response);

        assertEquals(c.getCount(), 1);
        assertTrue(c.moveToFirst());
        assertEquals(c.getString(c.getColumnIndex("msg")), "message");
        assertEquals(c.getLong(c.getColumnIndex("_id")), 0);
    }

    @Test
    public void shouldAddOneToMany() throws Exception {

        // when(childUri.equals(childUri)).thenReturn(true);

        SimpleXMLCursor child = new SimpleXMLCursor.Builder().withRootNode("array")
                .withNodeName("array_item").withFieldID("integer").create();

        SimpleXMLCursor c = new SimpleXMLCursor.Builder().withRootNode("response").withAutoID()
                .withChildren(child).create();

        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simple.xml")));
        c.handleResponse(response);

        assertEquals(c.getCount(), 1);
        assertTrue(c.moveToFirst());
        assertEquals(c.getString(c.getColumnIndex("msg")), "message");
        assertEquals(c.getLong(c.getColumnIndex("_id")), 0);

        assertTrue(c.getChildUri().size() == 1);

        SimpleXMLCursor c2 = (SimpleXMLCursor) c.getChild(childUri);
        assertEquals(c2.getCount(), 2);
        assertTrue(c2.moveToFirst());
        assertEquals(c2.getString(c2.getColumnIndex("string")), "string");
        assertEquals(c2.getLong(c2.getColumnIndex("integer")), 1);
    }

    @Test
    public void shouldHaveXPathSupport() throws IllegalStateException, FileNotFoundException,
            IOException {
        SimpleXMLCursor cursor = new SimpleXMLCursor.Builder()
                .withMappedField("hello", "test/value/").withMappedField("test", "array/array_item/string/").withRootNode("response").create();
        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simple.xml")));
        cursor.handleResponse(response);
        assertTrue(Arrays.asList(cursor.getColumnNames()).contains("hello"));
        assertTrue(cursor.moveToFirst());
        assertEquals(2, cursor.getInt(cursor.getColumnIndex("hello")));
        assertEquals("string", cursor.getString(cursor.getColumnIndex("test")));
    }
}
