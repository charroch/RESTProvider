
package novoda.lib.rest.marshaller.net;

import novoda.lib.rest.marshaller.MarshallingException;

import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;

public class XmlMarshaller extends HttpResponseMarshaller<XMLReader> {

    @Override
    public XMLReader marshall(InputStream in) throws IOException, MarshallingException {
        throw new IllegalStateException("not implemented");
    }
}
