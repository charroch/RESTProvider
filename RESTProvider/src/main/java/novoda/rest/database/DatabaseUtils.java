
package novoda.rest.database;

import novoda.rest.services.ETag;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;

public class DatabaseUtils {

    private static String table = "call_status";

    private static String query_column = "uri";

    private static String etag_column = "etag";

    private static String last_modified_column = "lastModified";

    public static ETag etagForQuery(SQLiteDatabase db, String query) {
        Parcel p = Parcel.obtain();
        Cursor cursor = db.query(table, new String[] {
                etag_column, last_modified_column
        }, new StringBuilder(query_column).append("=?").toString(), new String[] {
            query
        }, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                p.writeString(cursor.getString(0));
                p.writeString(cursor.getString(1));
            }
            return ETag.CREATOR.createFromParcel(p);
        } finally {
            p.recycle();
            cursor.close();
        }
    }
}
