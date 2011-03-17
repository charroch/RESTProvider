
package novoda.lib.rest.actor;

import com.novoda.lib.httpservice.actor.Actor;
import com.novoda.lib.httpservice.utils.Log;

import novoda.lib.rest.marshaller.IContentProviderOperationMarshaller;
import novoda.lib.rest.marshaller.MarshallingException;
import novoda.lib.rest.marshaller.net.HttpResponseMarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Carl-Gustaf Harroch
 * @param <To> The type of desired
 * @param <M> A marshaller that transforms an httpresponse into either JSON or
 *            XML for further parsing - can be extended
 */
public abstract class PersistingActor<To, M extends HttpResponseMarshaller<To>> extends Actor
        implements IContentProviderOperationMarshaller<To> {

    @Override
    public void onResponseReceived(HttpResponse httpResponse) {
        ArrayList<ContentProviderOperation> operations = null;
        try {
            operations = marshall(getMarshaller().marshall(httpResponse));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (MarshallingException e1) {
            e1.printStackTrace();
        }
        try {
            if (operations != null) {
                getHttpContext().getContentResolver().applyBatch(getAuthority(), operations);
            } else {
                Log.w("Operation list is null... No insert will happen", null);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
        super.onResponseReceived(httpResponse);
    }

    protected abstract String getAuthority();

    protected abstract M getMarshaller();

}
