/*
 *   Copyright 2010, Novoda ltd 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package novoda.rest.interceptors;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.signature.OAuthMessageSigner;
import oauth.signpost.signature.SigningStrategy;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

/**
 * The Class OAuthInterceptor which adds OAuth signing to the request using the
 * pattern approach.
 */
public class OAuthInterceptor implements HttpRequestInterceptor, OAuthConsumer {

    /** The Constant TAG. */
    private static final String TAG = OAuthInterceptor.class.getSimpleName();

    /** The consumer. */
    private final CommonsHttpOAuthConsumer consumer;

    /**
     * Instantiates a new o auth interceptor.
     * 
     * @param key the key
     * @param secret the secret
     */
    public OAuthInterceptor(final String key, final String secret) {
        consumer = new CommonsHttpOAuthConsumer(key, secret);
    }

    /**
     * Instantiates a new OAuth interceptor which is set for that consumer
     * key/secret combination and token key/secret combination.
     * 
     * @param key the key
     * @param secret the secret
     * @param token the token
     * @param tokenSecret the token secret
     */
    public OAuthInterceptor(final String key, final String secret, final String token,
            final String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(key, secret);
        setTokenWithSecret(token, tokenSecret);
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#setTokenWithSecret(java.lang.String,
     * java.lang.String)
     */
    public void setTokenWithSecret(final String token, final String tokenSecret) {
        consumer.setTokenWithSecret(token, tokenSecret);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apache.http.HttpRequestInterceptor#process(org.apache.http.HttpRequest
     * , org.apache.http.protocol.HttpContext)
     */
    @Override
    public void process(final HttpRequest request, final HttpContext context) throws HttpException,
            IOException {
        Log.i(TAG, "request + " + request.toString());
        if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }

        if (request instanceof RequestWrapper) {
            try {
                for (final Entry<String, String> e : consumer.sign(
                        ((RequestWrapper)request).getOriginal()).getAllHeaders().entrySet()) {
                    request.addHeader(e.getKey(), e.getValue());
                }
                Log.i(TAG, Arrays.toString(request.getAllHeaders()));
            } catch (final OAuthMessageSignerException e) {
                Log.e(TAG, "an error occured in process", e);
            } catch (final OAuthExpectationFailedException e) {
                Log.e(TAG, "an error occured in process", e);
            } catch (final OAuthCommunicationException e) {
                Log.e(TAG, "an error occured in process", e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#getConsumerKey()
     */
    @Override
    public String getConsumerKey() {
        return consumer.getConsumerKey();
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#getConsumerSecret()
     */
    @Override
    public String getConsumerSecret() {
        return consumer.getConsumerSecret();
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#getRequestParameters()
     */
    @Override
    public HttpParameters getRequestParameters() {
        return consumer.getRequestParameters();
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#getToken()
     */
    @Override
    public String getToken() {
        return consumer.getToken();
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#getTokenSecret()
     */
    @Override
    public String getTokenSecret() {
        return consumer.getTokenSecret();
    }

    /*
     * (non-Javadoc)
     * @see
     * oauth.signpost.OAuthConsumer#setAdditionalParameters(oauth.signpost.http
     * .HttpParameters)
     */
    @Override
    public void setAdditionalParameters(final HttpParameters additionalParameters) {
        consumer.setAdditionalParameters(additionalParameters);
    }

    /*
     * (non-Javadoc)
     * @see
     * oauth.signpost.OAuthConsumer#setMessageSigner(oauth.signpost.signature
     * .OAuthMessageSigner)
     */
    @Override
    public void setMessageSigner(final OAuthMessageSigner messageSigner) {
        consumer.setMessageSigner(messageSigner);
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#setSendEmptyTokens(boolean)
     */
    @Override
    public void setSendEmptyTokens(final boolean enable) {
        consumer.setSendEmptyTokens(enable);
    }

    /*
     * (non-Javadoc)
     * @see
     * oauth.signpost.OAuthConsumer#setSigningStrategy(oauth.signpost.signature
     * .SigningStrategy)
     */
    @Override
    public void setSigningStrategy(final SigningStrategy signingStrategy) {
        consumer.setSigningStrategy(signingStrategy);
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#sign(oauth.signpost.http.HttpRequest)
     */
    @Override
    public oauth.signpost.http.HttpRequest sign(final oauth.signpost.http.HttpRequest arg0)
            throws OAuthMessageSignerException, OAuthExpectationFailedException,
            OAuthCommunicationException {
        return consumer.sign(arg0);
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#sign(java.lang.String)
     */
    @Override
    public String sign(final String arg0) throws OAuthMessageSignerException,
            OAuthExpectationFailedException, OAuthCommunicationException {
        return consumer.sign(arg0);
    }

    /*
     * (non-Javadoc)
     * @see oauth.signpost.OAuthConsumer#sign(java.lang.Object)
     */
    @Override
    public oauth.signpost.http.HttpRequest sign(final Object arg0)
            throws OAuthMessageSignerException, OAuthExpectationFailedException,
            OAuthCommunicationException {
        return consumer.sign((Object)arg0);
    }
}
