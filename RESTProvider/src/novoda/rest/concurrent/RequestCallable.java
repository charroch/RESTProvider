
package novoda.rest.concurrent;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import java.util.concurrent.Callable;

public class RequestCallable<T> implements Callable<T> {

    private RequestExecutionListener<T> listener;

    private final HttpClient client;

    private final HttpUriRequest request;

    public RequestCallable(HttpClient client, HttpUriRequest request,
            RequestExecutionListener<T> listener) {
        super();
        if (client.getConnectionManager() instanceof ThreadSafeClientConnManager) {
            this.client = client;
            this.request = request;
            this.listener = listener;
        } else {
            throw new RuntimeException("Can not use non threaded http client");
        }
    }

    @Override
    public T call() throws Exception {
        try {
            listener.onPreCall(request);
            T data = client.execute(request, listener);
            listener.onPostCall(data);
            return data;
        } catch (Exception e) {
            listener.onThrowable(e.getCause());
        }
        return null;
    }
}
