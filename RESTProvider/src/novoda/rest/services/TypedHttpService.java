package novoda.rest.services;

import novoda.rest.parsers.Node;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

public abstract class TypedHttpService<T> extends HttpService {

	public static final String CONTEXT_TYPE = "novode.rest.type";

	/*
	 * We can have several content type for XML. For instance application/xml or
	 * text/xml or text/rss+xml etc... as such we only check for XML prefix
	 */
	public static final String CONTENT_TYPE_XML_SUFFIX = "xml";

	public static final String CONTENT_TYPE_JSON = "application/json";

	public static final int JSON = 0;

	public static final int XML = 1;

	@Override
	protected void onHandleResponse(HttpResponse response, HttpContext context) {

		// get the type from configuration if any
		Integer type = (Integer) context.getAttribute(CONTEXT_TYPE);

		if (type == null) {
			final String contentType = response.getEntity().getContentType()
					.getValue();
			if (CONTENT_TYPE_JSON.equals(contentType)) {
				type = JSON;
			} else if (contentType.contains(CONTENT_TYPE_XML_SUFFIX)) {
				type = XML;
			}
		}
		Node<T> node = getNode(response);
		onHandleResponse(node, context);
	}

	protected abstract Node<T> getNode(HttpResponse response);

	protected abstract void onHandleResponse(Node<T> node, HttpContext context);
}