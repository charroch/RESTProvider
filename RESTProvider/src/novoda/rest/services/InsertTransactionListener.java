
package novoda.rest.services;

import novoda.rest.database.SQLiteTableCreatorWrapper;
import android.content.ContentValues;
import android.net.Uri;

public interface InsertTransactionListener {
    public void onPreTableCreate(SQLiteTableCreatorWrapper creator);

    public void onPreInsert(final Uri uri, ContentValues values);

    public void onPostInsert(final Uri uri, long id);

    public void onFinish(final Uri uri);
}
