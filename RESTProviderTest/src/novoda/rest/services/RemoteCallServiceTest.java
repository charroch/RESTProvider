package novoda.rest.services;

import novoda.rest.configuration.RESTServiceInfo;
import android.test.ServiceTestCase;

public class RemoteCallServiceTest extends
		ServiceTestCase<DefaultRemoteCallService> {

	public RemoteCallServiceTest(Class<DefaultRemoteCallService> serviceClass) {
		super(serviceClass);
	}

	public RemoteCallServiceTest() {
		super(DefaultRemoteCallService.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setupService();
		assertNotNull(getService());
	}

	public void testSetupWithOptions() throws Exception {
		RESTServiceInfo serviceInfo = new RESTServiceInfo();
		getService().attachInfo(getContext(), serviceInfo);
	}

}
