
package novoda.rest.services;

import novoda.rest.utils.AndroidHttpClient;
import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class RESTCallService extends IntentService {
    
    private static final String EXTRA_OPERATION = "operation";

    private static final String TAG = RESTCallService.class.getSimpleName();
    
    protected static AndroidHttpClient httpClient;

    static {
        setupHttpClient();
    }

    public RESTCallService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        bundle.getShort("type");
        
        if (!intent.hasExtra(EXTRA_OPERATION)) {
            Log.e(TAG, "can't handle intent without operation");
            stopSelf();
        }
        
        ContentProviderOperation operation = intent.getParcelableExtra(EXTRA_OPERATION);
        //operation.
        
    }

    private static void setupHttpClient() {
        httpClient = AndroidHttpClient.newInstance("Android/RESTProvider");
    }
    /*
     * 
     * query(Uri, String[], String, String[], String) which returns data to the caller
insert(Uri, ContentValues) which inserts new data into the content provider
update(Uri, ContentValues, String, String[]) which updates existing data in the content provider
delete(Uri, String, String[]) which deletes data from the content provider

*/
     
}
