package novoda.rest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ModularSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String CREATE_TABLE_STATUS = "";

	public ModularSQLiteOpenHelper(Context context) {
		super(context, new StringBuilder(context.getApplicationInfo().name)
				.append(".db").toString(), null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_STATUS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
	}
}
