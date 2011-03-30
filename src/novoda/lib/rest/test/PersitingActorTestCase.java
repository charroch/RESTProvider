
package novoda.lib.rest.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import novoda.lib.rest.actor.PersistingActor;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.test.AndroidTestCase;
import android.test.mock.MockContentProvider;
import android.test.mock.MockContentResolver;
import android.util.Pair;

import com.novoda.lib.httpservice.controller.ContextHttpWrapper;

@SuppressWarnings("rawtypes")
public class PersitingActorTestCase<T extends PersistingActor> extends AndroidTestCase {

    T actor;

    private HttpResponse httpResponse;

    private MockHttpContext context;

    AssertContentResolver resolver;

    public PersitingActorTestCase(Class<T> persistingActor) {
        try {
            resolver = new AssertContentResolver();
            context = new MockHttpContext(getContext());
            actor = persistingActor.newInstance();
            actor.applyContext(context);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public PersitingActorTestCase<T> given(String httpResponseBody) {
        httpResponse = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("http", 1, 1),
                200, "OK"));
        StringEntity entity = null;
        try {
            entity = new StringEntity(httpResponseBody);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpResponse.setEntity(entity);
        return this;
    }

    public PersitingActorTestCase<T> whenReceived() {
        actor.onResponseReceived(httpResponse);
        return this;
    }

    public void inserts(List<Pair<Uri, ContentValues>> values) {
        assertEquals(values.size(), resolver.operations.size());
        int i = 0;
        for (ContentProviderOperation op : resolver.operations) {
            try {
                op.apply(MockProvider.assertAgainst(values.get(i++)), null, 0);
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }
        }
    }

    public T getActor() {
        return actor;
    }

    private class MockHttpContext extends ContextHttpWrapper {

        public MockHttpContext(Context base) {
            super(base);
        }

        @Override
        public ContentResolver getContentResolver() {
            return resolver;
        }
    }

    private class AssertContentResolver extends MockContentResolver {
        private ArrayList<ContentProviderOperation> operations;

        @Override
        public ContentProviderResult[] applyBatch(String authority,
                ArrayList<ContentProviderOperation> operations) throws RemoteException,
                OperationApplicationException {
            this.operations = operations;
            return null;
        }
    }

    private static class MockProvider extends MockContentProvider {

        private Pair<Uri, ContentValues> valuesAsserted;

        public MockProvider(Pair<Uri, ContentValues> values) {
            this.valuesAsserted = values;
        }

        public static ContentProvider assertAgainst(Pair<Uri, ContentValues> values) {
            return new MockProvider(values);
        }

        @Override
        public Uri insert(Uri uri, ContentValues values) {
            assertEquals(valuesAsserted.first, uri);
            assertEquals(valuesAsserted.second, values);
            return Uri.parse("mock://some.com");
        }
    }
}
