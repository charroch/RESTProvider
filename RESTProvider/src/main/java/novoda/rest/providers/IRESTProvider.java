
package novoda.rest.providers;

import novoda.rest.context.QueryCallInfo;

import android.app.Service;

public interface IRESTProvider {
    Service getService() throws InstantiationException, IllegalAccessException, ClassNotFoundException;
    void query(QueryCallInfo query);
}
