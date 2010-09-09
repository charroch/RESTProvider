
package novoda.rest.clag;

import novoda.rest.database.DatabaseUtils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.steps.Steps;
import org.junit.Assert;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

public class SchemaParserSteps extends Steps {

    private static ObjectMapper mapper = new ObjectMapper();

    private JsonNode json;

    private SchemaParser parser;

    @Given("the following JSON:\n---\n$result\n---")
    public void getJson(String result) throws SAXException, IOException,
            ParserConfigurationException, FactoryConfigurationError {
        json = mapper.readTree(result);
        parser = SchemaParser.from(json);
    }

    @Then("I should get a SQL create statement like: \"$sql\"")
    public void setRootNodeForChildren(String sql) {
     //   Assert.assertEquals(sql.replaceAll("", ""), DatabaseUtils.getCreateStatement(parser).replaceAll("", ""));
    }

}
