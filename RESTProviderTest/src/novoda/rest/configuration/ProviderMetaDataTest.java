
package novoda.rest.configuration;

import novoda.rest.configuration.ProviderMetaData;
import novoda.rest.test.R;

import android.test.InstrumentationTestCase;

public class ProviderMetaDataTest extends InstrumentationTestCase {

    public void testLoadingFromXML() throws Exception {
        ProviderMetaData m = ProviderMetaData.loadFromXML(getInstrumentation().getContext()
                .getResources().getXml(R.xml.metadata));
        assertEquals(m.serviceClassName, "test.com");
    }
}
