
package novoda.rest.database;

import novoda.rest.net.ResponseTree;

import java.io.InputStream;

public abstract class SQLMapper {
    
    public abstract ResponseTree readTree(String json);
    
}
