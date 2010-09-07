package novoda.rest.services;

import novoda.rest.context.QueryCallContext;
import android.net.Uri;

public interface IRemoteCallService {
    QueryCallContext getQueryCallContext(Uri uri);
}
