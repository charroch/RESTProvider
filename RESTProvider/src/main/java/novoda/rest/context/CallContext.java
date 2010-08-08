
package novoda.rest.context;

import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.net.ETag;
import novoda.rest.parsers.Node;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.ref.WeakReference;

public class CallContext implements Parcelable {

    SQLiteTableCreator creator;

    short status;

    long createdAt;

    long finishedAt;

    int contentLenght;

    WeakReference<Node<?>> node;

    Uri uri;
    
    ETag etag;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int arg1) {
    }

}
