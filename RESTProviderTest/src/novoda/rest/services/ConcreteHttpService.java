
package novoda.rest.services;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import java.io.IOException;

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
    protected void onHandleResponse(HttpResponse response, HttpContext context) {
    }
}
