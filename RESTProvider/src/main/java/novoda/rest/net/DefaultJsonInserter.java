package novoda.rest.net;

import org.codehaus.jackson.JsonNode;

import novoda.rest.database.SQLiteInserter;
import novoda.rest.database.SQLiteType;

public class DefaultJsonInserter extends JsonInserter {
    
    public DefaultJsonInserter(JsonNode node) {
        super(node);
    }

    @Override
    public SQLiteType getType(String field) {
        return SQLiteType.TEXT;
    }

    @Override
    public short onFailure(int index) {
        return SQLiteInserter.CONTINUE;
    }
}
