
package novoda.rest.test.apps.twitter;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TwitterFeedExample2 extends ListActivity {

    private BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(TwitterFeedExample2.this, "UPDATED", Toast.LENGTH_LONG).show();
            }
        };

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
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter("novoda.rest.action.QUERY_COMPLETE"));
        super.onResume();
    }
    
    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }
}
