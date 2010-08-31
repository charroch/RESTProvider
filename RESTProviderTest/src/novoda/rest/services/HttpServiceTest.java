
package novoda.rest.services;

import novoda.rest.services.HttpServiceTest.TestService;

import org.apache.http.HttpResponse;

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

    public class TestService extends HttpService {
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
}
