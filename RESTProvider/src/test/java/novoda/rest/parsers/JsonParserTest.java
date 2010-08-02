
package novoda.rest.parsers;
import static org.junit.Assert.assertEquals;
import novoda.rest.parsers.Node.Options;
import novoda.rest.parsers.json.JsonNodeObject;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.steps.Steps;

import android.content.ContentValues;

import java.io.IOException;

public class JsonParserTest extends Steps {

    @Given("the following JSON:\n---\n$result\n---")
    public void getJson(String result) throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        Options options = new Options();
        options.rootNode = "root";
        options.table = "test";
        
        Options child = new Options();
        child.table = "test2";
        options.children.put("children", child);
        
        JsonNode node = mapper.readTree(result);
        
        JsonNodeObject node2 = new JsonNodeObject(node);
        node2.applyOptions(options);
        
        assertEquals(2, node2.getCount());
        JsonNodeObject a1 = (JsonNodeObject) node2.getNode(0);

        assertEquals(1, a1.getChildren().size());
        System.out.println(a1.getContentValue());
        
        JsonNodeObject child1 = (JsonNodeObject) a1.getChildren().get(0);
        
        ContentValues v = child1.getNode(0).getContentValue();
        child1.onPostInsert(11);
        child1.getNode(0).onPreInsert(v);
        System.out.println(v);
        
    }

    @Then("compute")
    public void compute() {
        System.out.print("hello ");
    }
}
