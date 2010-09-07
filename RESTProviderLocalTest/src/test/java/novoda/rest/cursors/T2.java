
package novoda.rest.cursors;

import novoda.mixml.XMLNode;
import novoda.rest.cursors.xml.SimpleXMLCursor;

import org.xml.sax.SAXException;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

public class T2 extends ResponseCursor {

    private XMLNode node;

    private String[] columnName;

    private List<XMLNode> nodeList = new ArrayList<XMLNode>();

    private List<List<SimpleXMLCursor>> children = new ArrayList<List<SimpleXMLCursor>>();

    private List<List<Uri>> childrenUri = new ArrayList<List<Uri>>();

    public T2() {
        super();
    }

    public T2(XMLNode root) {
        this.node = node;
        init2();
    }

    @Override
    public Object get(String field) {
        return nodeList.get(mPos).path(field).getAsString();
    }

    @Override
    public ResponseCursor getChild(String field) {
        return this;
    }

    @Override
    public int getType(int column) {
        return 0;
    }

    @Override
    public void parse(InputStream in) throws IOException {
        node = new XMLNode();
        try {
            node.parse(in);
            for (String n : params.rootName.split("/")) {
                node = node.path(n);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        init2();
    }

    private void init2() {
        if (params.nodeName != null) {
            nodeList = node.getAsList(params.nodeName);
        } else {
            nodeList.add(node);
        }

        Map<String, String> m = nodeList.get(0).getAsMap();
        for (Entry<String, String> e : params.mapper.entrySet()) {
            m.put(e.getKey(), m.remove(e.getValue()));
        }
        columnName = m.keySet().toArray(new String[] {});
    }

    @Override
    public String[] getColumnNames() {
        return columnName;
    }

    @Override
    public int getCount() {
        return nodeList.size();
    }

    @Override
    public void parseChildren(ResponseCursor parent, String field) {
        T2 p = (T2)parent;
        node = p.nodeList.get(p.mPos);
        init2();
    }
}
