
package novoda.rest.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import novoda.rest.database.SQLiteTableCreator;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.os.Parcel;
import android.os.Parcelable;

public class ProviderMetaData implements Parcelable {

    public static final String NAMESPACE = "http://novoda.github.com/RESTProvider/apk/res";

    private static final String SERVICE_TAG_ATR_NAME = "name";

    private static final String CLAG_TAG_ATR_ENDPOINT = "endpoint";

    private static final String SERVICE_TAG = "service";

    private static final String CLAG_TAG = "clag";

    private static final String URI_MAPPER_TAG = "urimapper";

    public String serviceClassName;

    public ClagMetaData clag;
    
    public SQLiteMetaData sqlite;

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
        if (xml == null) {
            throw new IOException("xml is null");
        }
        
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

	private static final String SQLITE_TAG = "sqlite";


    /* Processing XML methods */
    private void processDocument(XmlResourceParser xpp) throws XmlPullParserException, IOException {
        int eventType = xpp.getEventType();
        do {
            if (eventType == XmlResourceParser.START_DOCUMENT) {
            } else if (eventType == XmlResourceParser.END_DOCUMENT) {
                xpp.close();
            } else if (eventType == XmlResourceParser.START_TAG) {

                // Default <service> tag
                if (xpp.getName().equals(SERVICE_TAG)) {
                    processServiceTag(xpp);
                }

                if (xpp.getName().equals(CLAG_TAG)) {
                    processClagTag(xpp);
                }
                
                if (xpp.getName().equals(URI_MAPPER_TAG)) {
                	
                }
                
                if (xpp.getName().equals(SQLITE_TAG) ) {
                	processSQLiteTag(xpp);
                }
            } else if (eventType == XmlResourceParser.END_TAG) {
            } else if (eventType == XmlResourceParser.TEXT) {
                // currently not in use
            }
            eventType = xpp.next();
        } while (eventType != XmlResourceParser.END_DOCUMENT);
    }

    private void processSQLiteTag(XmlResourceParser xpp) {
    	sqlite = new SQLiteMetaData(null, xpp);
	}

	/*
     * Process the <clag> tag
     */
    private void processClagTag(XmlResourceParser xpp) {
        clag = new ClagMetaData();
        clag.endpoint = xpp.getAttributeValue(NAMESPACE, CLAG_TAG_ATR_ENDPOINT);
    }

    /*
     * Process the <service> tag within the metadata which will contain most
     * attribute for the service declaration.
     */
    private void processServiceTag(XmlResourceParser xpp) {
        serviceClassName = xpp.getAttributeValue(NAMESPACE, SERVICE_TAG_ATR_NAME);
    }

    public boolean isClag() {
        return (clag != null);
    }

	public List<SQLiteTableCreator> getCreateStatements() {
		return sqlite.tables;
	}
}
