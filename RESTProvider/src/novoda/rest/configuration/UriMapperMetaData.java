package novoda.rest.configuration;

import java.io.IOException;
import java.util.HashMap;

import novoda.rest.database.Column;
import novoda.rest.database.SQLiteType;
import novoda.rest.system.LoaderException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class UriMapperMetaData {
	
	private static final String ROOT_TAG_NAME = "table";

	private static final String COLUMN_TAG_NAME = "column";

	private XmlPullParser xpp;
	
	public UriMapperMetaData(XmlPullParser xtc) {
		this.xpp = xtc;
		try {
			processTag();
		} catch (XmlPullParserException e) {
			new LoaderException("Should process tag " + ROOT_TAG_NAME
					+ " but was: " + ((xtc != null) ? xtc.getName() : "null"),
					e);
		} catch (IOException e) {
			new LoaderException("error reading XML file", e);
		}
	}

	private void processTag() throws XmlPullParserException, IOException {
		int eventType = xpp.getEventType();
		if (eventType == XmlPullParser.START_TAG
				&& !ROOT_TAG_NAME.equals(xpp.getName())) {
			throw new XmlPullParserException(
					"xmlpullparser not at correct position");
		} else {
		}
		do {
			if (eventType == XmlPullParser.START_TAG) {
				if (COLUMN_TAG_NAME.equals(xpp.getName())) {
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (ROOT_TAG_NAME.equals(xpp.getName()))
					break;
			}
			eventType = xpp.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);
	}
}
