
package novoda.rest.intents;

import novoda.rest.RESTIntent;
import novoda.rest.context.CallInfo;
import novoda.rest.services.HttpService;
import novoda.rest.services.HttpServiceException;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import java.util.ArrayList;

public abstract class QueryIntent implements HttpServiceIntent {

    private static final int GET = 1;

    private static final int POST = 2;

    private static final int DELETE = 3;

    private static final int PUT = 4;

    private Uri uri;

    private String[] projection;

    private String selection;

    private String[] selectionArgs;

    private String sortOrder;

    public QueryIntent(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        super();
        this.uri = uri;
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.sortOrder = sortOrder;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String[] getProjection() {
        return projection;
    }

    public void setProjection(String[] projection) {
        this.projection = projection;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String[] getSelectionArgs() {
        return selectionArgs;
    }

    public void setSelectionArgs(String[] selectionArgs) {
        this.selectionArgs = selectionArgs;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public abstract <T extends Parcelable, NameValuePair> ArrayList<T> getQueryParams(Uri uri,
            String[] projection, String selection, String[] selectionArgs, String sortOrder);

    @Override
    public Intent getIntent() {
        String action = null;
        switch (getMethod()) {
            case GET:
                action = HttpService.ACTION_GET;
                break;
            case POST:
                action = HttpService.ACTION_POST;
                break;
            default:
                throw new HttpServiceException(
                        "Method not supported, does the intent contain a method?");
        }

        Intent intent = new Intent(action);
        intent.setData(getHttpUri());
        intent.putParcelableArrayListExtra("params", getQueryParams(uri, projection, selection,
                selectionArgs, sortOrder));
        
        // RESTProvider specific
        CallInfo info = new CallInfo();
        info.action = RESTIntent.ACTION_QUERY;
        intent.putExtra("callinfo", info);
        
        return intent;
    }
}
