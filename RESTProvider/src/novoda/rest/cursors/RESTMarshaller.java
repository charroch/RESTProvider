
package novoda.rest.cursors;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import android.database.AbstractCursor;
import android.net.Uri;

public abstract class RESTMarshaller<T extends AbstractCursor> extends AbstractCursor implements
        ResponseHandler<T> {

    private  Uri uri;

    public RESTMarshaller() {
        super();
    }

    public RESTMarshaller(final Uri uri) {
        this.uri = uri;
    }

    public abstract List<Uri> getChildUri();

    /*
     * instead of having getChild for uri maybe getting the child for mPos as
     * for a normal cursor... Maybe we need a RESTCursor.
     */
    public abstract T getChild(final Uri uri);

    public abstract T getCursor();

    public abstract void parse(HttpResponse response) throws org.apache.http.ParseException;

    public Uri getUri() {
        return uri;
    }

    protected int getExpectedResponseCode() {
        return 200;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        if (response == null) {
            throw new IOException("response is null");
        }

        try {
            if (getExpectedResponseCode() == response.getStatusLine().getStatusCode()) {
                parse(response);
            }
        } finally {
            response.getEntity().consumeContent();
        }
        return getCursor();
    }
}
