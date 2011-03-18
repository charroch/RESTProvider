
package novoda.lib.rest.uri;

import novoda.lib.rest.uri.UriMapper;
import novoda.lib.rest.uri.UriMapper.Transformer;

import android.net.Uri;

import junit.framework.TestCase;

public class UriMapperTest extends TestCase {

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

    @Override
    protected void setUp() throws Exception {
        mapper = new UriMapper<String>();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        mapper.clear();
        super.tearDown();
    }

    public void testSimpleInsert() {
        mapper.map(TEST_COM_URI, TEST_COM_URL);
        assertEquals(mapper.get(TEST_COM_URI), TEST_COM_URL);
    }

    public void testSimpleInsertWithPath() {
        mapper.map(TEST_COM_URI_PATH, TEST_COM_URL);
        assertEquals(mapper.get(TEST_COM_URI_PATH), TEST_COM_URL);
    }

    public void testMultipleInsert() {
        mapper.map(TEST_COM_URI, TEST_COM_URL_2);
        mapper.map(TEST_COM_URI_PATH, TEST_COM_URL);
        assertEquals(mapper.get(TEST_COM_URI), TEST_COM_URL_2);
        assertEquals(mapper.get(TEST_COM_URI_PATH), TEST_COM_URL);
    }

    public void testSimpleInsertWithPathAndID() {
        mapper.map(TEST_COM_URI_PATH_WITH_HASH, TEST_COM_URL);
        assertEquals(mapper.get(TEST_COM_URI_PATH_WITH_HASH), TEST_COM_URL);
        assertEquals(mapper.get(TEST_COM_URI_PATH_WITH_HASH_IDeD), TEST_COM_URL);
    }

    public void testInsertWithStarAndId() {
        mapper.map(TEST_COM_URI_PATH_WITH_STAR, TEST_COM_URL);
        assertEquals(mapper.get(TEST_COM_URI_PATH_WITH_STAR), TEST_COM_URL);
        assertEquals(mapper.get(TEST_COM_URI_PATH_WITH_STAR_IDeD), TEST_COM_URL);
    }

    public void testNoMatch() {
        mapper.map(TEST_COM_URI_PATH_WITH_STAR, TEST_COM_URL);
        try {
            mapper.get(Uri.parse("content://noavail/test"));
            fail();
        } catch (UriMapper.NoMatchException e) {
            assertTrue(true);
        }
    }

    public void testOutputTransformer() {

        UriMapper.Transformer<String> transformer = new Transformer<String>() {
            @Override
            public String onResponse(Uri uri, String object) {
                return object + uri.getLastPathSegment();
            }
        };

        mapper.map(TEST_COM_URI_PATH_WITH_HASH, "http://test.com/");
        mapper.setTransformer(transformer);
        assertEquals("http://test.com/2", mapper.get(TEST_COM_URI_PATH_WITH_HASH_IDeD));
    }

    public void testDefaultGet() {
        assertEquals("default", mapper.get(TEST_COM_URI_PATH, "default"));
    }
}
