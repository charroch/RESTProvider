
package novoda.rest.services;

import novoda.rest.parsers.Node;

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
    public static final int COMPLETE = 0;

    /**
     * The query has finished but is in failure. This could be related to
     * network, parsing, SQL failure.
     */
    public static final int FAILURE = 1;

    /**
     * The query is in progress
     */
    public static final int IN_PROGRESS = 2;

    /**
     * The query is queued to be executed - usually waiting for an another call.
     */
    public static final int QUEUED = 3;

    /**
     * The current status of the query
     * 
     * @see CallInfo#QUEUED
     * @see CallInfo#COMPLETE
     * @see CallInfo#FAILURE
     * @see CallInfo#IN_PROGRESS
     */
    public int status;

    /**
     * The query is being downloaded
     */
    public static final int DOWNLOADING = 4;

    /**
     * The query is being parsed
     */
    public static final int PARSING = 5;

    /**
     * The query is being inserted into DB
     */
    public static final int INSERTING = 6;

    /**
     * The current status of the query
     * 
     * @see CallInfo#DOWNLOADING
     * @see CallInfo#PARSING
     * @see CallInfo#INSERTING
     */
    public int state;

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
     * differ from the Uri given within the node. The Uri given by the node is
     * represented by @see {@link CallInfo#insertingUri}
     * 
     * @see Node.Options#insertUri
     */
    public Uri originatingUri;

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

    /**
     * The uri within the node.
     */
    public Uri insertingUri;

    /**
     * The ETag of the request.
     */
    public ETag etag;

    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int parcelableFlags) {
        parcel.writeInt(status);
        parcel.writeInt(state);
        parcel.writeLong(createdAt);
        parcel.writeLong(finishedAt);
        parcel.writeInt(contentLenght);
        parcel.writeParcelable(originatingUri, parcelableFlags);
        parcel.writeString(action);
        parcel.writeParcelable(insertingUri, parcelableFlags);
        parcel.writeParcelable(etag, parcelableFlags);
        parcel.writeString(url);
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
        status = source.readInt();
        state = source.readInt();
        createdAt = source.readLong();
        finishedAt = source.readLong();
        contentLenght = source.readInt();
        originatingUri = source.readParcelable(null);
        action = source.readString();
        insertingUri = source.readParcelable(null);
        etag = source.readParcelable(null);
        url = source.readString();
    }
}
