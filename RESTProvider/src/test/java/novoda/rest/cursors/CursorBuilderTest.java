
package novoda.rest.cursors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import novoda.rest.RESTProvider;
import novoda.rest.cursors.CursorBuilder.Builder;
import novoda.rest.cursors.xml.SimpleXMLCursor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import android.database.AbstractCursor;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class CursorBuilderTest {

    @Mock
    HttpResponse response;

    @Mock
    HttpEntity entity;

    @Mock
    StatusLine status;

    @Mock
    Uri uri;

    @Mock
    Uri childUri;

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        RESTProvider.DEBUG = false;
        when(response.getEntity()).thenReturn(entity);
        when(response.getStatusLine()).thenReturn(status);
        when(status.getStatusCode()).thenReturn(200);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void simpleTest() throws Exception {

        when(entity.getContent()).thenReturn(
                new FileInputStream(new File("src/test/resources/simple.xml")));
        
        T2 child = new CursorBuilder.Builder<T2>().withRootNode("array")
                .withNodeName("array_item").withFieldID("integer").build(T2.class);

        AbstractCursor c = new CursorBuilder.Builder<T2>().withFieldID("integer").withRootNode(
                "array").withNodeName("array_item").withChildren(child).create(T2.class).handleResponse(response);

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
}
