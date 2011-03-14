package novoda.rest.services;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import novoda.rest.net.AndroidHttpClient;
import novoda.rest.net.UserAgent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.util.TimingLogger;

public abstract class HttpService extends IntentService {

	private static final String TAG = HttpService.class.getSimpleName();

	/*
	 * In order to enable ensure you run "setprop log.tag.HttpService VERBOSE"
	 * in adb
	 */
	private TimingLogger logger = new TimingLogger(TAG, "lifecycle");

	private static final String USER_AGENT = new UserAgent.Builder().with(
			"RESTProvider").build();

	public static final int GET = 1;

	public static final int POST = 2;

	public static final int DELETE = 3;

	public static final int PUT = 4;

	public static final String ACTION_GET = "novoda.rest.http.GET_REQUEST";

	public static final String ACTION_POST = "novoda.rest.http.POST_REQUEST";

	public static final String ACTION_UPDATE = "novoda.rest.http.PUT_REQUEST";

	public static final String ACTION_DELETE = "novoda.rest.http.DELETE_REQUEST";

	public static final String ACTION_QUERY = "novoda.rest.cp.QUERY";

	private static final String EXTRA_STATUS_RECEIVER = "novoda.rest.extra.STATUS_RECEIVER";

	private static final int STATUS_FINISHED = 0;

	private static final int STATUS_ERROR = 1;

	private Intent intent;

	protected AndroidHttpClient client;

	private HttpUriRequest request;

	private ResultReceiver receiver;

	public HttpService(String name) {
		super(name);
		if (client == null) {
			client = getHttpClient();
			if (Log.isLoggable(TAG, Log.VERBOSE)) {
				client.enableCurlLogging(TAG, Log.VERBOSE);
			}
		}
	}

	public HttpService() {
		this(HttpService.class.getSimpleName());
	}

	@Override
	public void onDestroy() {
		client.getConnectionManager().shutdown();
		client.close();
		super.onDestroy();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		this.intent = intent;
		receiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		try {
			request = getHttpUriRequest(intent);
			HttpContext context = getHttpContext();
			context.setAttribute("intent", intent);
			onPreCall(request, context);
			HttpResponse response = client.execute(request, context);
			onPostCall(response, context);
			onHandleResponse(response, context);
		} catch (Exception e) {
			onThrowable(e);
		} finally {
			onFinishCall();
		}
	}

	protected void onThrowable(Exception e) {
		if (Log.isLoggable(TAG, Log.ERROR)) {
			Log.e(TAG, "an error occured against intent: " + intent);
			Log.e(TAG, e.getMessage() + "");
		}
		if (Log.isLoggable(TAG, Log.DEBUG)) {
			Log.e(TAG, "full stack trace:", e);
		}

		if (receiver != null) {
			final Bundle bundle = new Bundle();
			bundle.putString(Intent.EXTRA_TEXT, e.toString());
			receiver.send(STATUS_ERROR, bundle);
		}
	}

	protected HttpUriRequest getHttpUriRequest(Intent intent) {
		final Uri uri = intent.getData();
		int method = -1;

		List<ParcelableBasicNameValuePair> params = intent
				.getParcelableArrayListExtra("params");

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
			StringBuilder query = new StringBuilder(URLEncodedUtils.format(
					params, "UTF-8"));
			if (uri.getQuery() != null && uri.getQuery().length() > 3) {
				if (params.size() > 0)
					query.append('&');
				query.append(uri.getQuery());
			}
			return URIUtils.createURI(uri.getScheme(), uri.getHost(),
					uri.getPort(), uri.getEncodedPath(), query.toString(),
					uri.getFragment());
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
		HttpContext context = new BasicHttpContext();
		context.setAttribute("novoda.rest.intent", getIntent());
		return context;
	}

	/**
	 * Called before the request goes on the wire. Leaves a chance to the
	 * implementor to change the request. If you intent to add interceptors, use
	 * the constructor instead.
	 * 
	 * @param request
	 *            , the constructed httpUriRequest from the several options
	 *            given by the intent
	 */
	protected void onPreCall(HttpUriRequest request, HttpContext context) {
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
			logger.addSplit("on pre call: "
					+ request.getRequestLine().toString());
		}
	}

	protected void onPostCall(HttpResponse response, HttpContext context) {
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
			logger.addSplit("on post call: "
					+ response.getStatusLine().toString());
		}
	}

	protected Intent getIntent() {
		return intent;
	}

	/* package */AndroidHttpClient getHttpClient() {
		return AndroidHttpClient.newInstance(USER_AGENT, getBaseContext());
	}

	/* package */void setHttpClient(AndroidHttpClient client) {
		this.client = client;
	}

	protected abstract void onHandleResponse(HttpResponse response,
			HttpContext context);

	protected void onFinishCall() {
		if (Log.isLoggable(TAG, Log.VERBOSE)) {
			logger.dumpToLog();
		}
		if (receiver != null)
			receiver.send(STATUS_FINISHED, Bundle.EMPTY);
	}
}
