
package novoda.lib.rest.actor;

import novoda.lib.rest.marshaller.net.XmlMarshaller;

import org.xmlpull.v1.XmlPullParser;

public abstract class XMLPersistingActor extends PersistingActor<XmlPullParser, XmlMarshaller> {
    
    @Override
    protected XmlMarshaller getMarshaller() {
        return new XmlMarshaller();
    }
    
}
