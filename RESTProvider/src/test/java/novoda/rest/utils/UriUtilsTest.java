
package novoda.rest.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import android.net.Uri;

public class UriUtilsTest {

    @Test
    public void testItem() throws Exception {
        Uri uri = Uri.parse("content://test.com/item/1");
        
        assertTrue(UriUtils.isItem("", uri));
        assertEquals("item", UriUtils.getTableName("", uri));
        
        assertTrue(UriUtils.isItem(uri));
        assertEquals("item", UriUtils.getTableName(uri));
    }
    
    @Test
    public void testRootNotNull() throws Exception {
        Uri uri = Uri.parse("content://test.com/root/item/1");
        
        assertTrue(UriUtils.isItem("root", uri));
        assertEquals("item", UriUtils.getTableName("root", uri));
        
        uri = Uri.parse("content://test.com/root/root2/item/1");
        
        assertTrue(UriUtils.isItem("root/root2", uri));
        assertEquals("item", UriUtils.getTableName("root/root2", uri));
    }
}
