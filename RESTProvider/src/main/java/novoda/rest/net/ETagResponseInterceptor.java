
package novoda.rest.net;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class ETagResponseInterceptor implements HttpResponseInterceptor {

    private ETag etag;

    public ETagResponseInterceptor(ETag etag) {
        this.etag = etag;
    }

    @Override
    public void process(HttpResponse response, HttpContext context) throws HttpException,
            IOException {

        /*
         * Only save if we have the etag in the header and the response has been
         * successful.
         */
        if (response != null && response.containsHeader(ETag.ETAG)
                && response.containsHeader(ETag.LAST_MODIFIED)
                && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            final Header etagHeader = response.getFirstHeader(ETag.ETAG);
            final Header lastModified = response.getFirstHeader(ETag.LAST_MODIFIED);

            final HttpRequest reqWrapper = (HttpRequest) context
                    .getAttribute(ExecutionContext.HTTP_REQUEST);

            final HttpHost targetHost = (HttpHost) context
                    .getAttribute(ExecutionContext.HTTP_TARGET_HOST);

            final String request = targetHost.toURI().concat(reqWrapper.getRequestLine().getUri());
            etag.save(request, lastModified.getValue(), etagHeader.getValue());
        }
    }

    public void setEtag(ETag etag) {
        this.etag = etag;
    }

    public ETag getEtag() {
        return etag;
    }

}
