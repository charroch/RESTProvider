
package novoda.rest.database;

import static org.junit.Assert.*;
import novoda.rest.RESTProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import android.net.Uri;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class UriQueryBuilderTest {

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        RESTProvider.DEBUG = false;
    }

    @Test
    public void testParentTableName() throws Exception {
        UriQueryBuilder b = (UriQueryBuilder) UriQueryBuilder.fromUri(Uri
                .parse("content://uri/parent/1/child/2"));
        assertEquals("should have the same table name", "child", b.getTables());

        assertTrue(!b.isDirectory());
        assertTrue(b.isOneToMany());
        assertEquals("_id=2 AND parent_id=1", b.getWhere());

        b.setUri(Uri.parse("content://uri/parent"));
        assertTrue(b.isDirectory());

        assertFalse(b.isOneToMany());

        b.setUri(Uri.parse("content://uri/parent/1"));
        assertFalse(b.isDirectory());
        assertFalse(b.isOneToMany());
        assertEquals(b.getWhere(), "_id=1");
    }
}
