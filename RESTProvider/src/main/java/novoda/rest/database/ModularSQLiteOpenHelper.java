
package novoda.rest.database;

import novoda.rest.utils.DatabaseUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ModularSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = ModularSQLiteOpenHelper.class.getSimpleName();

    private static final String SELECT_TABLES_NAME = "SELECT name FROM sqlite_master WHERE type='table';";

    public static final String TABLE_STATUS_NAME = "call_status";

    private static final String CREATE_TABLE_STATUS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_STATUS_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " uri TEXT NOT NULL, " + "status INTEGER NOT NULL, " + "createdAt INTEGER NOT NULL, "
            + "updatedAt INTEGER);";

    private List<String> createdTable = new ArrayList<String>();

    private Map<String, SQLiteTableCreator> createStatements = new HashMap<String, SQLiteTableCreator>();

    private int dbVersion = 1;

    public ModularSQLiteOpenHelper(Context context) {
        super(context, new StringBuilder(context.getApplicationInfo().packageName).append(".db")
                .toString(), null, 1);

        Cursor cur = getReadableDatabase().rawQuery(SELECT_TABLES_NAME, null);
        while (cur.moveToNext()) {
            createdTable.add(cur.getString(0));
        }
        cur.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STATUS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "upgrading database from version " + oldVersion + " to " + newVersion);
        for (Entry<String, SQLiteTableCreator> entry : createStatements.entrySet()) {
            if (createdTable.contains(entry.getKey())) {
                Log.v(TAG, "Table " + entry.getKey() + " already in DB.");
            } else {
                Log.v(TAG, "Creating table: " + entry.getKey());
                db.execSQL(DatabaseUtils.getCreateStatement(entry.getValue()));
                createdTable.add(entry.getKey());
            }
        }
    }

    public void createTable(SQLiteTableCreator creator) {
        if (createdTable.contains(creator.getTableName())) {
            Log.v(TAG, "Table " + creator.getTableName() + " already in DB.");
        } else {
            createStatements.put(creator.getTableName(), creator);
            getWritableDatabase().needUpgrade(++dbVersion);
        }
    }
}
