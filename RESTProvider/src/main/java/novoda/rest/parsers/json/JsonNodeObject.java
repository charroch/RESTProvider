
package novoda.rest.parsers.json;

import novoda.rest.parsers.Node;

import org.codehaus.jackson.JsonNode;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class JsonNodeObject extends Node<JsonNode> {

    private boolean isArray;

    private String tableName;

    public JsonNodeObject(JsonNode node) {
        data = node;
        isArray = node.isArray();
    }

    @Override
    public ContentValues getContentValue() {
        ContentValues values = new ContentValues();
        Iterator<String> fields = data.getFieldNames();
        String field = null;
        while (fields.hasNext()) {
            field = fields.next();
            // Do some XPath stuff here
            if (getOptions().children.containsKey(field)){
                continue;
            }
            values.put(field, data.path(field).getValueAsText());
        }
        return values;
    }

    @Override
    public String getTableName() {
        if (tableName != null)
            return tableName;
        else if (getOptions().rootNode != null)
            return getOptions().rootNode;
        throw new IllegalStateException("Can not have a node without a table name");
    }

    @Override
    public boolean shouldInsert() {
        return true;
    }

    @Override
    public int getCount() {
        if (data.isArray()) {
            return data.size();
        } else if (data != null) {
            return 1;
        }
        return 0;
    }

    @Override
    public Node<JsonNode> getNode(int index) {
        if (isArray) {
            JsonNodeObject o = new JsonNodeObject(data.get(index));
            o.setParent(this.getParent());
            Options options = getOptions();
            options.rootNode = null;
            o.applyOptions(options);
            return o;
        } else {
            return this;
        }
    }

    @Override
    public void applyOptions(Options options) {
        this.setOptions(options);
        if (options.rootNode != null) {
            data = data.path(options.rootNode);
            isArray = data.isArray();
        }
        if (options.children != null) {
            if (isArray) {
                // do nothing
            } else {
                applyOptionsChild(options, this);
            }
        }
        if (options.table != null) {
            this.tableName = options.table;
        }
    }
    
    public void applyOptionsChild(Options options, JsonNodeObject node) {
        for (Entry<String, Options> entry : options.children.entrySet()) {
            JsonNode child = node.data.path(entry.getKey());
            JsonNodeObject childNode = new JsonNodeObject(child);
            childNode.applyOptions(entry.getValue());
            node.addChild(childNode);
        }
    }
    
    @Override
    public String[] getColumns() {
        Iterator<String> fields = data.getFieldNames();
        List<String> values = new ArrayList<String>();
        String field = null;
        while (fields.hasNext()) {
            field = fields.next();
            // Do some XPath stuff here
            if (getOptions().children.containsKey(field)){
                continue;
            }
            values.add(field);
        }
        return values.toArray(new String[]{});
    }
}
