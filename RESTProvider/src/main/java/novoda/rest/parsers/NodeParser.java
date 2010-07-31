
package novoda.rest.parsers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.BufferedHttpEntity;

import novoda.rest.exception.ParserException;
import novoda.rest.parsers.NodeFactory.Options;

import java.io.IOException;
import java.io.InputStream;

public abstract class NodeParser<T extends Node<?>> implements ResponseHandler<T> {

    Options options;
    abstract T parse(InputStream response, Options options) throws ParserException;

    /**
     * The expected result HTTP response code. Do NOT return NOT MODIFIED (304)
     * as this will be handled automatically by the RESTProvider.
     * 
     * @return the expected status code. Usually OK 200 but could be NO CONTENT
     *         204
     */
    public int getExpectedResponse() {
        return HttpStatus.SC_OK;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        if (response == null) {
            throw new IOException("Response can not be null");
        }

        final int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = null;
        try {
            if (statusCode == getExpectedResponse()) {
                entity = new BufferedHttpEntity(response.getEntity());
                return parse(entity.getContent(), options);
            } else if (statusCode == HttpStatus.SC_NOT_MODIFIED) {
                // TODO add etag support
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        } finally {
            if (entity != null) {
                entity.consumeContent();
            }
        }
        throw new ParserException("unkown error");
    }
}
