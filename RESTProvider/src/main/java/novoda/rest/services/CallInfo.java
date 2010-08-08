
package novoda.rest.services;

import novoda.rest.parsers.Node;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Information you can retrieve for a specific HTTP call which is intended to be
 * inserted into SQLite.
 */
public class CallInfo implements Parcelable {

    /**
     * The query has been complete and inserted into database correctly
     */
    public static final short COMPLETE = 0;

    /**
     * The query has finished but is in failure. This could be related to
     * network, parsing, SQL failure.
     */
    public static final short FAILURE = 1;

    /**
     * The query is in progress
     */
    public static final short IN_PROGRESS = 2;

    /**
     * The query is queued to be executed - usually waiting for an another call.
     */
    public static final short QUEUED = 3;

    /**
     * The current status of the query
     * 
     * @see CallInfo#QUEUED
     * @see CallInfo#COMPLETE
     * @see CallInfo#FAILURE
     * @see CallInfo#IN_PROGRESS
     */
    public short state;

    public static final short DOWNLOADING = 4;

    public static final short PARSING = 5;

    public static final short INSERTING = 6;

    public short status;

    /**
     * The creation date of the query. The moment it was started by the system,
     * not the actual HTTP call
     */
    public long createdAt;

    /**
     * The timestamp to when the query was finished
     */
    public long finishedAt;

    /**
     * The content length of the request.
     */
    public int contentLenght;

    /**
     * The originated Uri for which this call was first generated. It might
     * differ from the Uri given within the node.
     * 
     * @see Node.Options#insertUri
     */
    public Uri originatinUri;

    /**
     * Local type of the request received by the content provider. This can be
     * either query, update, delete, insert but will usually be query.
     * 
     * @see RESTCallService#ACTION_DELETE
     * @see RESTCallService#ACTION_INSERT
     * @see RESTCallService#ACTION_QUERY
     * @see RESTCallService#ACTION_UPDATE
     */
    public String action;

    public Uri insertingUri;

    public ETag etag;

    public ContentValues values;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
        
    }

    public static final Parcelable.Creator<CallInfo> CREATOR = new Parcelable.Creator<CallInfo>() {
        @Override
        public CallInfo createFromParcel(Parcel parcel) {
            return new CallInfo(parcel);
        }

        @Override
        public CallInfo[] newArray(int size) {
            return new CallInfo[size];
        }
    };

    protected CallInfo(Parcel source) {
    }
}
