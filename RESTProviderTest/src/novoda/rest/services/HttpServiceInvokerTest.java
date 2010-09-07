
package novoda.rest.services;

import novoda.rest.RESTIntent;
import novoda.rest.context.command.Command;
import novoda.rest.context.command.QueryCommand;

import android.content.Intent;
import android.test.ServiceTestCase;

public class HttpServiceInvokerTest extends ServiceTestCase<HttpServiceInvoker> {

    public HttpServiceInvokerTest() {
        this(HttpServiceInvoker.class);
    }

    public HttpServiceInvokerTest(Class<HttpServiceInvoker> serviceClass) {
        super(serviceClass);
        setupService();
    }

    @SuppressWarnings("unchecked")
    public void testGettingQueryCommand() throws Exception {
        Intent intent = new Intent(RESTIntent.ACTION_QUERY);
        Command<?> command =  getService().getCommand(intent, HttpServiceInvoker.JSON);
        assertTrue(command instanceof QueryCommand);
    }
}
