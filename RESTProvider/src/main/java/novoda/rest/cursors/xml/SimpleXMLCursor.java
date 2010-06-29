
package novoda.rest.cursors.xml;

import novoda.mixml.XMLNode;
import novoda.rest.cursors.RESTCursor;
import novoda.rest.database.SQLTableCreator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

public class SimpleXMLCursor extends RESTCursor<SimpleXMLCursor> {

    private CursorParams P;

    private XMLNode node;

    private String[] columnName;

    private List<XMLNode> nodeList = new ArrayList<XMLNode>();

    public static class CursorParams {
        public String rootName;

        public String fieldId;

        public String nodeName;

        public Map<String, String> mapper = new HashMap<String, String>();

        public SQLTableCreator sqlCreateMapper;

        public CursorParams() {
        }
    }

    public static class Builder {
        private final CursorParams P = new CursorParams();

        public Builder() {
        }

        public Builder withFieldID(final String fieldID) {
            return withFieldID(fieldID, false);
        }

        public Builder withRootNode(final String rootNode) {
            P.rootName = rootNode;
            return this;
        }

        public Builder withNodeName(final String nodeName) {
            P.nodeName = nodeName;
            return this;
        }

        public SimpleXMLCursor create() {
            final SimpleXMLCursor cursor = new SimpleXMLCursor();
            cursor.P = P;
            return cursor;
        }

        public Builder withFieldID(String fieldID, boolean shouldBeUnderscore) {
            if (shouldBeUnderscore)
                P.mapper.put("_id", fieldID);
            return this;
        }

        public Builder withMappedField(final String original, final String newValue) {
            P.mapper.put(original, newValue);
            return this;
        }

        public Builder withSQLTableCreator(SQLTableCreator creator) {
            P.sqlCreateMapper = creator;
            return this;
        }
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
    public double getDouble(int column) {
        return nodeList.get(mPos).path(getOriginalName(column)).getAsDouble();
    }

    @Override
    public float getFloat(int column) {
        return nodeList.get(mPos).path(getOriginalName(column)).getAsFloat();
    }

    @Override
    public int getInt(int column) {
        return nodeList.get(mPos).path(getOriginalName(column)).getAsInt();
    }

    private String getOriginalName(int column) {
        return P.mapper.containsKey(columnName[column]) ? P.mapper.get(columnName[column])
                : columnName[column];
    }

    @Override
    public long getLong(int column) {
        return nodeList.get(mPos).path(getOriginalName(column)).getAsLong();
    }

    @Override
    public short getShort(int column) {
        return nodeList.get(mPos).path(getOriginalName(column)).getAsShort();
    }

    @Override
    public String getString(int column) {
        return nodeList.get(mPos).path(getOriginalName(column)).getAsString();
    }

    @Override
    public boolean isNull(int column) {
        return false;
    }

    @Override
    public boolean onMove(int oldPosition, int newPosition) {
        return super.onMove(oldPosition, newPosition);
    }

    @Override
    public SimpleXMLCursor handleResponse(HttpResponse response) throws ClientProtocolException,
            IOException {
        if (response == null) {
            throw new IOException("response can't be null");
        }
        node = new XMLNode();
        try {
            node.parse(response.getEntity().getContent());
            for (String n : P.rootName.split("/")) {
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
            response.getEntity().consumeContent();
        }
        init();
        return this;
    }

    private void init() {
        if (P.nodeName != null) {
            nodeList = node.getAsList(P.nodeName);
            Map<String, String> m = nodeList.get(0).getAsMap();
            for (Entry<String, String> e : P.mapper.entrySet()) {
                m.put(e.getKey(), m.remove(e.getValue()));
            }
            columnName = m.keySet().toArray(new String[] {});
        }
    }
}
