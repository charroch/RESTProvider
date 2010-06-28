
package novoda.rest.test.apps.twitter;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class TwitterFeedExample2 extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor cur = managedQuery(Uri.parse("content://novoda.rest.test.twitter2/feed"), null,
                null, null, null);
        setListAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cur,
                new String[] {
                        "from_user", "text"
                }, new int[] {
                        android.R.id.text1, android.R.id.text2
                }));
    }
    
    @Override
    public void onContentChanged() {
        Log.i("REST", "content changed");
        super.onContentChanged();
    }
}
