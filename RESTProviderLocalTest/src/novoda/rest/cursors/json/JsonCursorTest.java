
package novoda.rest.cursors.json;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonCursorTest {
    
    @Mock
    JsonCursor jsonCursor;

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSignHttpRequestMessage() throws Exception {
        when(jsonCursor.getColumnCount()).thenReturn(2);
        assertTrue(jsonCursor.getColumnCount() == 2);
    }
}
