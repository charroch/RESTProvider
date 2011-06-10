
package novoda.rest.net;

import novoda.rest.mock.MockHttpRequest;
import novoda.rest.mock.MockHttpResponse;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.net.URI;

public class ETagInterceptorTest extends AndroidTestCase {

    private ETagInterceptor interceptor;

    @Override
    protected void setUp() throws Exception {
        setContext(new RenamingDelegatingContext(getContext(), "temp_"));
        interceptor = new ETagInterceptor(getContext(), "etag");
        assertNotNull(interceptor);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        interceptor.helper.getWritableDatabase().delete("etag", null, null);
        // interceptor.helper.getWritableDatabase().delete("etag", null, null);
        super.tearDown();
    }

    public void testSimpleEtag() throws Exception {
        MockHttpRequest request = new MockHttpRequest() {

            @Override
            public String getMethod() {
                return HttpGet.METHOD_NAME;
            }

            @Override
            public URI getURI() {
                return URI.create("http://test.com");
            }

            @Override
            public boolean containsHeader(String name) {
                return false;
            }
        };
        try {
            interceptor.onPreCall(request, null);
        } catch (UnsupportedOperationException e) {
            fail();
        }
        assertTrue("should not give an exception", true);
    }

    public void testGettingEtag() throws Exception {

        ETag etag = new ETag();

        etag.etag = "test-etag";
        etag.lastModified = "modified";

        interceptor.helper.insertETagForUri(etag, "http://test.com");

        MockHttpRequest request = new MockHttpRequest() {

            @Override
            public String getMethod() {
                return HttpGet.METHOD_NAME;
            }

            @Override
            public URI getURI() {
                return URI.create("http://test.com");
            }

            @Override
            public boolean containsHeader(String name) {
                return false;
            }
            
            @Override
            public void addHeader(Header header) {
                if (header.getName().equals(ETag.ETAG)) {
                    assertEquals("test-etag", header.getValue());
                }
                
                if (header.getName().equals(ETag.LAST_MODIFIED)) {
                    assertEquals("modified", header.getValue());
                }
            }
        };
        try {
            interceptor.onPreCall(request, null);
        } catch (UnsupportedOperationException e) {
            fail("should not give an exception");
        }
        assertTrue(true);
    }

    public void testInsertEtag() throws Exception {
        MockHttpResponse response = new MockHttpResponse() {
            @Override
            public boolean containsHeader(String name) {
                return true;
            }

            public org.apache.http.StatusLine getStatusLine() {
                return new BasicStatusLine(new ProtocolVersion("http", 1, 1), 200, "OK");
            };

            @Override
            public Header getFirstHeader(String name) {
                if (name.equals(ETag.ETAG)) {
                    return new BasicHeader(ETag.ETAG, "test-etag");
                } else if (name.equals(ETag.LAST_MODIFIED)) {
                    return new BasicHeader(ETag.LAST_MODIFIED, "last-modified");
                }
                return super.getFirstHeader(name);
            }
        };
        HttpContext context = new BasicHttpContext();
        context.setAttribute(ExecutionContext.HTTP_REQUEST, new MockHttpRequest() {
            @Override
            public RequestLine getRequestLine() {
                return new BasicRequestLine("GET", "test.com", new ProtocolVersion("http", 1, 1));
            }
        });
        final HttpHost targetHost = new HttpHost("test.com");
        context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, targetHost);
        interceptor.onPostCall(response, context);
        ETag etag = interceptor.helper.getETag("test.com");
        assertEquals("test-etag", etag.etag);
        assertEquals("last-modified", etag.lastModified);
    }
}
