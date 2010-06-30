
package novoda.rest.database;

import static org.mockito.Mockito.when;
import novoda.rest.RESTProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ModularSQLiteOpenHelperTest {

    
    ModularSQLiteOpenHelper helper;

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
        RESTProvider.DEBUG = false;
    }

    @Test
    public void testSQLiteCreateCorretly() throws Exception {
        helper = new ModularSQLiteOpenHelper(null);
    }

}
