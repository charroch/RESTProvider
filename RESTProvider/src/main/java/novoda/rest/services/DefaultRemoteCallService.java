
package novoda.rest.services;

import novoda.rest.clag.InitService;
import novoda.rest.configuration.ClagMetaData;
import novoda.rest.configuration.RESTServiceInfo;
import novoda.rest.context.CallContext;
import novoda.rest.context.CallInfo;
import novoda.rest.context.QueryCallContext;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DefaultRemoteCallService extends RemoteCallService {

    // Needed for instantiation
    public DefaultRemoteCallService() {
        super();
    }
    
    public DefaultRemoteCallService(String name) {
        super(name);
    }
    
    @Override
    public CallContext<?> getCallContext(CallInfo info) {
        return null;
    }

    @Override
    public void onHandleIntent(Intent intent) {
        InitService init = new InitService((ClagMetaData)intent.getParcelableExtra(("clag")), getBaseContext());
        try {
            init.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public QueryCallContext getQueryCallContext(Uri uri) {
        return null;
    }


}
