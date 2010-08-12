
package novoda.rest.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;

public class RemoteCallService extends Service {

    @Override
    public void onCreate() {
        Looper.myQueue();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    public int getPercentil() {
        return -1;
    }

}
