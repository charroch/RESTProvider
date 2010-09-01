
package novoda.rest.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import novoda.rest.net.UserAgent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;

public abstract class HttpService extends IntentService {

    private static final String USER_AGENT = new UserAgent.Builder().with("RESTProvider").build();

    private static final int GET = 1;

    private static final int POST = 2;

    private static final int DELETE = 3;

    private static final int PUT = 4;

    private Intent intent;

    private HttpClient client;

    private HttpUriRequest request;

    public HttpService(String name) {
        super(name);
        client = getHttpClient();
    }

    /* package */HttpClient getHttpClient() {
        return android.net.http.AndroidHttpClient.newInstance(USER_AGENT, getBaseContext());
    }

    public HttpService() {
        this(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        this.intent = intent;
        final Uri uri = intent.getData();
        final int method = intent.getIntExtra("method", -1);
        final List<ParcelableBasicNameValuePair> params = intent
                .getParcelableArrayListExtra("params");

        switch (method) {
            case GET:
                request = new HttpGet(getURIFromUri(uri, params));
                break;
            case POST:
                request = new HttpPost(uri.toString());
                ((HttpPost)request).setEntity(getPostEntity(params));
                break;
            case DELETE:
                request = new HttpDelete(uri.toString());
                break;
            case PUT:
                request = new HttpPut(uri.toString());
                break;
            default:
                throw new HttpServiceException(
                        "Method not supported, does the intent contain a method? "
                                + intent.toString());
        }

        try {
            onPreCall(request);
            HttpResponse response = client.execute(request);
            onPostCall(response);
            onHandleResponse(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private URI getURIFromUri(Uri uri, List<ParcelableBasicNameValuePair> params) {
        try {
            return URIUtils.createURI(uri.getScheme(), uri.getHost(), uri.getPort(), uri
                    .getEncodedPath(), URLEncodedUtils.format(params, "UTF-8"), uri.getFragment());
        } catch (URISyntaxException e) {
            throw new HttpServiceException();
        }
    }

    /**
     * Override this method if you intend to change the default behaviour of the
     * POST entity. By default, we return URL encoded form entity (i.e.
     * param1=value1&param2=value2).The parameters are given from the intent.
     * 
     * @param params
     * @return the entity used within the post. By default, it will use form
     *         encoded entity (i.e. param1=value1&param2=value2)
     */
    protected HttpEntity getPostEntity(List<ParcelableBasicNameValuePair> params) {
        try {
            return new UrlEncodedFormEntity(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new HttpServiceException();
        }
    }

    /**
     * Called before the request goes on the wire. Leaves a chance to the
     * implementor to change the request. If you intent to add interceptors, use
     * the constructor instead.
     * 
     * @param request, the constructed httpUriRequest from the several options
     *            given by the intent
     */
    protected void onPreCall(HttpUriRequest request) {
    }

    protected void onPostCall(HttpResponse response) {
    }

    protected Intent getIntent() {
        return intent;
    }

    protected abstract void onHandleResponse(HttpResponse response);
}
