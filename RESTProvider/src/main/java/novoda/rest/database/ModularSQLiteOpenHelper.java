
package novoda.rest.database;

import novoda.rest.utils.DatabaseUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class ModularSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_STATUS_NAME = "call_status";

    private static final String CREATE_TABLE_STATUS = "CREATE TABLE " + TABLE_STATUS_NAME
            + " IF NOT EXISTS " + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " uri TEXT NOT NULL, " + "status INTEGER NOT NULL, " + "createdAt INTEGER NOT NULL, "
            + "updatedAt INTEGER);";

    public ModularSQLiteOpenHelper(Context context) {
        super(context, new StringBuilder(context.getApplicationInfo().packageName).append(".db")
                .toString(), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STATUS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No upgrade
    }

    public void create(SQLTableCreator creator) {
        getWritableDatabase().compileStatement(DatabaseUtils.getCreateStatement(creator))
                .execute();
    }

}
