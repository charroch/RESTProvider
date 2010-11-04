
package novoda.rest.mock;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.HttpParams;

import java.net.URI;

public class MockHttpRequest implements HttpUriRequest {

    @Override
    public void abort() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getMethod() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public URI getURI() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean isAborted() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public RequestLine getRequestLine() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void addHeader(Header header) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void addHeader(String name, String value) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public boolean containsHeader(String name) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Header[] getAllHeaders() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Header getFirstHeader(String name) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Header[] getHeaders(String name) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public Header getLastHeader(String name) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public HttpParams getParams() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public HeaderIterator headerIterator() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public HeaderIterator headerIterator(String name) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void removeHeader(Header header) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void removeHeaders(String name) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setHeader(Header header) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setHeader(String name, String value) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void setHeaders(Header[] headers) {
        throw new UnsupportedOperationException("not implemented");

    }

    @Override
    public void setParams(HttpParams params) {
        throw new UnsupportedOperationException("not implemented");
    }

}
