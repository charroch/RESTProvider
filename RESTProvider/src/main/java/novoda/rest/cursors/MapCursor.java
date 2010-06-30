package novoda.rest.cursors;

import android.database.AbstractCursor;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapCursor<T> extends AbstractCursor {

    private LinkedHashMap<String, T> internalMap;

    private String[] columnNames = new String[]{};
    
    public MapCursor(Map<String, T> map) {
        internalMap = new LinkedHashMap<String, T>(map);
        columnNames = internalMap.keySet().toArray(new String[]{});
    }
    
    // AbstractCursor implementation.
    public int getCount() {
        return 1;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public T get(int column) {
        return internalMap.get(columnNames[column]);
    }
    
    public String getString(int column) {
        return String.valueOf(get(column));
    }

    public short getShort(int column) {
        Object value = get(column);
        return (value instanceof String)
                ? Short.valueOf((String) value)
                : ((Number) value).shortValue();
    }

    public int getInt(int column) {
        Object value = get(column);
        return (value instanceof String)
                ? Integer.valueOf((String) value)
                : ((Number) value).intValue();
    }

    public long getLong(int column) {
        Object value = get(column);
        return (value instanceof String)
                ? Long.valueOf((String) value)
                : ((Number) value).longValue();
    }

    public float getFloat(int column) {
        Object value = get(column);
        return (value instanceof String)
                ? Float.valueOf((String) value)
                : ((Number) value).floatValue();
    }

    public double getDouble(int column) {
        Object value = get(column);
        return (value instanceof String)
                ? Double.valueOf((String) value)
                : ((Number) value).doubleValue();
    }

    public boolean isNull(int column) {
        return get(column) == null;
    }
}
