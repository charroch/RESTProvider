
package novoda.rest.cursors;

import novoda.rest.cursors.CursorController.CursorParams;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.BufferedHttpEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CursorBuilder<T extends ResponseCursor> implements ResponseHandler<T> {

    public T cursor;

    public List<Builder<T>> children = new ArrayList<Builder<T>>();

    public CursorBuilder(Builder<T> builder) {
    }

    protected int getExpectedResponseCode() {
        return HttpStatus.SC_OK;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        if (response == null || cursor == null) {
            throw new IOException("response or cursor is null");
        }
        try {
            if (getExpectedResponseCode() == response.getStatusLine().getStatusCode()) {
                cursor.parse(new BufferedHttpEntity(response.getEntity()).getContent());
                cursor.init();
            }
        } finally {
            response.getEntity().consumeContent();
        }
        return cursor;
    }

    public static final class Builder<T extends ResponseCursor> {
        private final CursorController.CursorParams P;

        private final List<Builder<T>> children = new ArrayList<Builder<T>>();

        public Builder() {
            P = new CursorController.CursorParams();
        }

        public Builder<T> withFieldID(final String fieldID) {
            return withFieldID(fieldID, false);
        }

        public Builder<T> withRootNode(final String rootNode) {
            P.rootName = rootNode;
            return this;
        }

        public Builder<T> withNodeName(final String nodeName) {
            P.nodeName = nodeName;
            return this;
        }

        public Builder<T> withFieldID(String fieldID, boolean shouldBeUnderscore) {
            if (shouldBeUnderscore)
                P.mapper.put("_id", fieldID);
            return this;
        }

        public Builder<T> withMappedField(final String original, final String newValue) {
            P.mapper.put(original, newValue);
            return this;
        }

        public Builder<T> withAutoID() {
            P.withAutoId = true;
            return this;
        }

        public Builder<T> withChildren(ResponseCursor... child) {
            P.withChildren = new ArrayList<ResponseCursor>(Arrays.asList(child));
            return this;
        }

        public CursorBuilder<T> create(Class<T> clazz) {
            CursorBuilder<T> builder = new CursorBuilder<T>(this);
            try {
                builder.cursor = clazz.newInstance();
                builder.cursor.params = (CursorParams) this.P;
                builder.children.addAll(children);
                return builder;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            throw new IllegalStateException("can not instantiate class: " + clazz.getSimpleName()
                    + ", do not add a constructor...");
        }

        public T build(Class<T> clazz) {
            CursorBuilder<T> builder = create(clazz);
            builder.cursor.init();
            return builder.cursor;
        }

        public Builder<T> withChildren(Builder<T>... child) {
            children.addAll(Arrays.asList(child));
            return this;
        }
    }
}
