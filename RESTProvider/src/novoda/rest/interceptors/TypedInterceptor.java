package novoda.rest.interceptors;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

public class TypedInterceptor implements HttpResponseInterceptor {

	@Override
	public void process(HttpResponse response, HttpContext context)
			throws HttpException, IOException {
	}
}
