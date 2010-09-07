
package novoda.rest.net;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserAgentTest {

    @Test
    public void testSimpleAgent() {
        assertEquals("Android/null (test)", new UserAgent.Builder().with("test").build());
    }
}
