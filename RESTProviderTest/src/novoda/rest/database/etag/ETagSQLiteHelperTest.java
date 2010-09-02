
package novoda.rest.database.etag;

import novoda.rest.net.ETag;

import android.database.DatabaseUtils;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.LargeTest;

public class ETagSQLiteHelperTest extends AndroidTestCase {

    private ETagSQLiteHelper helper;

    @Override
    protected void setUp() throws Exception {
        setContext(new RenamingDelegatingContext(getContext(), "etag_"));
        helper = new ETagSQLiteHelper(getContext(), "test.db");
        assertNotNull(helper);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        helper.clear();
        super.tearDown();
    }

    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
    }

    public void testSimpleInsert() throws Exception {
        ETag e = new ETag();
        e.etag = "test";
        e.lastModified = "time";
        assertTrue(helper.insertETagForUri(e, "http://test") > 0);
    }

    public void testGetEtag() throws Exception {
        ETag e = new ETag();
        e.etag = "etag";
        e.lastModified = "time";
        assertTrue(helper.insertETagForUri(e, "http://test") > 0);
        ETag etag = helper.getETag("http://test");
        assertEtagEquals(etag, e);
    }

    public void testConflict() throws Exception {
        ETag e = new ETag();
        e.etag = "etag";
        e.lastModified = "time";
        assertTrue(helper.insertETagForUri(e, "http://test") > 0);
        e.etag = "newEtag";
        assertTrue(helper.insertETagForUri(e, "http://test") > 0);
        assertEtagEquals(helper.getETag("http://test"), e);
    }

    @LargeTest
    public void testMaximumRows() throws Exception {
        for (int i = 0; i < 200; i++) {
            ETag e = new ETag();
            e.etag = "test" + i;
            e.lastModified = "time";
            helper.insertETagForUri(e, generateRandomUri());
        }
        assertEquals(helper.getCount(), 100);
    }

    @LargeTest
    public void testConcurrency() throws Exception {
        Runnable insert = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                do {
                    helper.insertETagForUri(generateRandomETag(), generateRandomUri());
                    i++;
                } while (i < 100);
            }
        };

        Runnable query = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                do {
                    helper.getETag(generateRandomUri());
                    i++;
                } while (i < 100);
            }
        };
        new Thread(insert).start();
        new Thread(query).start();
    }

    protected ETag generateRandomETag() {
        ETag etag = new ETag();
        etag.etag = DatabaseUtils.sqlEscapeString(Long.toHexString(Double.doubleToLongBits(Math
                .random())));
        etag.lastModified = DatabaseUtils.sqlEscapeString(Long.toHexString(Double
                .doubleToLongBits(Math.random())));
        return etag;
    }

    private void assertEtagEquals(ETag etag, ETag e) {
        assertEquals(etag.etag, e.etag);
        assertEquals(etag.lastModified, e.lastModified);
    }

    private String generateRandomUri() {
        StringBuilder builder = new StringBuilder("http://");
        builder.append(Long.toHexString(Double.doubleToLongBits(Math.random())));
        return DatabaseUtils.sqlEscapeString(builder.toString());
    }
}
