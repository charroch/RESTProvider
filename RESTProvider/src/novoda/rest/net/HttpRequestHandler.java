
package novoda.rest.net;

import novoda.rest.concurrent.RequestExecutionListener;
import novoda.rest.net.RequestExecutionException.Type;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.io.InputStream;

public abstract class HttpRequestHandler<T> implements RequestExecutionListener<T> {

    private final static IOException nullResponse = new IOException("response is null");

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        if (response == null) {
            onThrowable(nullResponse);
            throw nullResponse;
        }
        if (processStatusLine(response.getStatusLine())) {

        }
        return null;
    }

    private boolean processStatusLine(StatusLine statusLine) {
        final int statusCode = statusLine.getStatusCode();
        if (statusCode >= 400 && statusCode <= 499) {
            onHttpError(statusCode);
            return false;
        } else if (statusCode == HttpStatus.SC_NO_CONTENT) {
            onFinish();
            return false;
        } else if (statusCode == HttpStatus.SC_OK) {
            return true;
        } else {
            onThrowable(new RequestExecutionException(Type.UNKNOWN_STATUS_CODE, statusCode));
            return false;
        }
    }

    public abstract T parse(InputStream in);
}
