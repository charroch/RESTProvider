
package novoda.rest.test.apps.xmltest;

import android.app.Activity;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;

public class XMLActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Cursor cur = getContentResolver().query(Uri.parse("status://novoda.rest.test.xml/response"), null, null, null, null);
        DatabaseUtils.dumpCursor(cur);
        cur.close();
        super.onCreate(savedInstanceState);
    }
}
