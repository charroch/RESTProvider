
package novoda.rest.net;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class HttpCallInfo implements Parcelable {

    /**
     * Uniform Resource Identifier against which we will query using HttpClient.
     * Not to be confused with Android's internal Uri mechanism. We use
     * android.net.Uri here as it is already implementing parcelable.
     */
    public Uri uri;

    protected HttpCallInfo(Parcel parcel) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
    }

    public static final Parcelable.Creator<HttpCallInfo> CREATOR = new Parcelable.Creator<HttpCallInfo>() {
        @Override
        public HttpCallInfo createFromParcel(Parcel parcel) {
            return new HttpCallInfo(parcel);
        }

        @Override
        public HttpCallInfo[] newArray(int size) {
            return new HttpCallInfo[size];
        }
    };
}
