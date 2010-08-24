
package novoda.rest.uri;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import android.net.Uri;

public class UriMapperTest {

    private static final String TEST_COM_URL = "http://test.com";

    private static final String TEST_COM_URL_2 = "http://test2.com";

    private static final Uri TEST_COM_URI = Uri.parse("content://test.com/path");

    private static final Uri TEST_COM_URI_PATH = Uri.parse("content://test.com/path/second");

    private static final Uri TEST_COM_URI_PATH_WITH_HASH = Uri.parse("content://test.com/path/#");

    private static final Uri TEST_COM_URI_PATH_WITH_HASH_IDeD = Uri
            .parse("content://test.com/path/2");

    private static final Uri TEST_COM_URI_PATH_WITH_STAR = Uri.parse("content://test.com/path/*");

    private static final Uri TEST_COM_URI_PATH_WITH_STAR_IDeD = Uri
            .parse("content://test.com/path/thisissometest");

    private static UriMapper<String> mapper;

    @BeforeClass
    public static void testSetup() {
        mapper = new UriMapper<String>();
    }

    @Before
    public void setup() {
        mapper.clear();
    }

    @Test
    public void testSimpleInsert() {
        mapper.map(TEST_COM_URI, TEST_COM_URL);
        assertEquals(mapper.getUrl(TEST_COM_URI), TEST_COM_URL);
    }

    @Test
    public void testSimpleInsertWithPath() {
        mapper.map(TEST_COM_URI_PATH, TEST_COM_URL);
        assertEquals(mapper.getUrl(TEST_COM_URI_PATH), TEST_COM_URL);
    }

    @Test
    public void testMultipleInsert() {
        mapper.map(TEST_COM_URI, TEST_COM_URL_2);
        mapper.map(TEST_COM_URI_PATH, TEST_COM_URL);
        assertEquals(mapper.getUrl(TEST_COM_URI), TEST_COM_URL_2);
        assertEquals(mapper.getUrl(TEST_COM_URI_PATH), TEST_COM_URL);
    }

    @Test
    public void testSimpleInsertWithPathAndID() {
        mapper.map(TEST_COM_URI_PATH_WITH_HASH, TEST_COM_URL);
        assertEquals(mapper.getUrl(TEST_COM_URI_PATH_WITH_HASH), TEST_COM_URL);
        assertEquals(mapper.getUrl(TEST_COM_URI_PATH_WITH_HASH_IDeD), TEST_COM_URL);
    }

    @Test
    public void testInsertWithStarAndId() {
        mapper.map(TEST_COM_URI_PATH_WITH_STAR, TEST_COM_URL);
        assertEquals(mapper.getUrl(TEST_COM_URI_PATH_WITH_STAR), TEST_COM_URL);
        assertEquals(mapper.getUrl(TEST_COM_URI_PATH_WITH_STAR_IDeD), TEST_COM_URL);
    }
}
