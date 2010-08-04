package novoda.rest.database;

import static org.junit.Assert.*;
import novoda.rest.RESTProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import android.net.Uri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UriTableCreatorTest {
    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        RESTProvider.DEBUG = false;
    }

    @Test
    public void testParentTableName() throws Exception {
        UriTableCreator c = new UriTableCreator(Uri.parse("content://uri/parent/2/child")) {
            @Override
            public String[] getTableFields() {
                return null;
            }
        };
        assertEquals(c.getParentColumnName(), "parent_id");
        assertEquals(c.getTableName(), "child");
        c.setUri(Uri.parse("content://uri/parent/#/child"));
        assertEquals(c.getParentColumnName(), "parent_id");
        assertEquals(c.getTableName(), "child");
    }
}
