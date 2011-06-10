package novoda.rest.configuration;

import java.util.List;

import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.test.R;
import android.test.InstrumentationTestCase;

public class ProviderMetaDataTest extends InstrumentationTestCase {

	private ProviderMetaData m;

	@Override
	protected void setUp() throws Exception {
		m = ProviderMetaData.loadFromXML(getInstrumentation().getContext()
				.getResources().getXml(R.xml.metadata));
		super.setUp();
	}

	public void testLoadingFromXML() throws Exception {
		assertEquals(m.serviceClassName, "test.com");
	}
	
	public void testGettingUriMapper() throws Exception {
		List<SQLiteTableCreator> creators = m.getCreateStatements();
		assertEquals(1, creators.size());
	}
}
