
package novoda.lib.rest.marshaller.net;

import org.apache.http.HttpStatus;

import java.io.IOException;

public class HttpControlFlow extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 6102303556679150632L;

    private int httpCode;

    public HttpControlFlow(int httpCode) {
        this.httpCode = httpCode;
    }

    public boolean isNoContent() {
        return httpCode == HttpStatus.SC_NO_CONTENT;
    }
}
