
package novoda.rest.net;

import novoda.rest.database.SQLiteInserter;

import java.util.ArrayList;
import java.util.List;

public class ResponseTree {
    
    private Node<? extends SQLiteInserter> rootElement;

    /**
     * Default ctor.
     */
    public ResponseTree() {
        super();
    }

    /**
     * Return the root Node of the tree.
     * 
     * @return the root element.
     */
    public Node<? extends SQLiteInserter> getRootElement() {
        return this.rootElement;
    }

    /**
     * Set the root Element for the tree.
     * 
     * @param rootElement the root element to set.
     */
    public void setRootElement(Node<? extends SQLiteInserter> rootElement) {
        this.rootElement = rootElement;
    }

    /**
     * Returns the Tree<T> as a List of Node<T> objects. The elements of the
     * List are generated from a pre-order traversal of the tree.
     * 
     * @return a List<Node<T>>.
     */
    public List<Node<? extends SQLiteInserter>> toList() {
        List<Node<? extends SQLiteInserter>> list = new ArrayList<Node<? extends SQLiteInserter>>();
        walk(rootElement, list);
        return list;
    }

    /**
     * Returns a String representation of the Tree. The elements are generated
     * from a pre-order traversal of the Tree.
     * 
     * @return the String representation of the Tree.
     */
    public String toString() {
        return toList().toString();
    }

    /**
     * Walks the Tree in pre-order style. This is a recursive method, and is
     * called from the toList() method with the root element as the first
     * argument. It appends to the second argument, which is passed by reference
     * * as it recurses down the tree.
     * 
     * @param element the starting element.
     * @param list the output of the walk.
     */
    private void walk(Node<? extends SQLiteInserter> element, List<Node<? extends SQLiteInserter>> list) {
        list.add(element);
        for (Node<? extends SQLiteInserter> data : element.getChildren()) {
            walk(data, list);
        }
    }
}
