
package novoda.rest.parsers;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Tree object with back reference to the parent Node. It enables tree
 * traversal of a random response being JSON, XML or others. Each node within
 * the tree usually represent a node within the response as in a tag in XML or
 * an Object in JSON. For each Node, we should be able to insert it into the
 * database by extracting the necessary data.
 */
public abstract class Node<T> {

    T data;

    /*
     * The inserted row id in the database
     */
    private long databaseId = -1;

    /*
     * In case of One to many relationship, we need the database column name of
     * the parent for which to insert the child elements.
     */
    private String idFieldName = null;

    private List<Node<T>> children;

    /*
     * Keep track of the parent
     */
    private Node<T> parent;

    /*
     * And next in line
     */
    private Node<T> nextSibling;

    public T getData() {
        return data;
    }

    Node<T> next() {
        return nextSibling;
    }

    protected void setNext(Node<T> sibling) {
        this.nextSibling = sibling;
    }

    public List<Node<T>> getChildren() {
        if (this.children == null) {
            return new ArrayList<Node<T>>();
        }
        return this.children;
    }

    public int getNumberOfChildren() {
        if (children == null) {
            return 0;
        }
        return children.size();
    }

    public void addChild(Node<T> child) {
        if (children == null) {
            children = new ArrayList<Node<T>>();
        }
        child.parent = this;
        children.add(child);
    }

    /*
     * Gives you a chance to hook into the node before the insert.
     */
    public void onPreInsert(ContentValues values) {
        if (parent != null) {
            values.put(parent.getIdFieldName(), parent.getDatabaseId());
        }
    }

    /*
     * After the insert we get the DB id back and set it to the node for the
     * children to be used.
     */
    public void onPostInsert(long id) {
        setDatabaseId(id);
    }

    public void setDatabaseId(long databaseId) {
        this.databaseId = databaseId;
    }

    public long getDatabaseId() {
        return databaseId;
    }

    public void setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
    }

    public String getIdFieldName() {
        return idFieldName;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return parent;
    }

    /**
     * @return The values to be inserted into the DB for this node
     */
    abstract ContentValues getContentValue();

    /**
     * @return the table Name for which this row will be inserted
     */
    abstract String getTableName();
}
