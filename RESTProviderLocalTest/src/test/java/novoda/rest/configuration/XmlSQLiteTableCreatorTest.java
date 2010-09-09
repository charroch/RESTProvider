package novoda.rest.configuration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.XmlResourceParser;

@RunWith(MockitoJUnitRunner.class)
public class XmlSQLiteTableCreatorTest {

	XmlPullParser xtc;

	@Before
	public void initRequestMocks() throws IOException, XmlPullParserException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
				System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		xpp.setInput(new FileReader(new File(System.getProperty("user.dir"),
				"src/test/resources/novoda/rest/configuration/sqlite.xml")));
		xtc = xpp;
	}

	@Test
	public void testProcessTag() {
		//XmlSQLiteTableCreator creator = new XmlSQLiteTableCreator(xtc);
		//assertEquals("test", creator.getTableName());
	}
}
