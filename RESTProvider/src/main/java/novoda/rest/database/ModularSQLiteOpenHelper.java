
package novoda.rest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class ModularSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_STATUS_NAME = "call_status";

    private static final String CREATE_TABLE_STATUS = "CREATE TABLE " + TABLE_STATUS_NAME
            + " IF NOT EXISTS " + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + " uri TEXT NOT NULL, " + "status INTEGER NOT NULL, " + "createdAt INTEGER NOT NULL, "
            + "updatedAt INTEGER);";

    private List<SQLiteStatement> statements = new ArrayList<SQLiteStatement>();

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

    public void create(SQLTableCreator creator, Uri uri) {
        String sql = "CREATE TABLE " + creator.getParentTableName(uri) + " IF NOT EXISTS " + "("
                + creator.getPrimaryKey() + " " + creator.getType(creator.getPrimaryKey())
                + " PRIMARY KEY ";
    }
    
    public SQLiteStatement getCreateStatement(SQLTableCreator creator, Uri uri) {
        return null;
    }
}
