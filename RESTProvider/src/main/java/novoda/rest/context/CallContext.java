package novoda.rest.context;

import novoda.rest.database.SQLiteTableCreator;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class CallContext implements Parcelable {

    SQLiteTableCreator creator;
    
    Uri uri;
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int arg1) {
    }

}
