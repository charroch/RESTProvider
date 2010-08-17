
package novoda.rest.clag;

import novoda.rest.database.SQLiteTableCreator;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ServiceDescription implements Parcelable {
    public String name;

    public float version;

    public List<String> services;

    public List<SQLiteTableCreator> schemas;

    public ServiceDescription(Parcel parcel) {
        name = parcel.readString();
        version = parcel.readFloat();
        services = parcel.readArrayList(null);
        schemas = parcel.readArrayList(null); 
    }

    public ServiceDescription() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
    }

    public static final Parcelable.Creator<ServiceDescription> CREATOR = new Parcelable.Creator<ServiceDescription>() {
        @Override
        public ServiceDescription createFromParcel(Parcel parcel) {
            return new ServiceDescription(parcel);
        }

        @Override
        public ServiceDescription[] newArray(int size) {
            return new ServiceDescription[size];
        }
    };
}
