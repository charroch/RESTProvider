
package novoda.rest.parsers.xml;

import novoda.mixml.XMLNode;
import novoda.rest.exception.ParserException;
import novoda.rest.parsers.NodeParser;
import novoda.rest.parsers.Node.Options;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

public class XmlNodeParser extends NodeParser<XmlNodeObject> {

    @Override
    public XmlNodeObject parse(InputStream response, Options options) throws ParserException {
        XMLNode node = new XMLNode();
        try {
            node.parse(response);
            XmlNodeObject xml = new XmlNodeObject(node);
            xml.applyOptions(options);
            return xml;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        }
        throw new ParserException("unknown error");
    }

}
