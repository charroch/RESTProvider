
package novoda.rest.parsers;

import novoda.rest.parsers.NodeFactory.Options;

import org.codehaus.jackson.JsonNode;

import android.content.ContentValues;

import java.util.Iterator;

public class JsonNodeObject extends Node<JsonNode> {

    private boolean isArray;

    private int arrayIndex;

    private String tableName;

    public JsonNodeObject(JsonNode node) {
        this(node, 0);
    }

    public JsonNodeObject(JsonNode node, int index) {
        this(node, index, "");
    }

    public JsonNodeObject(JsonNode node, int index, String tableName) {
        data = node;
        isArray = node.isArray();
        arrayIndex = index;
        this.tableName = tableName;
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
        if (tableName != null)
            return tableName;
        else if (getOptions().rootNode != null)
            return getOptions().rootNode;
        throw new IllegalStateException("Can not have a node without a table name");
    }

    public String[] getColumns() {
        data.getFieldNames();
        return null;
    }

    @Override
    public boolean hasNext() {
        return isArray;
    }

    @Override
    public Node<JsonNode> next() {
        return new JsonNodeObject(this.data, arrayIndex + 1);
    }

    @Override
    boolean shouldInsert() {
        return true;
    }

    @Override
    void applyOptions(Options option) {
        if (option.rootNode != null) {
            data.path(option.rootNode);
        }
    }
}
