
package novoda.rest.net;


public interface ETag {
    static final String IF_NONE_MATCH = "If-None-Match";

    static final String IF_MODIFIED_SINCE = "If-Modified-Since";

    static final String ETAG = "ETag";

    static final String LAST_MODIFIED = "Last-Modified";

    String getEntityTag(String request);

    String getLastModified(String request);

    void save(String getRequest, String lastModified, String etag);
}
