
package novoda.rest.net;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

public class Test implements ETag {

    public static void main(String[] arg) throws ClientProtocolException, IOException {
             Test e = new Test();
        
        DefaultHttpClient client = new DefaultHttpClient();
        client.addRequestInterceptor(new ETagRequestInterceptor(e));
        client.addResponseInterceptor(new ETagResponseInterceptor(e));
        client.execute(new HttpGet("http://bitworking.org/news/?test=23"));
    }

    @Override
    public String getEntityTag(String request) {
        System.out.println("re: " + request);
        return "\"31980a2-16e1-48c193c162d40\"";
    }

    @Override
    public String getLastModified(String request) {
        return "Sat, 24 Jul 2010 02:51:57 GMT";
    }

    @Override
    public void save(String attribute, String last, String value) {
        System.out.println("something: " + attribute + "\n" + value +  "\n" + last);
    }

}
