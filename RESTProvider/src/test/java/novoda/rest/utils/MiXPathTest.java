
package novoda.rest.utils;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class MiXPathTest {

    @Mock
    Map node1;

    @Mock
    Map node2;
    
    @Mock
    Map node3;

    MiXPath<Map> xpath = new MiXPath<Map>() {
        @Override
        public Map eventArray(Map data, int index) {
            return (Map) data.get(index);
        }

        @Override
        public Map eventPath(Map data, String path) {
            return (Map) data.get(path);
        }
    };

    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSimplePath() throws Exception {
        when(node1.get(anyString())).thenReturn(node2);

        xpath.parse(node1, "node/another/");

        InOrder inOrder = inOrder(node1, node2);
        inOrder.verify(node1).get("node");
        inOrder.verify(node2).get("another");
    }

    @Test
    public void testSimpleArray() throws Exception {
        when(node2.get(anyInt())).thenReturn(node3);
        xpath.parse(node2, "[2]");
        verify(node2).get(2);
    }
}
