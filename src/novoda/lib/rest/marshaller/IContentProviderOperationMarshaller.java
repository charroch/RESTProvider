
package novoda.lib.rest.marshaller;

import android.content.ContentProviderOperation;

import java.util.ArrayList;

public interface IContentProviderOperationMarshaller<From> extends
        IMarshaller<From, ArrayList<ContentProviderOperation>> {
}
