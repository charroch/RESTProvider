
package novoda.rest.example.actor;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import novoda.lib.rest.actor.JSONPersistingActor;
import novoda.lib.rest.marshaller.MarshallingException;

import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.net.Uri;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;

public class TwitterTrends extends JSONPersistingActor {

    @Override
    public ArrayList<ContentProviderOperation> marshall(JsonReader reader) throws IOException,
            MarshallingException {
        
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
        Builder newInsert = null;
        String name = null;
        String value = null;
        while (true) {
            JsonToken token = reader.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    reader.beginArray();
                    break;
                case END_ARRAY:
                    reader.endArray();
                    break;
                case BEGIN_OBJECT:
                    reader.beginObject();
                    newInsert = ContentProviderOperation.newInsert(Uri
                            .parse("log://twitter_stream"));
                    break;
                case END_OBJECT:
                    reader.endObject();
                    if (newInsert != null) {
                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(value)) {
                            newInsert.withValue(name, value);
                            operations.add(newInsert.build());
                        }
                    }
                    break;
                case NAME:
                    name = reader.nextName();
                    break;
                case STRING:
                    value = reader.nextString();
                    break;
                case END_DOCUMENT:
                    return operations;
            }
        }
    }

    @Override
    protected String getAuthority() {
        return "novoda.rest.log";
    }
}
