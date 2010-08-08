
package novoda.rest.test.apps.xmltest;

import novoda.rest.database.CachingStrategy;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteTableCreatorWrapper;
import novoda.rest.database.UriTableCreator;
import novoda.rest.parsers.NodeParser;
import novoda.rest.parsers.Node.Options;
import novoda.rest.parsers.xml.XmlNodeParser;
import novoda.rest.services.InsertTransactionListener;
import novoda.rest.services.RESTCallService;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CFWService extends RESTCallService {

    public List<NameValuePair> getDeleteParams(Uri uri, String selection, String[] selectionArg) {
        return null;
    }

    public List<NameValuePair> getInsertParams(Uri uri, ContentValues values) {
        return null;
    }

    public NodeParser<?> getParser(Uri uri) {
        
        this.setInsertTransactionListener(new InsertTransactionListener() {
            
            public void onPreTableCreate(SQLiteTableCreatorWrapper creator) {
                ((SQLiteTableCreatorWrapper)creator).appendColumns(new String[] {"task"});
                Log.i("TEST", "TEHIS s" + Arrays.toString(creator.getTableFields()));
            }
            
            public void onPreInsert(Uri uri, ContentValues values) {
                values.put("task", "coolos");
                Log.i("TEST", "on pre insert");
            }
            
            public void onPostInsert(Uri uri, long id) {
                Log.i("TEST", uri.toString() + id);
            }
            
            public void onFinish(Uri uri) {
                Log.i("TEST", uri.toString() + " on finish");
            }
        });
        
        Options o = new Options();
        o.table = "optionsList";
        o.nodeName = "optionsList_item";
        o.insertUri = Uri.parse("content://novoda.rest.test.xml/response/#/optionsList");

        Map<String, Options> map = new HashMap<String, Options>();
        map.put("optionsList", o);
        return new XmlNodeParser.Builder().withRootNode("response").withTableName("response")
                .withChildren(map).withInsertUri(
                        Uri.parse("content://novoda.rest.test.xml/response")).build(
                        XmlNodeParser.class);
    }

    public List<NameValuePair> getQueryParams(Uri uri, String[] projection, String selection,
            String[] selectionArg, String sortOrder) {
        return null;
    }

    public HttpUriRequest getRequest(Uri uri, int type, List<NameValuePair> params) {
        return new HttpGet("http://cfw.lbi.dk/gw/xml.php?task=2");
    }

    public SQLiteTableCreator getTableCreator(Uri uri) {
        return UriTableCreator.fromUri(uri);
    }

    public List<NameValuePair> getUpdateParams(Uri uri, ContentValues values, String selection,
            String[] selectionArg) {
        return null;
    }

    public int onNewResults(Uri uri) {
        return CachingStrategy.REPLACE;
    }
}
