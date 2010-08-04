
package novoda.rest.database;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class UriTableCreator implements SQLiteTableCreator {

    private Uri uri;

    private List<String> pathSegments;

    public UriTableCreator() {
    }

    public UriTableCreator(final Uri uri) {
        this.setUri(uri);
    }

    @Override
    public String getParentColumnName() {
        if (isOneToMany()) {
            return new StringBuffer(pathSegments.get(pathSegments.size() - 3)).append("_id")
                    .toString();
        }
        return null;
    }

    @Override
    public SQLiteType getParentType() {
        return SQLiteType.INTEGER;
    }

    @Override
    public String getPrimaryKey() {
        return "_id";
    }

    @Override
    public String getTableName() {
        return pathSegments.get(pathSegments.size() - 1);
    }

    @Override
    public String[] getTriggers() {
        return null;
    }

    @Override
    public SQLiteType getType(String field) {
        return SQLiteType.TEXT;
    }

    @Override
    public boolean isNullAllowed(String field) {
        return true;
    }

    @Override
    public boolean isOneToMany() {
        return (pathSegments.size() > 2);
    }

    @Override
    public boolean isUnique(String field) {
        if (field.equals(getPrimaryKey())) {
            return true;
        }
        return false;
    }

    @Override
    public SQLiteConflictClause onConflict(String field) {
        return SQLiteConflictClause.REPLACE;
    }

    @Override
    public boolean shouldIndex(String field) {
        if (field.equals(getPrimaryKey())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldPKAutoIncrement() {
        if (getPrimaryKey() == null) {
            return true;
        }
        return false;
    }

    public void setUri(Uri uri) {
        // just to overcome an issue with # sign and getPath would only result
        // in the path after #
        this.uri = Uri.parse(uri.toString().replace('#', '1'));
        pathSegments = new ArrayList<String>(Arrays.asList(this.uri.getPath().split("/")));
        pathSegments.remove(0);
    }

    public Uri getUri() {
        return uri;
    }
}
