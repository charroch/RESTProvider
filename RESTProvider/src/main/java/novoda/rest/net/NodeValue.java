package novoda.rest.net;

import novoda.rest.database.SQLiteInserter;

public abstract class NodeValue implements SQLiteInserter {
    public abstract NodeValue path(String string);
    public abstract NodeValue path(int index);
}
