package novoda.rest.providers;

import java.sql.SQLException;

import novoda.rest.database.ModularSQLiteOpenHelper;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


public abstract class ModularProvider extends ContentProvider {

	private ModularSQLiteOpenHelper dbHelper;

    public void test(final Cursor cursor, Uri uri) {
		String[] columnNames = cursor.getColumnNames();
		String dbTable = uri.getLastPathSegment();
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
	    try {
	    dbHelper.getWritableDatabase().insertOrThrow(uri.getLastPathSegment(), "", values);
	    }catch (Exception e){
	        
	    }
		return null;
	}

	@Override
	public boolean onCreate() {
	    dbHelper = new ModularSQLiteOpenHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
