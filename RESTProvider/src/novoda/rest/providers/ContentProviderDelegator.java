
package novoda.rest.providers;

import novoda.rest.intents.HttpServiceIntent;

import android.content.pm.ServiceInfo;

public interface ContentProviderDelegator {
    public ServiceInfo getService();
    public void startService(HttpServiceIntent intent);
}
