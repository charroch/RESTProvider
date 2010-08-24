
package novoda.rest.services;

import org.codehaus.jackson.JsonNode;

import novoda.rest.configuration.RESTServiceInfo;
import novoda.rest.context.CallContext;
import novoda.rest.context.CallInfo;
import novoda.rest.os.Worker;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

public abstract class RemoteCallService extends Service implements IRemoteCallService {
    private volatile Looper mServiceLooper;

    private volatile ServiceHandler mServiceHandler;

    private String mName;

    private boolean mRedelivery;

    private Worker<JsonNode> worker;

    private Context mContext;

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent)msg.obj);
            stopSelf(msg.arg1);
        }
    }

    /**
     * Creates an RemoteCallService. Invoked by your subclass's constructor.
     * 
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RemoteCallService(String name) {
        super();
        mName = name;
    }

    /**
     * We need this for instrumentation
     */
    public RemoteCallService() {
        this(RemoteCallService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("RemoteCallService[" + mName + "]");
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        
        worker = new Worker<JsonNode>(5,5);
    }

    public void setIntentRedelivery(boolean enabled) {
        mRedelivery = enabled;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
    }

    public void onHandleIntent(Intent intent) {
        CallContext<JsonNode> context = (CallContext<JsonNode>)getCallContext((CallInfo)intent.getParcelableExtra("callInfo"));
        worker.produce(context);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     */
    // private final IRemoteCallService.Stub mBinder = new
    // IRemoteCallService.Stub() {
    // @Override
    // public void delete() throws RemoteException {
    // // TODO Auto-generated method stub
    //            
    // }
    // @Override
    // public void insert() throws RemoteException {
    // // TODO Auto-generated method stub
    //            
    // }
    // @Override
    // public void query(QueryCallInfo query) throws RemoteException {
    // // TODO Auto-generated method stub
    //            
    // }
    // @Override
    // public void registerCallback() throws RemoteException {
    // // TODO Auto-generated method stub
    //            
    // }
    // @Override
    // public void unregisterCallback() throws RemoteException {
    // // TODO Auto-generated method stub
    //            
    // }
    // @Override
    // public void update() throws RemoteException {
    // // TODO Auto-generated method stub
    //            
    // }
    // };

    public abstract CallContext<?> getCallContext(CallInfo info);

    public CallInfo getCallResult(CallInfo info) {
        Message msg = mServiceHandler.obtainMessage();
        return (CallInfo)msg.obj;
    }
    
    public void attachInfo(Context context, RESTServiceInfo serviceInfo) {
        mContext = context;
        
    }
}
