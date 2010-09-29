package novoda.rest.database;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SQLiteFileParserTest {

	SQLiteFileParser parser;

	String CREATE = "CREATE TABLE IF NOT EXISTS \"tableName\"(\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ " \"_rid\" INTEGER NOT NULL UNIQUE ON CONFLICT REPLACE, \"name\" TEXT NOT NULL);";
	String INSERT_1 = "INSERT INTO dict VALUES (null,\"fixture1\", \"241\");";
	String INSERT_2 = "INSERT INTO landmarks values(5, \"fixture2\", 51.5579, -0.102236);";

	@Before
	public void setup() throws FileNotFoundException {
		File file = new File(System.getProperty("user.dir"),
				"src/test/resources/novoda/rest/database/fixture.sql");
		FileInputStream in = new FileInputStream(file);
		parser = new SQLiteFileParser(in);
	}

	@After
	public void tearDown() {
		parser.close();
	}

	@Test
	public void testSimpleParsing() throws Exception {
		assertTrue(parser.hasNext());
		assertEquals(CREATE, parser.next());
		assertTrue(parser.hasNext());
		assertEquals(INSERT_1, parser.next());
		assertTrue(parser.hasNext());
		assertEquals(INSERT_2, parser.next());
		assertFalse(parser.hasNext());
		assertNull(parser.next());
	}
}
