
package novoda.rest.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

public class Landing extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getContentResolver().query(Uri.parse("content://novoda.rest.clag/Story"), null, null, null,
                null);
    }
}
