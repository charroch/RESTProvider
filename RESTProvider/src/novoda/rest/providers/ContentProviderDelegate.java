
package novoda.rest.providers;

import android.content.ContentProvider;

public abstract class ContentProviderDelegate extends ContentProvider {
    ContentProviderDelegator delegator;

    public ContentProviderDelegate(ContentProviderDelegator dispatcher) {
        this.delegator = dispatcher;
    }

    public ContentProviderDelegator getDispatcher() {
        return delegator;
    }
}
