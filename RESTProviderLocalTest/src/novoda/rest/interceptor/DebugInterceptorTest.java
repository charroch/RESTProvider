
package novoda.rest.interceptor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doAnswer;

import java.io.IOException;

import novoda.rest.interceptors.DebugInterceptor;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class DebugInterceptorTest {
    
    @Mock
    DebugInterceptor interceptor;

    @Mock
    HttpResponse response;
    
    @Before
    public void initRequestMocks() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSignHttpRequestMessage() throws Exception {
       //response.getEntity()
        
        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                HttpResponse r = (HttpResponse)args[0];
                Mock mock = (Mock)invocation.getMock();
                return null;
            }})
        .when(interceptor).process(response, null);
        verify(response).getProtocolVersion();
       
    }
}
