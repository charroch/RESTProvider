package novoda.rest.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.ContentValues;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseUtilsTest {

	@Mock
	Map<String, String> params;

	@Before
	public void initRequestMocks() throws IOException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void contentValuestoTableCreate() throws Exception {
		ContentValues values = new ContentValues();
		values.put("test", "Test");
		String result = DatabaseUtils.contentValuestoTableCreate(values, "test");
		assertEquals("CREATE TABLE test (test TEXT);", result);
		
		values.put("test2", "t");
		result = DatabaseUtils.contentValuestoTableCreate(values, "test");
		assertEquals("CREATE TABLE test (test TEXT, test2 TEXT);", result);
	}
}
