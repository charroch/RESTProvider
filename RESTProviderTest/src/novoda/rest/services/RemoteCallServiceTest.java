package novoda.rest.services;

import android.content.Intent;
import android.test.ServiceTestCase;

public class RemoteCallServiceTest extends ServiceTestCase<DefaultRemoteCallService> {

    public RemoteCallServiceTest(Class<DefaultRemoteCallService> serviceClass) {
        super(serviceClass);
    }
    
    public RemoteCallServiceTest() {
        super(DefaultRemoteCallService.class);
    }

    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
    }
    
    public void testTrue() throws Exception {
        ((DefaultRemoteCallService)getService()).onHandleIntent(new Intent());
        assertNotNull(getService().getCallResult(null));
    }
}
