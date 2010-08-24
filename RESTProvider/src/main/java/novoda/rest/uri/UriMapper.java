
package novoda.rest.uri;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

public class UriMapper<T> {

    // This hold 10 character (i.e. content://)
    private static final int CONTENT_LENGHT = 10;

    private SparseArray<T> uriToUrl = new SparseArray<T>();

    private UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    private int code = 0;

    public void map(Uri uri, T url) {
        matcher.addURI(uri.getAuthority(), sanifyPath(uri), code);
        uriToUrl.append(code, url);
        code += 1;
    }

    /*
     * Get rid of the last '/' if any
     */
    private String sanifyPath(Uri uri) {
        String path = uri.toString().substring(uri.getAuthority().length() + CONTENT_LENGHT);
        // removing trailing /
        if (path.charAt(0) == '/') {
            path = path.substring(1);
        }
        return path;
    }

    public T getUrl(Uri uri) {
        // replace # and * with an id so the match passes
        uri = Uri.parse(uri.toString().replace("#", "99"));
        uri = Uri.parse(uri.toString().replace("*", "99"));
        
        final int match = matcher.match(uri);
        if (match == UriMatcher.NO_MATCH) {
            throw new NoMatchException("no match found for uri: " + uri);
        }
        return uriToUrl.get(match);
    }

    public static class NoMatchException extends RuntimeException {
        public NoMatchException(String string) {
            super(string);
        }

        private static final long serialVersionUID = 1705583377032327225L;
    }

    /* package */void clear() {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriToUrl.clear();
        code = 0;
    }
}
