
package novoda.rest.providers;

import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import android.test.mock.MockPackageManager;

public class RESTProviderTest extends AndroidTestCase {

    private Bundle bundle;

    private RESTProvider mProvider;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setContext(new ProviderContext());
        mProvider = RESTProvider.class.newInstance();
        mProvider.attachInfo(getContext(), null);
        assertNotNull(mProvider);
    }

    public void testGetServiceFromMetadata() throws Exception {
        Bundle bundle = new Bundle();
        getContext().getResources().getIdentifier("metadata", "xml", "novoda.rest.test");
        bundle.putString("name", "novoda.rest");
        bundle.putString("resource", "@xml/test");
        setBundle(bundle);

        getContext().getPackageManager();
        assertEquals(mProvider.getService().getClass().getSimpleName(),
                "novoda.rest.test.MyRestService");

    }

    public void testShoudlThrowExceptionIfNoMetadata() throws Exception {
        setBundle(null);
        try {
            mProvider.getService();
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public class ProviderContext extends MockContext {
        @Override
        public PackageManager getPackageManager() {
            return new MockPackageManager() {
                @Override
                public ProviderInfo resolveContentProvider(String name, int flags) {
                    ProviderInfo info = new ProviderInfo();
                    info.metaData = getBundle();
                    return info;
                }
            };
        }
    }
}
