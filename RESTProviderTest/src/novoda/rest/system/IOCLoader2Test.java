
package novoda.rest.system;

import novoda.rest.mock.StringResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.mock.MockPackageManager;
import android.test.mock.MockResources;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class IOCLoader2Test extends AndroidTestCase {
    private Bundle bundle;

    private IOCLoader loader;

    private ProviderInfo info;

    public IOCLoader2Test() {
        super();
    }
    
    IOCLoader2Test(String name) {
        super();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setContext(new ProviderContext(getContext().getContentResolver(), getContext()));

        bundle = new Bundle();
        bundle.putInt("novoda.rest", -1);

        info = new ProviderInfo();
        info.authority = "novoda.rest.test";
        info.packageName = "novoda.rest.test";
        info.name = "novoda.rest.providers.RESTProvider";
        info.metaData = bundle;

        loader = IOCLoader.getInstance(getContext());
        loader.providerInfo = info;
        assertNotNull(loader);
    }

    public void testGetServiceFromMetadata() throws Exception {
        setBundle(bundle);
        assertEquals(loader.getProviderInfo(), info);
        assertNotNull(loader.metaData);
    }

    public void testShoudlThrowExceptionIfNoMetadata() throws Exception {
        loader.providerInfo = null;
        try {
            loader.getMetaData();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    public void testGettingMetadata() throws Exception {
        assertEquals(loader.metaData.serviceClassName, "test.com");
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public class ProviderContext extends IsolatedContext {

        public ProviderContext(ContentResolver resolver, Context targetContext) {
            super(resolver, targetContext);
        }

        @Override
        public PackageManager getPackageManager() {
            return new MockPackageManager() {
                @Override
                public ProviderInfo resolveContentProvider(String name, int flags) {
                    return info;
                }

                @Override
                public String getNameForUid(int uid) {
                    return "test";
                }

                @Override
                public List<ProviderInfo> queryContentProviders(String processName, int uid,
                        int flags) {
                    assertTrue(processName.equals("test"));
                    return Arrays.asList(info);
                }

                @Override
                public XmlResourceParser getXml(String packageName, int resid,
                        ApplicationInfo appInfo) {
                    XmlPullParser xpp = null;
                    try {
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        factory.setNamespaceAware(true);
                        xpp = factory.newPullParser();
                        xpp
                                .setInput(new StringReader(
                                        "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                                + "<service xmlns:rest=\"http://novoda.github.com/RESTProvider/apk/res\" rest:name=\"test.com\">"
                                                + "</service>"));
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                    return new StringResourceParser(xpp);
                }
            };
        }

        @Override
        public Resources getResources() {
            return new MockResources() {
                @Override
                public int getIdentifier(String name, String defType, String defPackage) {
                    return -1;
                }
            };
        }
    }
}
