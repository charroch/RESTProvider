
package novoda.lib.rest.marshaller.net;

import novoda.lib.rest.marshaller.IMarshaller;
import novoda.lib.rest.marshaller.MarshallingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.entity.BufferedHttpEntity;

import java.io.IOException;
import java.io.InputStream;

public abstract class HttpResponseMarshaller<To> implements IMarshaller<HttpResponse, To> {

    @Override
    public To marshall(HttpResponse content) throws IOException, MarshallingException {
        if (content == null)
            throw new IOException("response can not be null");

        StatusLine statusLine = content.getStatusLine();

        /*
         * This should actually be handled upstream in future release (i.e. we
         * should not reach here if No content). Just in case, use exception to
         * ensure handling.
         */
        if (statusLine.getStatusCode() == HttpStatus.SC_NO_CONTENT) {
            throw new HttpControlFlow(HttpStatus.SC_NO_CONTENT);
        }

        BufferedHttpEntity entity = new BufferedHttpEntity(content.getEntity());
        return marshall(entity.getContent());
    }

    abstract To marshall(InputStream content) throws IOException, MarshallingException;
}
