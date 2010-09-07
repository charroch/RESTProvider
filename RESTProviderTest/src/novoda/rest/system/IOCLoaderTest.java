
package novoda.rest.system;

import android.app.Service;
import android.content.pm.ServiceInfo;
import android.test.InstrumentationTestCase;

public class IOCLoaderTest extends InstrumentationTestCase {

    private IOCLoader loader;

    public IOCLoaderTest() {
        super();
    }

    @Override
    protected void setUp() throws Exception {
        loader = new IOCLoader(getInstrumentation().getContext());
        super.setUp();
    }

    public void testSetup() throws Exception {
        assertTrue(true);
    }

    public void testServiceExists() throws Exception {
        try {
            Service service = loader.getService("novoda.rest.test.services.TestService");
            assertNotNull(service);
        } catch (Exception e) {
            fail();
        }
    }
    
    public void testServiceExistsNoPackage() throws Exception {
        try {
            Service service = loader.getService(".services.TestService");
            assertNotNull(service);
        } catch (Exception e) {
            fail();
        }
    }

    public void testShouldThrowExceptionIfServiceNotFound() throws Exception {
        try {
            loader.getService("not.existance");
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    public void testServiceExistsInManifest() throws Exception {
        ServiceInfo info = new ServiceInfo();
        info.name = ".services.TestService";
        assertServiceEquals(info, loader.getServiceInfo("novoda.rest.test.services.TestService"));
    }
    
    public void testGetMetaData() throws Exception {
        
    }
    
    private void assertServiceEquals(ServiceInfo expected, ServiceInfo actual) {
        assertTrue(actual.name.endsWith(expected.name));
    }
}
