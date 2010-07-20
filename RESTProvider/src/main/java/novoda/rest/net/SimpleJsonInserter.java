package novoda.rest.net;

import org.codehaus.jackson.JsonNode;

import novoda.rest.database.SQLiteType;

public class SimpleJsonInserter extends JsonInserter {

    public SimpleJsonInserter(JsonNode node) {
        super(node);
    }

    @Override
    public SQLiteType getType(String field) {
        return SQLiteType.TEXT;
    }

}
