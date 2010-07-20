
package novoda.rest.net;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import novoda.rest.database.SQLiteType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;

import java.io.IOException;

public class ParsingSteps extends Steps {

    private static ObjectMapper mapper = new ObjectMapper();

    private JsonInserter inserter;

    private JsonNode node;

    private String tableName;

    @Given("the following json: $result")
    public void getJson(String result) throws JsonProcessingException, IOException {
        node = mapper.readTree(result);
    }
    
    @Given("table name is \"$table\"")
    public void getTableName(String table) throws JsonProcessingException, IOException {
        this.tableName = table;
    }

    
    @When("I parse against root node \"$node\"")
    public void parseJson(String node) {
        inserter = new JsonInserter((node == null || node.equals(""))? this.node :this.node.path(node)) {
            @Override
            public SQLiteType getType(String field) {
                return SQLiteType.TEXT;
            }
        };
    }

    @Then("I should get a count of $count")
    public void checkCount(int count) {
        assertEquals(count, inserter.getCount());
    }
    
    @Then("I should get columns: [$columns]")
    public void checkColumns(String columns) {
        assertArrayEquals(inserter.getColumns(), columns.split(","));
    }
    
    @Then("I should get SQL insert \"$statement\"")
    public void checkSQLinsert(String statement) {
        assertEquals(statement, inserter.getInsertStatement(tableName));
    }
    
    @Then("And the insert index of \"$field\" should be $index")
    public void checkIndexOfField(String field, int index) {
        assertEquals(inserter.getInsertIndex(field), index);
    }
    
    @Then("And the object returned for index $index and field $field should be $object")
    public void checkObjectForIndex(int index, String field, Object obj) {
        assertEquals(obj, inserter.get(field, index));
    }
    
}
