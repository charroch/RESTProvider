
package novoda.rest.net;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class ETagRequestInterceptor implements HttpRequestInterceptor {

    private ETag etag;

    public ETagRequestInterceptor(ETag etag) {
        this.setEtag(etag);
    }

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {

        final HttpRequest reqWrapper = (HttpRequest) context
                .getAttribute(ExecutionContext.HTTP_REQUEST);

        final HttpHost targetHost = (HttpHost) context
                .getAttribute(ExecutionContext.HTTP_TARGET_HOST);

        final String requestUri = targetHost.toURI().concat(reqWrapper.getRequestLine().getUri());

        if (!request.containsHeader(ETag.IF_NONE_MATCH)) {
            Header inm = new BasicHeader(ETag.IF_NONE_MATCH, etag.getEntityTag(requestUri));
            request.addHeader(inm);
        }

        if (!request.containsHeader(ETag.IF_MODIFIED_SINCE)) {
            Header inm = new BasicHeader(ETag.IF_MODIFIED_SINCE, etag.getLastModified(requestUri));
            request.addHeader(inm);
        }
    }

    public void setEtag(ETag etag) {
        this.etag = etag;
    }

    public ETag getEtag() {
        return etag;
    }
}
