
package novoda.rest.mock;

import static junit.framework.Assert.assertEquals;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MockHttpClient implements HttpClient {

    HttpUriRequest expected;

    private Class<? extends HttpUriRequest> requestType = null;

    private String uri;

    private String params;

    private HttpResponse response;

    public MockHttpClient expect(HttpUriRequest expected) {
        this.expected = expected;
        return this;
    }

    public MockHttpClient expectType(Class<? extends HttpUriRequest> requestType) {
        this.requestType = requestType;
        return this;
    }

    public MockHttpClient expectUri(String uri) {
        this.uri = uri;
        return this;
    }

    public MockHttpClient expectPostParams(String params) {
        this.params = params;
        return this;
    }

    public MockHttpClient withResponse(HttpResponse response) {
        this.response = response;
        return this;
    }

    @Override
    public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
        assertRequest(request);
        return response;
    }

    private void assertRequest(HttpUriRequest request) throws IOException {
        if (requestType != null) {
            assertEquals("should have save request type", requestType, request.getClass());
        }
        if (uri != null) {
            assertEquals("should have same uri", uri, request.getRequestLine().getUri());
        }
        if (params != null) {
            if (request instanceof HttpEntityEnclosingRequest) {
                assertEquals("should have same params within http enclosing request", params,
                        EntityUtils.toString(((HttpEntityEnclosingRequest) request).getEntity()));
            }
        }
    }

    @Override
    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException,
            ClientProtocolException {
        assertRequest(request);
        return response;
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException,
            ClientProtocolException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1)
            throws IOException, ClientProtocolException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
            throws IOException, ClientProtocolException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1, HttpContext arg2)
            throws IOException, ClientProtocolException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2)
            throws IOException, ClientProtocolException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2,
            HttpContext arg3) throws IOException, ClientProtocolException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public HttpParams getParams() {
        throw new UnsupportedOperationException("not implemented");
    }
}
