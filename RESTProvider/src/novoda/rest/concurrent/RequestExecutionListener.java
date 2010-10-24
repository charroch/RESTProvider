
package novoda.rest.concurrent;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

public interface RequestExecutionListener<T> extends ResponseHandler<T> {

    public void onThrowable(Throwable e);

    public void onPreCall(HttpUriRequest request);

    public void onPostCall(T data);
}
