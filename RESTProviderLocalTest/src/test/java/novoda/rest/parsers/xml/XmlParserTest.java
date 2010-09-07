
package novoda.rest.parsers.xml;

import static org.junit.Assert.assertEquals;
import novoda.mixml.XMLNode;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.Node.ParsingOptions;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.definition.ExamplesTable;
import org.jbehave.scenario.steps.Steps;
import org.xml.sax.SAXException;

import android.content.ContentValues;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParserTest extends Steps {

    private XMLNode node;

    private XmlNodeObject xmlNode;

    private ParsingOptions options = null;

    @Given("the following XML:\n---\n$result\n---")
    public void getJson(String result) throws SAXException, IOException,
            ParserConfigurationException, FactoryConfigurationError {
        node = new XMLNode();
        node.parse(new ByteArrayInputStream(result.getBytes()));
        xmlNode = new XmlNodeObject(node);
        options = new ParsingOptions();
    }

    @When("I configure the $type node to be \"$rootNode\"")
    public void setRootNode(String type, String rootNode) {
        if (options == null) {
            options = new ParsingOptions();
        }
        if (type.equals("root")) {
            options.rootNode = rootNode;
        } else if (type.equals("array")) {
            options.nodeName = rootNode;
        }
    }

    @When("I configure a one to many relationship on node \"$children\"")
    public void setRelationship(String name) {
        if (options == null) {
            options = new ParsingOptions();
        }

        if (options.children == null) {
            options.children = new HashMap<String, ParsingOptions>();
        }
        options.children.put(name, new ParsingOptions());
    }

    @When("the relationship \"$children\" is configured with root node \"$rootNode\"")
    public void setRootNodeForChildren(String childName, String root) {
        if (options == null) {
            options = new ParsingOptions();
        }
        ParsingOptions opt = options.children.get(childName);
        opt.rootNode = root;
        options.children.put(childName, opt);
    }

    @When("the relationship \"$children\" is configured with node name \"$rootNode\"")
    public void setNodeNameForChildren(String childName, String node) {
        if (options == null) {
            options = new ParsingOptions();
        }
        ParsingOptions opt = options.children.get(childName);
        opt.nodeName = node;
        options.children.put(childName, opt);
    }

    @Then("I should get a count of $count")
    public void checkCount(int count) {
        xmlNode.applyOptions(options);
        assertEquals(count, xmlNode.getCount());
    }

    @Then("I should get a count for $nd child of $count")
    public void checkChildCount(int childIndex, int count) {
        assertEquals(count, xmlNode.getChildren().get(childIndex - 1).getCount());
    }

    @Then("I should get following results:$results")
    public void getResult(ExamplesTable table) {
        checkResults(xmlNode, table);
    }

    @Then("I should get foolowing results for child $index:$results")
    public void getResultForChildren(int index, ExamplesTable table) {
        checkResults(xmlNode.getChildren().get(index - 1), table);
    }

    private void checkResults(Node<?> node, ExamplesTable table) {
        for (int i = 0; i < node.getCount(); i++) {
            for (Entry<String, String> en : table.getRows().get(i).entrySet()) {
                ContentValues values = node.getNode(i).getContentValue();
                assertEquals(values.get(en.getKey()).toString(), en.getValue());
            }
        }
    }
}
