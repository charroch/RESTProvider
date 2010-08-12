
package novoda.rest.net;

import java.io.IOException;

import novoda.rest.services.ETag;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

public class ETagRequestInterceptor implements HttpRequestInterceptor {

    private ETag etag;

    public ETagRequestInterceptor(ETag etag) {
        this.setEtag(etag);
    }

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {

        if (!request.containsHeader(ETag.IF_NONE_MATCH)) {
            Header inm = new BasicHeader(ETag.IF_NONE_MATCH, getEtag().etag);
            request.addHeader(inm);
        }

        if (!request.containsHeader(ETag.IF_MODIFIED_SINCE)) {
            Header inm = new BasicHeader(ETag.IF_MODIFIED_SINCE, getEtag().lastModified);
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
