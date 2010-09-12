
package novoda.rest.clag.command;

import novoda.rest.clag.ServiceDescription;
import novoda.rest.clag.ServiceDescriptionParser;
import novoda.rest.context.command.Command;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.database.Persister;
import novoda.rest.database.SQLiteTableCreator;

import org.codehaus.jackson.JsonNode;

public class ClagInitCommand implements Command<JsonNode>, Persister {

    private ModularSQLiteOpenHelper sqlite;

    @Override
    public void execute(JsonNode data) {
        ServiceDescriptionParser parser = new ServiceDescriptionParser();
        ServiceDescription description = parser.parse(data);
        for (SQLiteTableCreator creator : description.schemas) {
            sqlite.createTable(creator);
        }
        sqlite.close();
    }

    @Override
    public void setPersister(ModularSQLiteOpenHelper sqlite) {
        this.sqlite = sqlite;
    }
}
