
package novoda.rest.parsers;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import novoda.rest.exception.ParserException;
import novoda.rest.parsers.NodeFactory.Options;

import java.io.IOException;
import java.io.InputStream;

public abstract class NodeParser<V,T extends Node<T>> implements ResponseHandler<T> {
    
    abstract T parse(InputStream response, Options options) throws ParserException;

    public int getExpectedResponse() {
        return HttpStatus.SC_OK;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        
        if (response == null) {
            throw new IOException("Response can not be null");
        }
        
        
        return null;
    }
}
