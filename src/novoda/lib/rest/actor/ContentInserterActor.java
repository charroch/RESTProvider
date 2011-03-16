
package novoda.lib.rest.actor;

import com.novoda.lib.httpservice.actor.Actor;

import novoda.lib.rest.marshaller.IContentProviderOperationMarshaller;
import novoda.lib.rest.marshaller.MarshallingException;
import novoda.lib.rest.marshaller.net.HttpResponseMarshaller;

import org.apache.http.HttpResponse;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import java.io.IOException;
import java.util.ArrayList;

public abstract class ContentInserterActor<To, M extends HttpResponseMarshaller<To>> extends Actor
        implements IContentProviderOperationMarshaller<To> {

    @Override
    public void onResponseReceived(HttpResponse httpResponse) {
        ArrayList<ContentProviderOperation> operations = null;
        try {
            operations = marshall(getMarshaller().marshall(
                    httpResponse));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (MarshallingException e1) {
            e1.printStackTrace();
        }
        try {
            getHttpContext().getContentResolver().applyBatch(getAuthority(), operations);
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
