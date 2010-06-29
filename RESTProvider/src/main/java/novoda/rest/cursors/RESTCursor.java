
package novoda.rest.cursors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import android.database.AbstractCursor;

import java.io.IOException;

public abstract class RESTCursor<T extends AbstractCursor> extends AbstractCursor implements
        ResponseHandler<T> {

    public String getPrimaryKey() {
        return null;
    }
    
    public String getType(final String field) {
        return "TEXT";
    }
    
    public boolean isNullAllowed(final String field) {
        return true;
    }
    
    public boolean isUnique(final String field) {
        return false;
    }

    T getCursor(HttpResponse response) {
        return null;
    }
    
    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        return getCursor(response);
    }

}
