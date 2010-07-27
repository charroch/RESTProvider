
package novoda.rest.test.service;

import novoda.rest.cursors.json.JsonCursor;
import novoda.rest.database.SQLiteInserter;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.net.DefaultJsonInserter;
import novoda.rest.net.JsonInserter;
import novoda.rest.net.Node;
import novoda.rest.net.ResponseTree;
import novoda.rest.services.RESTCallService;
import novoda.rest.utils.HTTPUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.ContentValues;
import android.database.AbstractCursor;
import android.net.Uri;

import java.io.IOException;
import java.util.List;


// FIXME move this to the content provider
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
        return new HttpGet("http://search.twitter.com/search.json?q=droidconUK");
    }

    public List<NameValuePair> getUpdateParams(Uri uri, ContentValues values, String selection,
            String[] selectionArg) {
        return null;
    }

    public List<SQLiteInserter> getInserter(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    public ResponseHandler<? extends List<SQLiteInserter>> getQueryInserter(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }

    public ResponseHandler<ResponseTree> getResponseTree(Uri baseUri) {
        return new JsonResponseTree();
    }

    public SQLiteTableCreator getTableCreator(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private class JsonResponseTree implements ResponseHandler<ResponseTree> {

        public ResponseTree handleResponse(HttpResponse arg0) throws ClientProtocolException,
                IOException {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(arg0.getEntity().getContent());
            ResponseTree tree = new ResponseTree();
            tree.setRootElement(new Node<JsonInserter> (new DefaultJsonInserter(node.path("results")){
                @Override
                public String[] getColumns() {
                    return new String[] {
                            "profile_image_url", "created_at", "from_user", "to_user_id", "text", "id",
                            "from_user_id", "geo", "iso_language_code", "source"
                    };
                }
            }));
            return tree;
        }
        
    }

}
