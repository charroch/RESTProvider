
package novoda.rest.database;

import novoda.rest.context.CallContext;

import android.net.Uri;
import android.util.Pair;

public interface CachingStrategy {

    /**
     * This basically drops all values within the table and insert new ones.
     */
    public static int REPLACE = 0;

    /**
     * <p>
     * Append the new values to the DB using the primary key and other unique
     * features defined in the SQLiteTableCreator.
     * </p>
     * <p>
     * If a field is declared unique within the table, it will use the conflict
     * strategy defined in the SQLiteTableCreator.
     * </p>
     */
    public static int APPEND = 1;

    /**
     * Does not cache. Just returns a Response into a Cursor. NOT SUPPORTED YET
     * TODO mark the status_call table as transient call.
     */
    public static int TRANSIENT = 2;

    /*
     * What to do when new results comes in.
     */
    public int onNewResults(Uri uri);

    public int onNewResults(CallContext context);

    public Pair<String, String[]> getWhereClause(CallContext context);
}
