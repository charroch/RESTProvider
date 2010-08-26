package novoda.rest.uri;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

/**
 * Simple class which maps Android URIs to any type of objects.
 * 
 */
public class UriMapper<T> {

	// This hold 10 character (i.e. content://)
	private static final int CONTENT_LENGHT = 10;

	private SparseArray<T> uriToUrl = new SparseArray<T>();

	private UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	private int code = 0;

	/*
	 * Ability to hook into the response object before it is returned
	 */
	private Transformer<T> transformer = null;

	public void map(Uri uri, T url) {
		matcher.addURI(uri.getAuthority(), sanifyPath(uri), code);
		uriToUrl.append(code, url);
		code += 1;
	}

	/*
	 * Get rid of the last '/' if any
	 */
	private String sanifyPath(Uri uri) {
		String path = uri.toString().substring(
				uri.getAuthority().length() + CONTENT_LENGHT);
		// removing trailing /
		if (path.charAt(0) == '/') {
			path = path.substring(1);
		}
		return path;
	}

	public T get(Uri uri) {
		return get(uri, null, true);
	}

	public T get(Uri uri, T defaultValue) {
		return get(uri, defaultValue, false);
	}

	private T get(Uri uri, T defaultValue, boolean shouldThrow) {
		// replace # and * with an id so the match passes
		uri = Uri.parse(uri.toString().replace("#", "99"));
		uri = Uri.parse(uri.toString().replace("*", "99"));

		final int match = matcher.match(uri);
		if (match == UriMatcher.NO_MATCH) {
			if (!shouldThrow)
				return defaultValue;
			else
				throw new NoMatchException("no match found for uri: " + uri);
		}
		if (transformer != null) {
			return transformer.onResponse(uri, uriToUrl.get(match));
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
		transformer = null;
	}

	public interface Transformer<T> {
		T onResponse(Uri uri, T object);
	}

	public void setTransformer(Transformer<T> transformer) {
		this.transformer = transformer;
	}
}
