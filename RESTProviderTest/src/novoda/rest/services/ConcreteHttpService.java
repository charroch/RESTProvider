
package novoda.rest.services;

import org.apache.http.HttpResponse;

import novoda.rest.services.HttpService;

/*
 * Concrete class used for testing abstract HTTP service.
 */
public class ConcreteHttpService extends HttpService {

    public ConcreteHttpService(String name) {
        super(name);
    }

    public ConcreteHttpService() {
        super();
    }

    @Override
    protected void onHandleResponse(HttpResponse response) {
    }
}
