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
import java.io.InputStream;
import java.util.Arrays;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * As with Android we can not use log4j to debug requests as set by HttpClient
 * (http://hc.apache.org/httpcomponents-client/logging.html) and I could not
 * figure out another way to debug my calls, this interceptor will print all
 * request info before it hit the wire.
 */
public class DebugInterceptor implements HttpRequestInterceptor, HttpResponseInterceptor {

    private static final String TAG = DebugInterceptor.class.getSimpleName();

    /*
     * (non-Javadoc)
     * @see
     * org.apache.http.HttpRequestInterceptor#process(org.apache.http.HttpRequest
     * , org.apache.http.protocol.HttpContext)
     */
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        Log.d(TAG, "--- request debug start ---");
        Log.d(TAG, "request: " + request);
        Log.d(TAG, "context: " + context);
        if (request != null) {
            StringBuffer buf = new StringBuffer();
            buf.append("request line: \n").append(request.getRequestLine());
            buf.append("\nheaders: \n").append(Arrays.toString(request.getAllHeaders()));
            buf.append("\nparams: \n").append(request.getParams());
            if (request instanceof HttpEntityEnclosingRequest) {
                BufferedHttpEntity entity = new BufferedHttpEntity(
                        ((HttpEntityEnclosingRequest)request).getEntity());
                buf.append("\nentity: \n").append(EntityUtils.toString(entity));
            }
            Log.d(TAG, buf.toString());
        }
        Log.d(TAG, "--- request debug end ---");
    }

    public void process(HttpResponse response, HttpContext context) throws HttpException,
            IOException {
        Log.d(TAG, "--- response debug start ---");
        Log.d(TAG, "response: " + response);
        Log.d(TAG, "context: " + context);
        if (response != null) {
            StringBuffer buf = new StringBuffer();
            buf.append("\nstatus line: \n").append(response.getStatusLine());
            buf.append("\nheaders: \n").append(Arrays.toString(response.getAllHeaders()));
            BufferedHttpEntity entity = new BufferedHttpEntity(response.getEntity());
            if (entity.isRepeatable()) {
                if (entity.getContent().markSupported()) {
                    buf.append("\nentity: \n").append(EntityUtils.toString(entity));
                    entity.getContent().reset();
                }
            }
        }
        Log.d(TAG, "--- response debug end ---");
    }

    private class DebuggableInputStream extends InputStream {

        private InputStream in;

        public DebuggableInputStream(InputStream in) {
            this.in = in;
        }

        @Override
        public int read() throws IOException {
            return in.read();
        }

        @Override
        public int read(byte[] b, int offset, int length) throws IOException {
            return super.read(b, offset, length);
        }

        @Override
        public int read(byte[] b) throws IOException {
            return super.read(b);
        }
    }
}
