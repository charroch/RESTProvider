
package novoda.rest.net;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.BufferedHttpEntity;

import android.database.AbstractCursor;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class ResponseMarshaller<T extends AbstractCursor> implements ResponseHandler<T> {

    public abstract List<String> getChildUriSegment();

    public abstract T getChildCursor(final long parentId, final String segment);

    public abstract T getCursor();

    public abstract void parse(InputStream response) throws IOException;

    protected int getExpectedResponseCode() {
        return HttpStatus.SC_OK;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        if (response == null) {
            throw new IOException("response is null");
        }
        try {
            if (getExpectedResponseCode() == response.getStatusLine().getStatusCode()) {
                parse(new BufferedHttpEntity(response.getEntity()).getContent());
            }
        } finally {
            response.getEntity().consumeContent();
        }
        return getCursor();
    }
}
