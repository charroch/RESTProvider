
package novoda.rest.configuration;

import android.os.Parcel;
import android.os.Parcelable;

public class RESTServiceInfo implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    public RESTServiceInfo() {
        // For testing
    }
    
    public RESTServiceInfo(Parcel parcel) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static final Parcelable.Creator<RESTServiceInfo> CREATOR = new Parcelable.Creator<RESTServiceInfo>() {
        @Override
        public RESTServiceInfo createFromParcel(Parcel parcel) {
            return new RESTServiceInfo(parcel);
        }

        @Override
        public RESTServiceInfo[] newArray(int size) {
            return new RESTServiceInfo[size];
        }
    };
}
