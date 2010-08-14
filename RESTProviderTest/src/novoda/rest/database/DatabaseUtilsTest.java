
package novoda.rest.database;

import novoda.rest.services.ETag;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class DatabaseUtilsTest extends AndroidTestCase {

    private SQLiteDatabase db;

    @Override
    protected void setUp() throws Exception {
        db = new ModularSQLiteOpenHelper(getContext()).getWritableDatabase();
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        for (String d : getContext().databaseList()) {
            getContext().deleteDatabase(d);
        }
        super.tearDown();
    }
    
    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
    }
    
    public void testGettingEtagIfEmpty() throws Exception {
        ETag e = DatabaseUtils.etagForQuery(db, "http://test");
        assertEquals(e.etag, "");
        assertEquals(e.lastModified, "");
    }
}
