package novoda.rest.test.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

// Used within IOCLoaderTest.java
public class TestService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
