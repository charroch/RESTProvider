
package novoda.rest.providers;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;

public class ProviderMetaData implements Parcelable {

    private static final String NAMESPACE = "http://novoda.github.com/RESTProvider/apk/res";

    private static final String SERVICE_TAG_ATR_NAME = "name";

    private static final String SERVICE_TAG = "service";

    public String serviceClassName;
    
    public int xml;

    public ProviderMetaData(Parcel parcel) {
        serviceClassName = parcel.readString();
    }

    private ProviderMetaData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int parcelableFlag) {
        parcel.writeString(serviceClassName);
    }

    public static ProviderMetaData loadFromXML(XmlResourceParser xml)
            throws XmlPullParserException, IOException {
        ProviderMetaData m = new ProviderMetaData();
        m.processDocument(xml);
        return m;
    }

    public static final Parcelable.Creator<ProviderMetaData> CREATOR = new Parcelable.Creator<ProviderMetaData>() {
        @Override
        public ProviderMetaData createFromParcel(Parcel parcel) {
            return new ProviderMetaData(parcel);
        }

        @Override
        public ProviderMetaData[] newArray(int size) {
            return new ProviderMetaData[size];
        }
    };

    /* Processing XML methods */

    private void processDocument(XmlResourceParser xpp) throws XmlPullParserException, IOException {
        int eventType = xpp.getEventType();
        do {
            if (eventType == XmlResourceParser.START_DOCUMENT) {
            } else if (eventType == XmlResourceParser.END_DOCUMENT) {
                xpp.close();
            } else if (eventType == XmlResourceParser.START_TAG) {

                if (xpp.getName().equals(SERVICE_TAG)) {
                    processServiceTag(xpp);
                }

            } else if (eventType == XmlResourceParser.END_TAG) {
            } else if (eventType == XmlResourceParser.TEXT) {
                // currently not in use
            }
            eventType = xpp.next();
        } while (eventType != XmlResourceParser.END_DOCUMENT);
    }

    /*
     * Process the <service> tag within the metadata which will contain most
     * attribute for the service declaration.
     */
    private void processServiceTag(XmlResourceParser xpp) {
        serviceClassName = xpp.getAttributeValue(NAMESPACE, SERVICE_TAG_ATR_NAME);
    }

}
