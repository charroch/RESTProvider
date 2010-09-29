
package novoda.rest.configuration;

import org.xmlpull.v1.XmlPullParserException;

import novoda.rest.database.SQLiteConflictClause;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteType;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class SQLiteMetaData {

    private static final String SQLITE_TAG = "sqlite";

    private static final String SQLITE_TAG_ATR_NAME = "name";

    private static final String SQLITE_SCHEMA_ATR_NAME = "schema";

    private static final String TABLE_TAG = "table";

    public String databaseName;

    public ArrayList<SQLiteTableCreator> tables;

    private Context context;

    public SQLiteMetaData(Context context, XmlResourceParser xml) {
        this.context = context;
        tables = new ArrayList<SQLiteTableCreator>();
        try {
            processDocument(xml);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (databaseName == null || databaseName.equals("")) {
            // databaseName = new
            // StringBuilder(context.getApplicationInfo().packageName)
            // .append(".db").toString();
        }
    }

    /* Processing XML methods */
    private void processDocument(XmlResourceParser xpp) throws XmlPullParserException, IOException {
        int eventType = xpp.getEventType();
        do {
            if (eventType == XmlResourceParser.START_DOCUMENT) {
            } else if (eventType == XmlResourceParser.END_DOCUMENT) {
                xpp.close();
            } else if (eventType == XmlResourceParser.START_TAG) {

                if (xpp.getName().equals(SQLITE_TAG)) {
                    processSQliteTag(xpp);
                }
                if (xpp.getName().equals(TABLE_TAG)) {
                    processTableTag(xpp);
                }
            } else if (eventType == XmlResourceParser.END_TAG) {
                if (xpp.getName().equals(SQLITE_TAG)) {
                    break;
                }
            } else if (eventType == XmlResourceParser.TEXT) {
            }
            eventType = xpp.next();
        } while (eventType != XmlResourceParser.END_DOCUMENT);
    }

    private void processTableTag(XmlResourceParser xpp) {
        SQLiteTableCreator creator = new XmlSQLiteTableCreator(xpp);
        tables.add(creator);
    }

    private void processSQliteTag(XmlResourceParser xpp) {
        databaseName = xpp.getAttributeValue(ProviderMetaData.NAMESPACE, SQLITE_TAG_ATR_NAME);
        final String rawCreate = xpp.getAttributeValue(ProviderMetaData.NAMESPACE,
                SQLITE_SCHEMA_ATR_NAME);
//        
//        context.getResources().openRawResource(
//                context.getResources().getIdentifier(rawCreate.split("//")[1], "raw", context.getPackageName()));
    }
}
