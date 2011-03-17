
package novoda.lib.rest.marshaller.net;

import novoda.lib.rest.marshaller.MarshallingException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import java.io.IOException;
import java.io.InputStream;

public class XmlMarshaller extends HttpResponseMarshaller<XmlPullParser> {

    @Override
    public XmlPullParser marshall(InputStream in) throws IOException, MarshallingException {
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, null);
            return parser;
        } catch (XmlPullParserException e) {
            throw new MarshallingException(e);
        }
    }
}
