
package novoda.rest.cursors.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import novoda.mixml.XMLNode;
import novoda.rest.cursors.RESTMarshaller;
import novoda.rest.database.SQLTableCreator;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import android.database.AbstractCursor;
import android.net.Uri;

public class SimpleXMLCursor extends RESTMarshaller {

    public SimpleXMLCursor(Uri uri) {
        super(uri);
    }

    private CursorParams P;

    private XMLNode node;

    private String[] columnName;

    private List<XMLNode> nodeList = new ArrayList<XMLNode>();

    public static class CursorParams {
        public String rootName;

        public String fieldId;

        public String nodeName = null;

        public Map<String, String> mapper = new HashMap<String, String>();

        public SQLTableCreator sqlCreateMapper;

        public boolean withAutoId = false;

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

        public SimpleXMLCursor create(final Uri uri) {
            final SimpleXMLCursor cursor = new SimpleXMLCursor(uri);
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

        public Builder withAutoID() {
            P.withAutoId = true;
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
        if (P.withAutoId && columnName[column].equals("_id")) {
            return mPos;
        }
        return nodeList.get(mPos).path(getOriginalName(column)).getAsInt();
    }

    private String getOriginalName(int column) {
        return P.mapper.containsKey(columnName[column]) ? P.mapper.get(columnName[column])
                : columnName[column];
    }

    @Override
    public long getLong(int column) {
        if (P.withAutoId && columnName[column].equals("_id")) {
            return mPos;
        }
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
        } else {
            nodeList.add(node);
        }
        
        Map<String, String> m = nodeList.get(0).getAsMap();
        for (Entry<String, String> e : P.mapper.entrySet()) {
            m.put(e.getKey(), m.remove(e.getValue()));
        }
        columnName = m.keySet().toArray(new String[] {});
        
        if (P.withAutoId) {
            String[] tmp = new String[columnName.length + 1];
            System.arraycopy(columnName, 0, tmp, 0, columnName.length);
            tmp[tmp.length - 1] = "_id";
            columnName = tmp;
        }
    }

    @Override
    public AbstractCursor getChild(Uri uri) {
        return null;
    }

    @Override
    public List getChildUri() {
        return null;
    }

    @Override
    public AbstractCursor getCursor() {
        return this;
    }

    @Override
    public void parse(HttpResponse response) throws ParseException {
    }
}
