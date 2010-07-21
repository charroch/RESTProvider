
package novoda.rest.database;

import novoda.rest.net.ResponseTree;

public abstract class SQLMapper {
    public abstract ResponseTree readTree(String json);
}
