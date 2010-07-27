package novoda.rest.database;

import novoda.rest.net.Node;

import android.net.Uri;

public abstract class URITableCreatorImpl implements SQLiteTableCreator {
    private String parentId;
    
    void setParentId(String id) {
        parentId = id;
    }
    
    SQLiteTableCreator fromURI(Uri uri, Node<?> node){
        node.inserter.getColumns();
        return null;
    }
}
