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

public class XmlSQLiteTableCreator implements SQLiteTableCreator {

	private static final String ROOT_TAG_NAME = "table";

	private static final String COLUMN_TAG_NAME = "column";

	private XmlPullParser xpp;

	private Map<String, Column> columns;

	private String tableName;

	private String primaryField = "_id";

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
				if (COLUMN_TAG_NAME.equals(xpp.getName())) {
					Column column = new Column();

					column.name = xpp.getAttributeValue(
							ProviderMetaData.NAMESPACE, "name");

					String type = xpp.getAttributeValue(
							ProviderMetaData.NAMESPACE, "type");

					if (type != null) {
						column.type = SQLiteType.valueOf(type);
					}
					String allowNull = xpp.getAttributeValue(
							ProviderMetaData.NAMESPACE, "allow_null");

					if (allowNull != null && allowNull.equals("true")) {
						column.allowNull = true;
					}

					String unique = xpp.getAttributeValue(
							ProviderMetaData.NAMESPACE, "unique");

					if (unique != null && unique.equals("true")) {
						column.unique = true;
					}

					String primary = xpp.getAttributeValue(ProviderMetaData.NAMESPACE,
					"primary");
					if (primary != null && primary.equals("true")) {
						primaryField = column.name;
					}
					
					columns.put(column.name, column);
				}
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
		return null;
	}

	@Override
	public SQLiteType getParentType() {
		return null;
	}

	@Override
	public String getPrimaryKey() {
		return primaryField;
	}

	@Override
	public String[] getTableFields() {
		return columns.keySet().toArray(new String[] {});
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public String[] getTriggers() {
		return null;
	}

	@Override
	public SQLiteType getType(String field) {
		return columns.get(field).type;
	}

	@Override
	public boolean isNullAllowed(String field) {
		return columns.get(field).allowNull;
	}

	@Override
	public boolean isOneToMany() {
		return false;
	}

	@Override
	public boolean isUnique(String field) {
		return columns.get(field).unique;
	}

	@Override
	public SQLiteConflictClause onConflict(String field) {
		return null;
	}

	@Override
	public boolean shouldIndex(String field) {
		return false;
	}

	@Override
	public boolean shouldPKAutoIncrement() {
		return false;
	}

}
