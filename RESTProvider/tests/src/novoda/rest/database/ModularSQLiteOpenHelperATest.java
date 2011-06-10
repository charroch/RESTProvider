
package novoda.rest.database;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class ModularSQLiteOpenHelperATest extends AndroidTestCase {

    private ModularSQLiteOpenHelper db;

    @Override
    protected void setUp() throws Exception {
        setContext(new RenamingDelegatingContext(getContext(), "mod_"));
        db = new ModularSQLiteOpenHelper(getContext());
        assertNotNull(db);
        super.setUp();
    }

    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
    }

    public void testCreationOfDB() throws Exception {
        assertTrue(db.getColumnsForTable("test").isEmpty());
        assertFalse(db.getColumnsForTable("call_status").isEmpty());
    }
    
    public void testTableCreated() throws Exception {
        
    }
}
