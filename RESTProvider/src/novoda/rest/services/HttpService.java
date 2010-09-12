
package novoda.rest.services;

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
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.net.http.AndroidHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class HttpService extends IntentService {

    private static final String USER_AGENT = new UserAgent.Builder().with("RESTProvider").build();

    public static final int GET = 1;

    public static final int POST = 2;

    public static final int DELETE = 3;

    public static final int PUT = 4;

    public static final String ACTION_GET = "novoda.rest.http.GET_REQUEST";

    public static final String ACTION_POST = "novoda.rest.http.POST_REQUEST";

    public static final String ACTION_UPDATE = "novoda.rest.http.PUT_REQUEST";

    public static final String ACTION_DELETE = "novoda.rest.http.DELETE_REQUEST";

    public static final String ACTION_QUERY = "novoda.rest.cp.QUERY";

    private Intent intent;

    private HttpClient client;

    private HttpUriRequest request;

    private List<HttpServiceWrapper> wrappers;

    public HttpService(String name) {
        super(name);
        if (client == null)
            client = getHttpClient();
        wrappers = new ArrayList<HttpServiceWrapper>();
    }

    public HttpService() {
        this(HttpService.class.getSimpleName());
    }

    @Override
    public void onDestroy() {
        ((AndroidHttpClient) client).close();
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        this.intent = intent;
        try {
        	request = getHttpUriRequest(intent);
            HttpContext context = getHttpContext();
            context.setAttribute("intent", intent);
            onPreCall(request, context);
            HttpResponse response = client.execute(request, context);
            onPostCall(response, context);
            onHandleResponse(response, context);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected HttpUriRequest getHttpUriRequest(Intent intent) {
        final Uri uri = intent.getData();
        int method = -1;

        List<ParcelableBasicNameValuePair> params = intent.getParcelableArrayListExtra("params");

        if (params == null) {
            params = new ArrayList<ParcelableBasicNameValuePair>();
        }

        if (ACTION_GET.equals(intent.getAction())) {
            method = GET;
        } else if (ACTION_POST.equals(intent.getAction())) {
            method = POST;
        } else if (ACTION_DELETE.equals(intent.getAction())) {
            method = DELETE;
        } else if (ACTION_UPDATE.equals(intent.getAction())) {
            method = PUT;
        }

        switch (method) {
            case GET:
                request = new HttpGet(getURIFromUri(uri, params));
                break;
            case POST:
                request = new HttpPost(uri.toString());
                ((HttpPost) request).setEntity(getPostEntity(params));
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
		return request;
	}

	private URI getURIFromUri(Uri uri, List<ParcelableBasicNameValuePair> params) {
        try {
            StringBuilder query = new StringBuilder(URLEncodedUtils.format(params, "UTF-8"));
            if (uri.getQuery() != null && uri.getQuery().length() > 3) {
                if (params.size() > 0)
                    query.append('&');
                query.append(uri.getQuery());
            }
            return URIUtils.createURI(uri.getScheme(), uri.getHost(), uri.getPort(),
                    uri.getEncodedPath(), query.toString(), uri.getFragment());
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

    protected HttpContext getHttpContext() {
        return new BasicHttpContext();
    }

    /**
     * Called before the request goes on the wire. Leaves a chance to the
     * implementor to change the request. If you intent to add interceptors, use
     * the constructor instead.
     * 
     * @param request, the constructed httpUriRequest from the several options
     *            given by the intent
     */
    protected void onPreCall(HttpUriRequest request, HttpContext context) {
        for (HttpServiceWrapper wrapper : wrappers) {
            wrapper.onPreCall(request, context);
        }
    }

    protected void onPostCall(HttpResponse response, HttpContext context) {
        for (HttpServiceWrapper wrapper : wrappers) {
            wrapper.onPostCall(response, context);
        }
    }

    protected Intent getIntent() {
        return intent;
    }

    public void addHttpServiceWrapper(HttpServiceWrapper wrapper) {
        wrappers.add(wrapper);
    }

    protected abstract void onHandleResponse(HttpResponse response, HttpContext context);

    /* package */HttpClient getHttpClient() {
        return android.net.http.AndroidHttpClient.newInstance(USER_AGENT, getBaseContext());
    }

    /* package */void setHttpClient(HttpClient client) {
        this.client = client;
    }
}
