
package novoda.rest.services;

import novoda.rest.mock.MockHttpClient;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import android.content.Intent;
import android.net.Uri;
import android.test.ServiceTestCase;

import java.util.ArrayList;

public class HttpServiceTest extends ServiceTestCase<ConcreteHttpService> {

    private static final String BASE_URI = "http://android.com";

    private static MockHttpClient client;

    public HttpServiceTest(Class<ConcreteHttpService> serviceClass) {
        super(serviceClass);
    }

    public HttpServiceTest() {
        this(ConcreteHttpService.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setupService();
        client = new MockHttpClient();
        getService().setHttpClient(client);
    }

    @Override
    protected void tearDown() throws Exception {
        client = null;
        super.tearDown();
    }

    public void testGetRequest() throws Exception {
        client.expectType(HttpGet.class);
        Intent intent = new Intent(HttpService.ACTION_GET, Uri.parse(BASE_URI));
        getService().onHandleIntent(intent);
    }

    public void testPostRequest() throws Exception {
        client.expectType(HttpPost.class);
        Intent intent = new Intent(HttpService.ACTION_POST, Uri.parse(BASE_URI));
        getService().onHandleIntent(intent);
    }

    public void testDeleteRequest() throws Exception {
        client.expectType(HttpDelete.class);
        Intent intent = new Intent(HttpService.ACTION_DELETE, Uri.parse(BASE_URI));
        getService().onHandleIntent(intent);
    }

    public void testPutRequest() throws Exception {
        client.expectType(HttpPut.class);
        Intent intent = new Intent(HttpService.ACTION_UPDATE, Uri.parse(BASE_URI));
        getService().onHandleIntent(intent);
    }

    public void testGetRequestWithParams() throws Exception {

        client.expectUri(BASE_URI + "/?test=2&another=myString");

        ArrayList<ParcelableBasicNameValuePair> params = new ArrayList<ParcelableBasicNameValuePair>();
        params.add(new ParcelableBasicNameValuePair("test", "2"));
        params.add(new ParcelableBasicNameValuePair("another", "myString"));

        Intent intent = new Intent(HttpService.ACTION_GET, Uri.parse(BASE_URI));
        intent.putParcelableArrayListExtra("params", params);
        getService().onHandleIntent(intent);
    }

    public void testGetRequestWithParamsInUri() throws Exception {
        client.expectUri(BASE_URI + "/?test=2&another=myString");
        Intent intent = new Intent(HttpService.ACTION_GET, Uri.parse(BASE_URI
                + "?test=2&another=myString"));
        getService().onHandleIntent(intent);
    }
    
    public void testGetRequestWithParamsInUriAndAsParams() throws Exception {
        client.expectUri(BASE_URI + "/?yetanother=test&test=2&another=myString");
        
        ArrayList<ParcelableBasicNameValuePair> params = new ArrayList<ParcelableBasicNameValuePair>();
        params.add(new ParcelableBasicNameValuePair("yetanother", "test"));
        
        Intent intent = new Intent(HttpService.ACTION_GET, Uri.parse(BASE_URI
                + "?test=2&another=myString"));
        
        intent.putParcelableArrayListExtra("params", params);
        getService().onHandleIntent(intent);
    }

    public void testPostRequestWithParams() throws Exception {

        client.expectUri(BASE_URI).expectPostParams("test=2&another=myString");

        ArrayList<ParcelableBasicNameValuePair> params = new ArrayList<ParcelableBasicNameValuePair>();
        params.add(new ParcelableBasicNameValuePair("test", "2"));
        params.add(new ParcelableBasicNameValuePair("another", "myString"));

        Intent intent = new Intent(HttpService.ACTION_POST, Uri.parse(BASE_URI));
        intent.putParcelableArrayListExtra("params", params);
        getService().onHandleIntent(intent);
    }
}
