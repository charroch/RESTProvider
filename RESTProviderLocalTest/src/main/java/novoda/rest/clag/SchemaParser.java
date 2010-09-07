
package novoda.rest.clag;

import novoda.rest.database.SQLiteConflictClause;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteType;

import org.codehaus.jackson.JsonNode;

import java.util.HashMap;
import java.util.Iterator;

public class SchemaParser implements SQLiteTableCreator {

    private static final String FIELD_TYPE = "type";

    private static final String FIELD_KEY = "key";

    private static final String COLUMNS = "columns";

    private static final String TABLE_NAME = "name";

    private static final String FIELD_NAME = "name";

    private JsonNode json;

    private JsonNode columns;

    private HashMap<String, FieldOptions> map;

    public class FieldOptions {
        public SQLiteType type = SQLiteType.TEXT;

        public boolean notNull = true;

        public boolean unique = false;

        public boolean shouldIndex = false;
    }

    public SchemaParser(JsonNode json) {
        this.json = json;
        columns = json.path(COLUMNS);
        map = new HashMap<String, FieldOptions>(columns.size());
        init();
    }

    private void init() {
        JsonNode temp;
        Iterator<JsonNode> i = columns.getElements();
        while (i.hasNext()) {
            temp = i.next();
            FieldOptions options = new FieldOptions();

            // getting type
            if (!temp.path(FIELD_TYPE).isMissingNode()
                    || temp.path(FIELD_TYPE).getValueAsText() != null) {
                options.type = SQLiteType.valueOf(temp.path(FIELD_TYPE).getValueAsText()
                        .toUpperCase());
                if (options.type == null || options.type == SQLiteType.NULL) {
                    options.type = SQLiteType.TEXT;
                }
            }

            // TODO getting unique
            // TODO getting should index
            // TODO getting not null

            // special remote id
            if (!temp.path(FIELD_KEY).isMissingNode()) {
                options.unique = true;
                options.notNull = false;
            }

            // TODO move it to the DatabaseUtils
            map.put("\"" + temp.path(FIELD_NAME).getTextValue() + "\"", options);
        }
    }

    @Override
    public String getParentColumnName() {
        return null;
    }

    @Override
    public String getParentPrimaryKey() {
        return null;
    }

    @Override
    public String getParentTableName() {
        return null;
    }

    @Override
    public SQLiteType getParentType() {
        return null;
    }

    @Override
    public String getPrimaryKey() {
        // Will autocreate
        return null;
    }

    @Override
    public String[] getTableFields() {
        return map.keySet().toArray(new String[] {});
    }

    @Override
    public String getTableName() {
        return json.path(TABLE_NAME).getTextValue();
    }

    @Override
    public String[] getTriggers() {
        return null;
    }

    @Override
    public SQLiteType getType(String field) {
        return map.get(field).type;
    }

    @Override
    public boolean isNullAllowed(String field) {
        return map.get(field).notNull;
    }

    @Override
    public boolean isOneToMany() {
        return false;
    }

    @Override
    public boolean isUnique(String field) {
        return map.get(field).unique;
    }

    @Override
    public SQLiteConflictClause onConflict(String field) {
        return null;
    }

    @Override
    public boolean shouldIndex(String field) {
        return false;
    }

    @Override
    public boolean shouldPKAutoIncrement() {
        return true;
    }

    public static SchemaParser from(final JsonNode json) {
        SchemaParser parser = new SchemaParser(json);
        return parser;
    }
}
