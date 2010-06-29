
package novoda.rest.test.apps.linkedin;

import com.google.gdata.client.authn.oauth.OAuthParameters;

import novoda.rest.DefaultRESTProvider;
import novoda.rest.RESTProvider;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.AbstractCursor;
import android.net.Uri;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class LinkedInProvider extends DefaultRESTProvider {

    private static final String TAG = LinkedInProvider.class.getSimpleName();

    private OAuthConsumer consumer;

    private OnSharedPreferenceChangeListener listener = new OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (sharedPreferences.contains(OAuthParameters.OAUTH_TOKEN_KEY)
                    && sharedPreferences.contains(OAuthParameters.OAUTH_TOKEN_SECRET_KEY)) {
                consumer.setTokenWithSecret(sharedPreferences.getString(
                        OAuthParameters.OAUTH_TOKEN_KEY, ""), sharedPreferences.getString(
                        OAuthParameters.OAUTH_TOKEN_SECRET_KEY, ""));
            }
        }
    };

    @Override
    public boolean onCreate() {
        SharedPreferences pref = getContext().getSharedPreferences(Constants.SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        pref.registerOnSharedPreferenceChangeListener(listener);

        consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY,
                Constants.CONSUMER_KEY_SECRET);

        consumer.setTokenWithSecret(pref.getString(OAuthParameters.OAUTH_TOKEN_KEY, ""), pref
                .getString(OAuthParameters.OAUTH_TOKEN_SECRET_KEY, ""));

        if (consumer.getConsumerKey().equals("") || consumer.getConsumerKey() == null)
            return false;
        return super.onCreate();
    }

    @Override
    public HttpUriRequest getRequest(Uri uri, int type, Map<String, String> params) {
        if (type == RESTProvider.QUERY) {
            HttpGet get = new HttpGet("http://api.linkedin.com/v1/people/~/network");
            try {
                consumer.sign(get);
            } catch (OAuthMessageSignerException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            } catch (OAuthExpectationFailedException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            } catch (OAuthCommunicationException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            }
            return get;
        } else if (type == RESTProvider.UPDATE) {
            HttpPut put = new HttpPut("http://api.linkedin.com/v1/people/~/current-status");
            try {
                put.setEntity(new StringEntity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                        + "<current-status>" + params.get(Constants.CURRENT_STATUS)
                        + "</current-status>"));
                consumer.sign(put);
            } catch (OAuthMessageSignerException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            } catch (OAuthExpectationFailedException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            } catch (OAuthCommunicationException e) {
                Log.e(TAG, "an error occured in getRequest", e);
            }
            return put;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseHandler<? extends Integer> getDeleteHandler(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseHandler<? extends Uri> getInsertHandler(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseHandler<? extends AbstractCursor> getQueryHandler(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseHandler<? extends Integer> getUpdateHandler(Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
