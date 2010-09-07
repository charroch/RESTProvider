
package novoda.rest;

import android.content.Context;
import android.net.Uri;

public class RESTIntent extends android.content.Intent {

    public RESTIntent() {
        super();
    }

    public RESTIntent(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
    }

    public RESTIntent(android.content.Intent o) {
        super(o);
    }

    public RESTIntent(String action, Uri uri, Context packageContext, Class<?> cls) {
        super(action, uri, packageContext, cls);
    }

    public RESTIntent(String action, Uri uri) {
        super(action, uri);
    }

    public RESTIntent(String action) {
        super(action);
    }

    public static final String ACTION_QUERY = "novoda.rest.ACTION_QUERY";

    public static final String ACTION_DELETE = "novoda.rest.ACTION_DELETE";

    public static final String ACTION_INSERT = "novoda.rest.ACTION_INSERT";

    public static final String ACTION_UPDATE = "novoda.rest.ACTION_UPDATE";
}
