
package novoda.rest.parsers.xml;

import static org.junit.Assert.assertEquals;
import novoda.mixml.XMLNode;
import novoda.rest.parsers.Node.Options;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

public class XmlParserTest extends Steps {

    private XMLNode node;

    private XmlNodeObject xmlNode;

    private Options options = null;

    @Given("the following XML:\n---\n$result\n---")
    public void getJson(String result) throws SAXException, IOException,
            ParserConfigurationException, FactoryConfigurationError {
        node = new XMLNode();
        node.parse(new ByteArrayInputStream(result.getBytes()));
        xmlNode = new XmlNodeObject(node);
    }

    @When("I configure the $type node to be \"$rootNode\"")
    public void setRootNode(String type, String rootNode) {
        if (options == null) {
            options = new Options();
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
            options = new Options();
        }
        if (options.children == null) {
            options.children = new HashMap<String, Options>();
        }
        options.children.put(name, new Options());
    }

    @When("the relationship \"$children\" is configured with root node \"$rootNode\"")
    public void setRootNodeForChildren(String childName, String root) {
        if (options == null) {
            options = new Options();
        }
        Options opt = options.children.get(childName);
        opt.rootNode = root;
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
}
