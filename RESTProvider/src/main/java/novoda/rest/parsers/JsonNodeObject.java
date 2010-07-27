
package novoda.rest.parsers;

import org.codehaus.jackson.JsonNode;

import android.content.ContentValues;

import java.util.Iterator;

public class JsonNodeObject extends Node<JsonNode> {

    private boolean isArray;

    private int arrayIndex;

    public JsonNodeObject(JsonNode node) {
        data = node;
        if (node.isArray()) {
            // 
            // node.
        } else {
            // do something else
        }

    }

    public JsonNodeObject(JsonNode node, int index) {
        arrayIndex = index;
    }

    @Override
    ContentValues getContentValue() {
        ContentValues values = new ContentValues();
        Iterator<String> fields = data.getFieldNames();
        String field;
        while ((field = fields.next()) != null) {
            values.put(field, data.path(field).getTextValue());
        }
        return values;
    }

    @Override
    String getTableName() {
        return "test";
    }

    public String[] getColumns() {
        data.getFieldNames();
        return null;
    }

    @Override
    public boolean hasNext() {
        return data.isArray();
    }

    @Override
    public Node<JsonNode> next() {
        return new JsonNodeObject(this.data, arrayIndex + 1);
    }

    @Override
    boolean shouldInsert() {
        return true;
    }
}
