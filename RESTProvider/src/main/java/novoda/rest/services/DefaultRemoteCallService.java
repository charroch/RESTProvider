
package novoda.rest.services;

import novoda.rest.clag.InitService;
import novoda.rest.context.CallContext;
import novoda.rest.providers.ClagMetaData;

import android.content.Intent;
import android.util.Log;

public class DefaultRemoteCallService extends RemoteCallService {

    @Override
    public CallContext getCallContext(CallInfo info) {
        return null;
    }

    @Override
    public void onHandleIntent(Intent intent) {
        Log.i("TSA", ((ClagMetaData)intent.getParcelableExtra(("clag"))).endpoint + " clage ");
        InitService init = new InitService((ClagMetaData)intent.getParcelableExtra(("clag")), getBaseContext());
        try {
            init.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
