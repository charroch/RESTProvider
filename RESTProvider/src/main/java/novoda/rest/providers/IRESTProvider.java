
package novoda.rest.providers;

import novoda.rest.context.QueryCallInfo;

import android.app.Service;
import android.content.pm.ServiceInfo;

public interface IRESTProvider {
    ServiceInfo getService() throws InstantiationException, IllegalAccessException, ClassNotFoundException;
    void query(QueryCallInfo query);
}
