
package novoda.rest.marshaller.net;

import novoda.lib.rest.marshaller.MarshallingException;
import novoda.lib.rest.marshaller.net.HttpControlFlow;
import novoda.lib.rest.marshaller.net.HttpResponseMarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

public class HttpResponseMarshallerTest extends TestCase {

    private HttpResponseMarshallerUT ut;

    @Override
    protected void setUp() throws Exception {
        ut = new HttpResponseMarshallerUT();
        super.setUp();
    }

    public void testThrowAnExceptionInCaseOfNoContent() throws Exception {
        try {
            ut.marshall(new MockHttpResponse(HttpStatus.SC_NO_CONTENT));
            fail();
        } catch (HttpControlFlow e) {
            assertTrue("should throw an exception in case of no content", true);
        }
    }

    public void testThrowAnExceptionInCaseNull() throws Exception {
        try {
            ut.marshall((HttpResponse) null);
            fail();
        } catch (IOException e) {
            assertTrue("should throw an exception in case of null content", true);
        }
    }

    public void testShouldGiveCorrectResponse() throws Exception {
        MockHttpResponse response = new MockHttpResponse(200);
        response.setResponse("response");
        assertEquals("response", ut.marshall(response));
    }

    class HttpResponseMarshallerUT extends HttpResponseMarshaller<String> {
        @Override
        protected String marshall(InputStream content) throws IOException, MarshallingException {
            BufferedReader br = new BufferedReader(new InputStreamReader(content));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        }
    }

    class MockHttpResponse extends BasicHttpResponse {
        public MockHttpResponse(final int statusline) {
            this(new StatusLine() {

                @Override
                public int getStatusCode() {
                    return statusline;
                }

                @Override
                public String getReasonPhrase() {
                    return null;
                }

                @Override
                public ProtocolVersion getProtocolVersion() {
                    return null;
                }
            });
        }

        public MockHttpResponse(StatusLine statusline) {
            super(statusline);
        }

        public void setResponse(String response) {
            try {
                setEntity(new StringEntity(response));
            } catch (UnsupportedEncodingException e) {
                // should never happen
            }
        }

        public void setEmptyResponse() {
            setEntity(null);
        }
    }
}
