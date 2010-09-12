
package novoda.rest.parsers.xml;

import novoda.mixml.XMLNode;
import novoda.rest.parsers.Node;

import android.content.ContentValues;

import java.util.List;
import java.util.Map.Entry;

public class XmlNodeObject extends Node<XMLNode> {

    private boolean isArray;

    private String tableName;

    public XmlNodeObject(XMLNode node) {
        this.data = node;
        isArray = node.isArray();
    }

    @Override
    public void applyOptions(ParsingOptions options) {
        this.setOptions(options);
        if (options.rootNode != null) {
            data = data.path(options.rootNode);
            isArray = data.isArray();
        }
        if (options.nodeName != null) {
            //data = data.path(options.nodeName);
        }
        if (options.children != null) {
            if (isArray) {
                // do nothing as we do it in the get
            } else {
                applyOptionsChild(options, this);
            }
        }
        if (options.table != null) {
            this.tableName = options.table;
        }
    }

    public void applyOptionsChild(ParsingOptions options, XmlNodeObject node) {
        for (Entry<String, ParsingOptions> entry : options.children.entrySet()) {
            XMLNode child = node.data.path(entry.getKey());
            XmlNodeObject childNode = new XmlNodeObject(child);
            childNode.applyOptions(entry.getValue());
            node.addChild(childNode);
        }
    }

    @Override
    public ContentValues getContentValue() {
        ContentValues values = new ContentValues();
        for (Entry<String, String> s: data.getAsMap().entrySet()){
            // Do some XPath stuff here
            if (getOptions().children.containsKey(s.getKey())) {
                continue;
            }
            values.put(s.getKey(), s.getValue());
        }
        return values;
    }

    @Override
    public int getCount() {
        if (data.isArray()) {
            return data.size();
        } else if (data != null) {
            return 1;
        }
        return 0;
    }

    @Override
    public Node<XMLNode> getNode(int index) {
        if (isArray) {
            XmlNodeObject o = new XmlNodeObject(data.get(index));
            o.setParent(this.getParent());
            ParsingOptions options = getOptions();
            options.rootNode = null;
            o.applyOptions(options);
            return o;
        } else {
            return this;
        }
    }

    @Override
    public String getTableName() {
        if (tableName != null)
            return tableName;
        else if (getOptions().rootNode != null)
            return getOptions().rootNode;
        throw new IllegalStateException("Can not have a node without a table name");
    }

    @Override
    public boolean shouldInsert() {
        return true;
    }

    @Override
    public String[] getColumns() {
        List<String> fields = null; //data.getFieldsName(); 
        if (getParent() != null) {
            fields.add(getParent().getTableName() + "_id");
        }
        return fields.toArray(new String[]{});
    }
}
