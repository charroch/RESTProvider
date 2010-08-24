
package novoda.rest.configuration;

import android.os.Parcel;
import android.os.Parcelable;

public class ClagMetaData implements Parcelable {

    public String endpoint;

    private ClagMetaData(Parcel in) {
        endpoint = in.readString();
    }

    public ClagMetaData() {
    }

    public static final Parcelable.Creator<ClagMetaData> CREATOR = new Parcelable.Creator<ClagMetaData>() {
        @Override
        public ClagMetaData createFromParcel(Parcel in) {
            return new ClagMetaData(in);
        }

        @Override
        public ClagMetaData[] newArray(int size) {
            return new ClagMetaData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int parcelableFlag) {
        parcel.writeString(endpoint);
    }
}
