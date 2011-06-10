
package novoda.rest.concurrent;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import android.content.ContentProviderOperation;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public abstract class RequestCallable<T> implements Callable<ArrayList<ContentProviderOperation>>,
        RequestExecutionListener<T> {

    private HttpClient client;

    private HttpUriRequest request;

    public RequestCallable(HttpUriRequest request) {
        this.setRequest(request);
    }

    public RequestCallable() {
    }

    @Override
    public ArrayList<ContentProviderOperation> call() throws Exception {
        try {
            onPreCall(getRequest());
            T data = client.execute(getRequest(), this);
            onPostCall(data);
            return marshall(data);
        } catch (Exception e) {
            onThrowable(e.getCause());
        }
        // return an empty array if the implementor did not return anything
        return new ArrayList<ContentProviderOperation>();
    }

    public void setRequest(HttpUriRequest request) {
        this.request = request;
    }

    public HttpUriRequest getRequest() {
        return request;
    }

    public void setHttpClient(HttpClient client) {
        if (client.getConnectionManager() instanceof ThreadSafeClientConnManager) {
            this.client = client;
        }
    }

    public HttpClient getHttpClient() {
        return client;
    }
}
