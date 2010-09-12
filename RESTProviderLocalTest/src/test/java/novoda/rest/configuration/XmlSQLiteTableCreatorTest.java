package novoda.rest.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import novoda.rest.database.SQLiteType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

@RunWith(MockitoJUnitRunner.class)
public class XmlSQLiteTableCreatorTest {

	XmlPullParser xtc;
	private XmlSQLiteTableCreator creator;

	@Before
	public void initRequestMocks() throws IOException, XmlPullParserException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
				System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		xpp.setInput(new FileReader(new File(System.getProperty("user.dir"),
				"src/test/resources/novoda/rest/configuration/sqlite.xml")));
		xtc = xpp;
		xtc.getEventType();
		
		// get ride of start document
		xtc.next();
		creator = new XmlSQLiteTableCreator(xtc);
	}

	@Test
	public void testProcessTag() {
		assertEquals("table", creator.getTableName());
	}
	
	@Test
	public void testGetTableField() throws Exception {
		assertArrayEquals(new String[] {"id", "name"}, creator.getTableFields());
	}
	
	@Test
	public void testGetType() {
		assertEquals(SQLiteType.INTEGER, creator.getType("id"));
		assertEquals(SQLiteType.TEXT, creator.getType("name"));
	}
	
	@Test
	public void testIsNullAllowed() {
		assertEquals(false, creator.isNullAllowed("id"));
		assertEquals(true, creator.isNullAllowed("name"));
	}
	
	@Test
	public void testUniqueAllowed() {
		assertEquals(true, creator.isUnique("id"));
		assertEquals(false, creator.isUnique("name"));
	}
	
	@Test
	public void testPrimaryKey() {
		assertEquals("id", creator.getPrimaryKey());
	}
}
