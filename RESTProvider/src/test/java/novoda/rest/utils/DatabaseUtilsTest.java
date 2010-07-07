
package novoda.rest.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteConflictClause;
import novoda.rest.database.SQLiteType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.ContentValues;
import android.net.Uri;

import java.io.IOException;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseUtilsTest {

    @Mock
    Map<String, String> params;

    @Mock
    SQLiteTableCreator p;

    @Mock
    Uri uri;

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void contentValuestoTableCreate() throws Exception {
        ContentValues values = new ContentValues();
        values.put("test", "Test");
        String result = DatabaseUtils.contentValuestoTableCreate(values, "test");
        assertEquals("CREATE TABLE test (test TEXT);", result);

        values.put("test2", "t");
        result = DatabaseUtils.contentValuestoTableCreate(values, "test");
        assertEquals("CREATE TABLE test (test TEXT, test2 TEXT);", result);
    }

    @Test
    public void creatortoTableCreate() throws Exception {

        String expected = "CREATE TABLE IF NOT EXISTS test (_id INTEGER PRIMARY KEY AUTOINCREMENT , f TEXT);";

        String[] fields = new String[] {
            "f"
        };
        when(p.isNullAllowed("f")).thenReturn(true);
        when(p.getPrimaryKey()).thenReturn(null);
        when(p.getTableFields()).thenReturn(fields);
        when(p.getTableName()).thenReturn("test");
        when(p.getType("f")).thenReturn(SQLiteType.TEXT);

        String sql = DatabaseUtils.getCreateStatement(p);
        assertEquals(expected, sql);

        expected = "CREATE TABLE IF NOT EXISTS test (_id INTEGER PRIMARY KEY AUTOINCREMENT , f TEXT NOT NULL);";

        fields = new String[] {
            "f"
        };
        when(p.isNullAllowed("f")).thenReturn(false);
        when(p.getPrimaryKey()).thenReturn(null);
        when(p.getTableFields()).thenReturn(fields);
        when(p.getType("f")).thenReturn(SQLiteType.TEXT);

        sql = DatabaseUtils.getCreateStatement(p);
        assertEquals(expected, sql);

        expected = "CREATE TABLE IF NOT EXISTS test (_id INTEGER PRIMARY KEY AUTOINCREMENT , f TEXT NOT NULL, f2 INTEGER);";

        fields = new String[] {
                "f", "f2"
        };
        when(p.isNullAllowed("f")).thenReturn(false);
        when(p.isNullAllowed("f2")).thenReturn(true);
        when(p.getPrimaryKey()).thenReturn(null);
        when(p.getTableFields()).thenReturn(fields);
        when(p.getType("f")).thenReturn(SQLiteType.TEXT);
        when(p.getType("f2")).thenReturn(SQLiteType.INTEGER);

        sql = DatabaseUtils.getCreateStatement(p);
        assertEquals(expected, sql);

        expected = "CREATE TABLE IF NOT EXISTS test (f TEXT PRIMARY KEY , f2 INTEGER);";

        fields = new String[] {
                "f", "f2"
        };
        when(p.isNullAllowed("f")).thenReturn(false);
        when(p.isNullAllowed("f2")).thenReturn(true);
        when(p.getPrimaryKey()).thenReturn("f");
        when(p.getTableFields()).thenReturn(fields);
        when(p.getType("f")).thenReturn(SQLiteType.TEXT);
        when(p.getType("f2")).thenReturn(SQLiteType.INTEGER);

        sql = DatabaseUtils.getCreateStatement(p);
        assertEquals(expected, sql);

        expected = "CREATE TABLE IF NOT EXISTS test (f TEXT PRIMARY KEY , f2 INTEGER UNIQUE);";

        fields = new String[] {
                "f", "f2"
        };
        when(p.isNullAllowed("f")).thenReturn(false);
        when(p.isUnique("f2")).thenReturn(true);
        when(p.isNullAllowed("f2")).thenReturn(true);
        when(p.getPrimaryKey()).thenReturn("f");
        when(p.getTableFields()).thenReturn(fields);
        when(p.getType("f")).thenReturn(SQLiteType.TEXT);
        when(p.getType("f2")).thenReturn(SQLiteType.INTEGER);

        sql = DatabaseUtils.getCreateStatement(p);
        assertEquals(expected, sql);
        
        expected = "CREATE TABLE IF NOT EXISTS test (f TEXT PRIMARY KEY , f2 INTEGER UNIQUE ON CONFLICT REPLACE);";
        fields = new String[] {
                "f", "f2"
        };
        when(p.onConflict("f2")).thenReturn(SQLiteConflictClause.REPLACE);
        sql = DatabaseUtils.getCreateStatement(p);
        assertEquals(expected, sql);
    }
}
