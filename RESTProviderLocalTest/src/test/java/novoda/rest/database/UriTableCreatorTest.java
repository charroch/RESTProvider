
package novoda.rest.database;

import static org.junit.Assert.assertEquals;
import novoda.rest.RESTProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import android.net.Uri;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class UriTableCreatorTest {
    private UriTableCreator cut;

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        RESTProvider.DEBUG = false;
        cut = new UriTableCreator(Uri.parse("content://uri/parent/2/child"));
    }

    @Test
    public void testParentTableName() throws Exception {
        assertEquals(cut.getParentColumnName(), "parent_id");
        assertEquals(cut.getTableName(), "child");
        
        cut.setUri(Uri.parse("content://uri/parent/#/child"));
        assertEquals(cut.getParentColumnName(), "parent_id");
        assertEquals(cut.getTableName(), "child");

        cut.setUri(Uri.parse("content://uri/parent/#/child/"));
        assertEquals(cut.getParentColumnName(), "parent_id");
        assertEquals(cut.getTableName(), "child");
        
        cut.setUri(Uri.parse("content://uri/parent"));
        assertEquals(cut.getTableName(), "parent");
    }

    @Test
    public void testTrigger() throws Exception {
        final String expected = "CREATE TRIGGER delete_parent BEFORE DELETE ON parent FOR EACH ROW BEGIN DELETE from child WHERE parent_id = OLD._id; END;";
        assertEquals(expected, SQLiteUtil.getDeleteTrigger("parent", "_id", "child", "parent_id"));
    }
}
