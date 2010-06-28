
package novoda.rest.test.apps.twitter;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;

import novoda.rest.providers.ModularProvider;
import novoda.rest.services.RESTCallService;
import novoda.rest.test.service.TwitterService;

public class ModTwitter extends ModularProvider {

    public ModTwitter() {
        super();
    }
    
    @Override
    protected RESTCallService getService() {
        return new TwitterService("MyIntentService");
    }

}
