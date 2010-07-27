
package novoda.rest.net;

import novoda.rest.database.SQLiteConflictClause;
import novoda.rest.database.SQLiteInserter;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteType;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    public T data;

    private Uri uri;

    private long parentId;

    public SQLiteInserter inserter;
    
    public ContentValues values;
    
    public String table;

    public List<Node<T>> children;

    public Node() {
        super();
    }

    public Node(T data) {
        this();
        setData(data);
    }

    public Node(T data, Uri uri) {
        this();
        setData(data);
        setUri(uri);
    }

    public List<Node<T>> getChildren() {
        if (this.children == null) {
            return new ArrayList<Node<T>>();
        }
        return this.children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
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
        children.add(child);
    }

    public T getData() {
        return this.data;
    }

    public void onParentInserted(long id) {
        for (Node<T> child : children) {
            child.setParentId(id);
            child.values.put(table, id);
        }
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(getData().toString()).append(",[");
        int i = 0;
        for (Node<T> e : getChildren()) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(e.getData().toString());
            i++;
        }
        sb.append("]").append("}");
        return sb.toString();
    }

    public void setUri(Uri insertUri) {
        this.uri = insertUri;
    }

    public Uri getUri() {
        return uri;
    }
    
    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
