
package novoda.rest.net;

import novoda.rest.database.SQLiteInserter;

import org.codehaus.jackson.JsonNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class JsonInserter implements SQLiteInserter {

    private JsonNode node;

    public JsonInserter(JsonNode node) {
        this.node = node;
    }

    @Override
    public Object get(String field, int index) {
        JsonNode o = node;
        if (node.isArray()) {
            o = node.get(index);
        }
        return o.get(field).getValueAsText();
    }

    @Override
    public String[] getColumns() {
        Iterator<String> fieldNames = node.getFieldNames();
        List<String> s = new ArrayList<String>();
        while (fieldNames.hasNext()) {
            s.add(fieldNames.next());
        }
        return s.toArray(new String[] {});
    }

    @Override
    public int getCount() {
        if (node.isArray())
            return node.size();
        else if (node == null)
            return -1;
        else
            return 1;
    }

    @Override
    public int getInsertIndex(String field) {
        return getColumnIndex(field);
    }

    private int getColumnIndex(String field) {
        String columnNames[] = getColumns();
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            if (columnNames[i].equalsIgnoreCase(field)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String getInsertStatement(String tableName) {
        String columns = Arrays.toString(getColumns());
        StringBuilder builder = new StringBuilder().append("INSERT INTO ").append(tableName)
                .append("(").append(columns.substring(1, columns.length() - 1)).append(") VALUES(");
        for (int i = 0; i < getColumns().length; i++) {
            builder.append("?,");
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append(");");
        return builder.toString();
    }
}
