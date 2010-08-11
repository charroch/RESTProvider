
package novoda.rest.context;

import novoda.rest.database.DatabaseUtils;
import novoda.rest.services.CallInfo;
import novoda.rest.services.ETag;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.List;

public class QueryCallContext extends CallContext {

    @Override
    public List<NameValuePair> getParams(CallInfo info) {
        return null;
    }

    @Override
    public HttpUriRequest getRequest(CallInfo info) {
        return null;
    }

    @Override
    public CallResult execute() {
        // Do DB here
        
        return null;
    }

    protected synchronized ETag getEtag() {
        return DatabaseUtils.etagForQuery(getDBHelper().getReadableDatabase(), getCallInfo().url);
    }
}
