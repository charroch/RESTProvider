package novoda.rest.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import novoda.rest.database.Column;
import novoda.rest.database.SQLiteConflictClause;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteType;
import novoda.rest.system.LoaderException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;

public class XmlSQLiteTableCreator implements SQLiteTableCreator {

	private static final String ROOT_TAG_NAME = "table";

	private XmlPullParser xpp;

	private Map<String, Column> columns;

	private String tableName;

	public XmlSQLiteTableCreator(XmlPullParser xtc) {
		columns = new HashMap<String, Column>();
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
			tableName = xpp.getAttributeValue(ProviderMetaData.NAMESPACE,
					"tableName");
		}
		do {
			if (eventType == XmlPullParser.START_TAG) {

			} else if (eventType == XmlPullParser.END_TAG) {
				if (ROOT_TAG_NAME.equals(xpp.getName()))
					break;
			}
			eventType = xpp.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);
	}

	@Override
	public String getParentColumnName() {
		return null;
	}

	@Override
	public String getParentPrimaryKey() {
		return null;
	}

	@Override
	public String getParentTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLiteType getParentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getTableFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		return "test";
	}

	@Override
	public String[] getTriggers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLiteType getType(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNullAllowed(String field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOneToMany() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUnique(String field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SQLiteConflictClause onConflict(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shouldIndex(String field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldPKAutoIncrement() {
		// TODO Auto-generated method stub
		return false;
	}

}
