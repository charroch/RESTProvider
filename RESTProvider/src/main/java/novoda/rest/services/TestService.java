
package novoda.rest.services;

import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.parsers.NodeParser;
import novoda.rest.parsers.json.JsonNodeParser;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.net.Uri;

import java.util.List;

public class TestService extends RESTCallService {

    @Override
    public List<NameValuePair> getDeleteParams(Uri uri, String selection, String[] selectionArg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NameValuePair> getInsertParams(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NodeParser<?> getParser(Uri uri) {
        return new JsonNodeParser.Builder().withNodeName("test").build(JsonNodeParser.class);
    }

    @Override
    public List<NameValuePair> getQueryParams(Uri uri, String[] projection, String selection,
            String[] selectionArg, String sortOrder) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HttpUriRequest getRequest(Uri uri, int type, List<NameValuePair> params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLiteTableCreator getTableCreator(Uri uri) {
        return null;
    }

    @Override
    public List<NameValuePair> getUpdateParams(Uri uri, ContentValues values, String selection,
            String[] selectionArg) {
        return null;
    }

}
