
package novoda.rest.net;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

public abstract class RequestProcessor<T> implements Callable<T> {

    private HttpClient client;

    private HttpUriRequest request;

    public void setHttpClient(HttpClient client) {
        this.client = client;
    }

    public void setHttpUriRequest(HttpUriRequest request) {
        this.request = request;
    }

    @Override
    public T call() throws Exception {
        try {
            HttpResponse response = client.execute(request);
            return parse(getStream(response));
        } catch (IOException e) {
            
        }
        return null;
    }

    public abstract T parse(InputStream stream);

    protected InputStream getStream(HttpResponse response) throws IllegalStateException,
            IOException {
        return response.getEntity().getContent();
    }

}
