
package novoda.lib.rest.actor;

import com.google.gson.stream.JsonReader;

import novoda.lib.rest.marshaller.net.JsonMarshaller;

public abstract class JSONPersistingActor extends PersistingActor<JsonReader, JsonMarshaller> {

    @Override
    protected JsonMarshaller getMarshaller() {
        return new JsonMarshaller();
    }
}
