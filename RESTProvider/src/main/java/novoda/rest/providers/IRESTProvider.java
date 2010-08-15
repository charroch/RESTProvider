
package novoda.rest.providers;

import novoda.rest.context.QueryCallInfo;

import android.app.Service;

public interface IRESTProvider {
    Service getService();
    void query(QueryCallInfo query);
}
