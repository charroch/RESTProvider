
package novoda.rest.utils;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class HTTPUtilsTest {

    @Mock
    Map<String, String> params;

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMap2HCParams() throws Exception {
        //when(params.size()).thenReturn(5);
        
    }
}
