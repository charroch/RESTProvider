
package novoda.rest.clag;

import novoda.rest.database.SQLiteTableCreator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class ServiceDescriptionParser extends JsonParser<ServiceDescription> {

    @Override
    public ServiceDescription parse(JsonNode node) {
        ServiceDescription description = new ServiceDescription();
        description.name = node.path("name").getTextValue();
        // description.version =
        // node.path("version").getNumberValue().floatValue();
        description.services = getServices((ArrayNode) node.path("services"));
        description.schemas = getSchemas((ArrayNode) node.path("schema"));
        return description;
    }

    private List<SQLiteTableCreator> getSchemas(ArrayNode node) {
        List<SQLiteTableCreator> schemas = new ArrayList<SQLiteTableCreator>(node.size());
        for (int i = 0; i < node.size(); i++) {
            schemas.add(SchemaParser.from(node.get(i)));
        }
        return schemas;
    }

    private List<String> getServices(ArrayNode node) {
        List<String> services = new ArrayList<String>(node.size());
        for (int i = 0; i < node.size(); i++) {
        }
        return services;
    }
}
