
package novoda.rest.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.util.Log;

public class Landing extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProviderInfo info = getPackageManager().resolveContentProvider(
                "novoda.rest.test", PackageManager.GET_META_DATA);
        
        Log.i("test", info.metaData.toString());
    }
}
