
package novoda.rest.clag.command;

import novoda.rest.context.command.Command;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.database.Persister;
import novoda.rest.database.SQLiteType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

import android.content.ContentValues;

import java.util.Map;

public class ClagQueryCommand implements Command<JsonNode>, Persister {
    ModularSQLiteOpenHelper sqlite;

    @Override
    public void setPersister(ModularSQLiteOpenHelper sqlite) {
        this.sqlite = sqlite;
    }

    @Override
    public void execute(JsonNode data) {
        ArrayNode array = (ArrayNode) data;
        Map<String, SQLiteType> col = sqlite.getColumnsForTable("Bookation");
        ContentValues values = new ContentValues(col.size());
        for (JsonNode node : array) {
            values.clear();
            for (String s : col.keySet()) {
                if (!s.equals("_id"))
                    ;
                values.put(s, node.path(s).getValueAsText());
            }
            sqlite.getWritableDatabase().insert("Bookation", "", values);
        }
        sqlite.close();
    }

}
