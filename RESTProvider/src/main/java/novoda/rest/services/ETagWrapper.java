
package novoda.rest.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class ETagWrapper extends HttpService {

    private HttpService delegate;

    public ETagWrapper(HttpService delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPostCall(HttpResponse response) {
        // save here.
        super.onPostCall(response);
    }

    @Override
    protected void onPreCall(HttpUriRequest request) {
        // only against conditional gett
        if (request.getMethod().equals(HttpGet.METHOD_NAME)) {
            // append ETag here
        }
        super.onPreCall(request);
    }

    @Override
    protected void onHandleResponse(HttpResponse response) {
        delegate.onHandleResponse(response);
    }
}
