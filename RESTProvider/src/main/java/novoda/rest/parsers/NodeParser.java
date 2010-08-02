
package novoda.rest.parsers;

import novoda.rest.exception.ParserException;
import novoda.rest.parsers.Node.Options;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.BufferedHttpEntity;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public abstract class NodeParser<T extends Node<?>> implements ResponseHandler<T> {

    protected NodeParser() {
    }

    Options options;

    abstract public T parse(InputStream response, Options options) throws ParserException;

    /**
     * The expected result HTTP response code. Do NOT return NOT MODIFIED (304)
     * as this will be handled automatically by the RESTProvider.
     * 
     * @return the expected status code. Usually OK 200 but could be NO CONTENT
     *         204
     */
    public int getExpectedResponse() {
        return HttpStatus.SC_OK;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        if (response == null) {
            throw new IOException("Response can not be null");
        }

        final int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = null;
        try {
            if (statusCode == getExpectedResponse()) {
                entity = new BufferedHttpEntity(response.getEntity());
                return parse(entity.getContent(), options);
            } else if (statusCode == HttpStatus.SC_NOT_MODIFIED) {
                // TODO add etag support
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        } finally {
            if (entity != null) {
                entity.consumeContent();
            }
            // Should we broadcast?
        }
        throw new ParserException("unknown error");
    }

    public static class Builder {

        Options option;

        Builder builder;

        public Builder() {
            option = new Options();
        }

        public Builder withRootNode(String root) {
            option.rootNode = root;
            return this;
        }

        public Builder withNodeName(String nodeName) {
            option.nodeName = nodeName;
            return this;
        }

        public Builder withMappedFields(Map<String, String> mapper) {
            option.mapper = mapper;
            return this;
        }

        public Builder withTableName(String table) {
            option.table = table;
            return this;
        }

        public Builder withChildren(Map<String, Options> children) {
            option.children.putAll(children);
            return this;
        }

        public Builder withInsertUri(Uri uri) {
            option.insertUri = uri;
            return this;
        }

        public <T extends NodeParser<?>> NodeParser<?> build(Class<T> klass) {
            T t = null;
            try {
                t = klass.newInstance();
                t.options = this.option;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return t;
        }
    }
}
