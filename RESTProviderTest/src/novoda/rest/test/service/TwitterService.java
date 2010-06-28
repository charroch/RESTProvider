
package novoda.rest.test.service;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.database.AbstractCursor;
import android.net.Uri;

import java.util.List;

import novoda.rest.cursors.json.JsonCursor;
import novoda.rest.services.RESTCallService;
import novoda.rest.utils.HTTPUtils;

public class TwitterService extends RESTCallService {

    public TwitterService() {
        super();
    }
    
    public TwitterService(String name) {
        super(name);
    }

    public List<NameValuePair> getDeleteParams(Uri uri, String selection, String[] selectionArg) {
        return null;
    }

    public ResponseHandler<? extends Integer> getDeleteHandler(Uri uri) {
        throw new UnsupportedOperationException("not in use");
    }

    public ResponseHandler<? extends Uri> getInsertHandler(Uri uri) {
        throw new UnsupportedOperationException("not in use");
    }

    public ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri) {
        return new JsonCursor("results", true);
    }

    public ResponseHandler<? extends Integer> getUpdateHandler(Uri uri) {
        throw new UnsupportedOperationException("not in use");
    }

    public List<NameValuePair> getQueryParams(Uri uri, String[] projection, String selection,
            String[] selectionArg, String sortOrder) {
        return HTTPUtils.getParamsFrom("q", "droidcon");
    }

    public String getTableName(Uri uri) {
        return uri.getLastPathSegment();
    }

    public List<NameValuePair> getInsertParams(Uri uri, ContentValues values) {
        return null;
    }

    public HttpUriRequest getRequest(Uri uri, int type, List<NameValuePair> params) {
        return new HttpGet("http://search.twitter.com/search.json?q=droidcon");
    }

    public List<NameValuePair> getUpdateParams(Uri uri, ContentValues values, String selection,
            String[] selectionArg) {
        return null;
    }

}
