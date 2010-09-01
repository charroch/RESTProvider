
package novoda.rest.services;

import java.io.IOException;

import novoda.rest.services.HttpServiceTest.TestService;

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

import android.test.ServiceTestCase;

public class HttpServiceTest extends ServiceTestCase<TestService> {

    public HttpServiceTest(Class<TestService> serviceClass) {
        super(serviceClass);
    }

    public HttpServiceTest() {
        this(TestService.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setupService();
    }

    public static class TestService extends HttpService {
        public TestService() {
            super();
        }

        public TestService(String tag) {
            super(tag);
        }

        @Override
        protected void onHandleResponse(HttpResponse response) {
        }
    }

    public class MockHttpClient implements HttpClient {
        @Override
        public HttpResponse execute(HttpUriRequest request) throws IOException,
                ClientProtocolException {
            return null;
        }

        @Override
        public HttpResponse execute(HttpUriRequest request, HttpContext context)
                throws IOException, ClientProtocolException {
            return null;
        }

        @Override
        public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException,
                ClientProtocolException {
            return null;
        }

        @Override
        public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1)
                throws IOException, ClientProtocolException {
            return null;
        }

        @Override
        public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context)
                throws IOException, ClientProtocolException {
            return null;
        }

        @Override
        public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1,
                HttpContext arg2) throws IOException, ClientProtocolException {
            return null;
        }

        @Override
        public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2)
                throws IOException, ClientProtocolException {
            return null;
        }

        @Override
        public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2,
                HttpContext arg3) throws IOException, ClientProtocolException {
            return null;
        }

        @Override
        public ClientConnectionManager getConnectionManager() {
            return null;
        }

        @Override
        public HttpParams getParams() {
            return null;
        }
    }
}
