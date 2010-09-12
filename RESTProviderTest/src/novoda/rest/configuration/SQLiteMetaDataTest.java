
package novoda.rest.configuration;

import novoda.rest.test.R;

import android.test.InstrumentationTestCase;

public class SQLiteMetaDataTest extends InstrumentationTestCase {

    public SQLiteMetaDataTest() {
        super();
    }

    public void testGettingDatabaseName() throws Exception {
        SQLiteMetaData data = new SQLiteMetaData(getInstrumentation().getContext(),
                getInstrumentation().getContext().getResources().getXml(R.xml.sqlitemetadata));
        assertEquals(data.databaseName, "d.db"); 
        assertEquals(data.tables.size(), 1);
    }
}
