package novoda.rest.parsers;

import org.codehaus.jackson.JsonNode;

import android.content.ContentValues;

import java.util.Iterator;

public class JsonNodeObject extends Node<JsonNode> {

    public JsonNodeObject(JsonNode node) {
        data = node;
        if (node.isArray()) {
            // 
            //node.
        } else {
            // do something else
        }
         
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
}
