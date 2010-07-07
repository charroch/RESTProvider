
package novoda.rest.database;

import android.net.Uri;

public interface CachingStrategy {

    /*
     * This basically drops all values within the table and insert new ones.
     */
    public static int REPLACE = 0;

    /*
     * Append the new values to the DB using the primary key and other unique
     * features defined in the SQLiteTableCreator
     */
    public static int APPEND = 1;

    /*
     * Does not cache. Just returns a Response into a Cursor.
     */
    public static int TRANSIENT = 2;

    /*
     * What to do when new results comes in.
     */
    public int onNewResults(Uri uri);
}
