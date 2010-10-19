package novoda.rest.concurrent;

import java.util.concurrent.Callable;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

public class HttpRequestCallable implements Callable<HttpResponse> {

	private HttpClient client;

	private HttpUriRequest request;

	public HttpRequestCallable(HttpClient client, HttpUriRequest request) {
		if (client.getConnectionManager() instanceof ThreadSafeClientConnManager) {
			this.client = client;
			this.request = request;
		} else {
			throw new RuntimeException("Can not use non threaded http client");
		}
	}

	@Override
	public HttpResponse call() throws Exception {
		return client.execute(request);
	}
}
