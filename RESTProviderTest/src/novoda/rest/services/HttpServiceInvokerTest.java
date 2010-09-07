
package novoda.rest.services;

import novoda.rest.RESTIntent;
import novoda.rest.context.command.QueryCommand;

import org.codehaus.jackson.JsonNode;

import android.content.Intent;
import android.test.ServiceTestCase;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;

public class HttpServiceInvokerTest extends ServiceTestCase<HttpServiceInvoker> {

    public HttpServiceInvokerTest() {
        super(HttpServiceInvoker.class);
    }

    public HttpServiceInvokerTest(Class<HttpServiceInvoker> serviceClass) {
        super(serviceClass);
    }

    public void testGettingQueryCommand() throws Exception {
        setupService();
        Intent intent = new Intent(RESTIntent.ACTION_QUERY);
        Object obj = getService().getCommand(intent, HttpServiceInvoker.JSON);
        TypeVariable<?>[] typeParameters = obj.getClass().getTypeParameters();

        assertTrue(obj instanceof QueryCommand<?>);
        assertEquals(typeParameters[0], JsonNode.class);
    }

}
