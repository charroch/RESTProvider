
package novoda.rest.database;

import android.net.Uri;

/**
 * Definition of the methods needed to create a table out of an object - will
 * mostly likely be a cursor.
 * 
 * @author Carl-Gustaf Harroch
 */
public interface SQLTableCreator {
    
    /**
     * 
     * @return the primary key field within the cursor. If null, will use _id which auto increments
     */
    public String getPrimaryKey();
    
    public boolean shouldPKAutoIncrement();

    public SQLiteType getType(final String field);

    public String getParentTableName(final Uri uri);

    public String getParentID(final Uri uri);

    public boolean isNullAllowed(final String field);

    public boolean isUnique(final String field);

    public boolean isOneToMany();
    
    public boolean shouldIndex(final String field);
    
    public SQLiteConflictClause onConflict(final String field);
    
    public String getTableName(final Uri uri);
    
    public String[] getTableFields();
}
