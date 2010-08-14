package novoda.rest.services;

import android.test.ServiceTestCase;

public class RemoteCallServiceTest extends ServiceTestCase<RemoteCallService> {

    public RemoteCallServiceTest(Class<RemoteCallService> serviceClass) {
        super(serviceClass);
    }
    
    public RemoteCallServiceTest() {
        super(RemoteCallService.class);
    }

    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
    }
    
    public void testTrue() throws Exception {
        assertTrue(true);
    }
}
