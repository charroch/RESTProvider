
package novoda.rest.mock;

import java.io.IOException;

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

public class MockHttpClient implements HttpClient {

    public void expect(HttpUriRequest expected) {

    }

    @Override
    public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException,
            ClientProtocolException {
        throw new UnsupportedOperationException("not implemented");
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
