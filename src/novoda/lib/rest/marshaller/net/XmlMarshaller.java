package novoda.lib.rest.marshaller.net;

import novoda.lib.rest.marshaller.MarshallingException;

import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;

public class XmlMarshaller extends HttpResponseMarshaller<XmlPullParser> {

    @Override
    public XmlPullParser marshall(InputStream in) throws IOException, MarshallingException {
XmlPullParser parser = Xml.newPullParser();
parser.setInput(this.getInputStream(), null);
        return parser;
    }
}
