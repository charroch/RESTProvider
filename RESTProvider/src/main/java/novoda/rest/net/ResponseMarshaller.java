
package novoda.rest.net;

import novoda.rest.database.SQLiteInserter;
import novoda.rest.database.SQLiteTableCreator;

import org.apache.http.client.ResponseHandler;

import android.content.ContentValues;
import android.net.Uri;

import java.io.InputStream;

public abstract class ResponseMarshaller implements ResponseHandler<ResponseMarshaller>{

    public abstract SQLiteTableCreator getTableCreator(Uri uri);

    public abstract SQLiteInserter getInserter(Uri uri);
    
    public void insert(Uri uri, ContentValues values) {
        
    }
    
    public abstract void parse(InputStream in);
}
