
package novoda.rest.cursors;

import android.database.AbstractCursor;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ResponseCursor extends AbstractCursor {

    private static final String TAG = ResponseCursor.class.getSimpleName();

    /* Static variables for the getType() method */
    public static final int STRING = 0;

    public static final int INT = 1;

    public static final int LONG = 2;

    public static final int DOUBLE = 3;

    public static final int FLOAT = 4;

    public static final int SHORT = 5;

    public static final int BOOLEAN = 6;

    public static final int BYTE = 7;

    public static final int CHAR = 8;

    public static final int BYTE_ARRAY = 9;
    /* Static variables for the getType() method */

    public abstract int getType(int column);

    public abstract ResponseCursor getChild(String field);

    public abstract Object get(String field);

    public abstract void parse(InputStream in) throws IOException;

    public abstract void parseChildren(ResponseCursor parent, String field);

    protected CursorController.CursorParams params;

    protected String[] columnName;

    private String[] childrenColumnNames;

    protected ResponseCursor() {
    }

    public Object get(int column) {
        final String field = columnName[column];
        if (field.equals("_id")) {
            return mPos;
        }

        if (childrenColumnNames != null && Arrays.asList(childrenColumnNames).contains(field)) {
            // We hit a children.
            Log.w(TAG, "you are requesting a child. Will output the child as string");
        }

        return get((params.mapper.containsKey(field)) ? params.mapper.get(field) : field);
    }

    public void init() {

        /*
         * Getting the column names from concrete - this should be the ones from
         * the XML/JSON applicable to that format (i.e. id should be the actual
         * id field not _id)
         */
        columnName = getColumnNames();
        if (params.withAutoId) {
            String[] tmp = new String[columnName.length + 1];
            System.arraycopy(columnName, 0, tmp, 0, columnName.length);
            tmp[tmp.length - 1] = "_id";
            columnName = tmp;
        }

        /*
         * Getting the root of all the children elements.
         */
        if (params.withChildren != null) {
            List<String> childRoots = new ArrayList<String>(params.withChildren.size());
            for (ResponseCursor child : params.withChildren) {
                childRoots.add(child.params.rootName);
            }
            childrenColumnNames = childRoots.toArray(new String[] {});
            childRoots = null;
        }
    }

    @Override
    public String getString(int column) {
        return String.valueOf(get(column));
    }

    @Override
    public short getShort(int column) {
        Object value = get(column);
        return (value instanceof String) ? Short.valueOf((String) value) : ((Number) value)
                .shortValue();
    }

    @Override
    public int getInt(int column) {
        Object value = get(column);
        return (value instanceof String) ? Integer.valueOf((String) value) : ((Number) value)
                .intValue();
    }

    @Override
    public long getLong(int column) {
        Object value = get(column);
        return (value instanceof String) ? Long.valueOf((String) value) : ((Number) value)
                .longValue();
    }

    @Override
    public float getFloat(int column) {
        Object value = get(column);
        return (value instanceof String) ? Float.valueOf((String) value) : ((Number) value)
                .floatValue();
    }

    @Override
    public double getDouble(int column) {
        Object value = get(column);
        return (value instanceof String) ? Double.valueOf((String) value) : ((Number) value)
                .doubleValue();
    }

    @Override
    public boolean isNull(int column) {
        return get(column) == null;
    }
}
