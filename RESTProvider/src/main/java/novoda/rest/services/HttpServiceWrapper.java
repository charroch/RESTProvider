
package novoda.rest.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

public interface HttpServiceWrapper {
    void onPreCall(HttpUriRequest request, HttpContext context);
    void onPostCall(HttpResponse response, HttpContext context);
}
