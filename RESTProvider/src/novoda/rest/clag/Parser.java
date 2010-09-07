
package novoda.rest.clag;

import novoda.rest.exception.ParserException;

import java.io.InputStream;

public interface Parser<T> {
    public abstract T parse(InputStream in) throws ParserException;
}
