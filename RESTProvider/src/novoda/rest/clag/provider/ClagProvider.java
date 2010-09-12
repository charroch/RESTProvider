
package novoda.rest.clag.provider;

import com.xtify.android.sdk.PersistentLocationManager;

import novoda.rest.configuration.ClagMetaData;
import novoda.rest.intents.HttpServiceIntent;
import novoda.rest.providers.ContentProviderDelegate;
import novoda.rest.providers.ContentProviderDelegator;
import novoda.rest.services.HttpService;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public class ClagProvider extends ContentProviderDelegate {

    ClagMetaData clag;

    public ClagProvider(ContentProviderDelegator delegator, ClagMetaData clag) {
        super(delegator);
        this.clag = clag;
    }

    private PersistentLocationManager persistentLocationManager;

    @Override
    public boolean onCreate() {
        // persistentLocationManager = new
        // PersistentLocationManager(getContext());
        HttpServiceIntent intent = new HttpServiceIntent() {
            @Override
            public String getMethod() {
                return HttpService.ACTION_GET;
            }

            // this is confusing, which intent and why?
            @Override
            public Intent getIntent() {
                Intent intent = new Intent(getMethod());
                intent.setData(getHttpUri());

                Intent i2 = new Intent("novoda.rest.clag.INIT");
                intent.putExtra("intent", i2);
                return intent;
            }

            @Override
            public Uri getHttpUri() {
                return Uri.parse("http://bookation.appspot.com/data");
            }
        };
        getDispatcher().startService(intent);

        intent = new HttpServiceIntent() {

            @Override
            public String getMethod() {
                return HttpService.ACTION_GET;
            }

            // this is confusing, which intent and why?
            @Override
            public Intent getIntent() {
                Intent intent = new Intent(getMethod());
                intent.setData(getHttpUri());

                Intent i2 = new Intent("novoda.rest.clag.REGISTER_XTIFY");
                i2.putExtra("account", "charroch@gmail.com");
                //i2.putExtra("xtify_user_key", persistentLocationManager.getUserKey());
                intent.putExtra("intent", i2);
                return intent;
            }

            @Override
            public Uri getHttpUri() {
                return Uri
                        .parse("http://bookation.appspot.com/register?xtify_id=8010a12c-bc25-4b8e-8e8c-cb9942cbcfaa");
            }
        };
        getDispatcher().startService(intent);

        return true;
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        return 0;
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

    @Override
    public Uri insert(Uri arg0, ContentValues arg1) {
        return null;
    }

    // should maybe get Intent?
    @Override
    public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3, String arg4) {
        HttpServiceIntent intent = new HttpServiceIntent() {
            @Override
            public String getMethod() {
                return HttpService.ACTION_GET;
            }

            // this is confusing, which intent and why?
            @Override
            public Intent getIntent() {
                Intent intent = new Intent(getMethod());
                intent.setData(getHttpUri());

                Intent i2 = new Intent("novoda.rest.clag.QUERY");
                i2.putExtra("account", "charroch@gmail.com");
                intent.putExtra("intent", i2);
                return intent;
            }

            @Override
            public Uri getHttpUri() {
                return Uri.parse("http://bookation.appspot.com/data/Bookation");
            }
        };
        getDispatcher().startService(intent);
        return null;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        return 0;
    }

}
