
package novoda.rest.parsers;

import java.io.InputStream;

import novoda.rest.net.Node;

public abstract class SQLiteCodec {
    public abstract <T> Node<T> readTree(SQLiteParser parser, InputStream in);

    public abstract SQliteFactory getSQLiteFactory();
}
