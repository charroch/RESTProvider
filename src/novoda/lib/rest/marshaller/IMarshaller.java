
package novoda.lib.rest.marshaller;

import java.io.IOException;

public interface IMarshaller<From, To> {
    To marshall(From content) throws IOException, MarshallingException;
}
