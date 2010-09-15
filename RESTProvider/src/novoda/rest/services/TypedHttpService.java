package novoda.rest.services;

import java.io.IOException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import novoda.mixml.XMLNode;
import novoda.rest.exception.ParserException;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.json.JsonNodeObject;
import novoda.rest.parsers.xml.XmlNodeObject;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;

public abstract class TypedHttpService extends HttpService {

	public static final String CONTEXT_TYPE = "novode.rest.type";

	/*
	 * We can have several content type for XML. For instance application/xml or
	 * text/xml or text/rss+xml etc... as such we only check for XML prefix
	 */
	public static final String CONTENT_TYPE_XML_SUFFIX = "xml";

	public static final String CONTENT_TYPE_JSON = "application/json";

	public static final int JSON = 0;

	public static final int XML = 1;

	private int type = -1;

	@Override
	protected void onHandleResponse(HttpResponse response, HttpContext context) {
		// get the type from configuration if any
		type = (Integer) context.getAttribute(CONTEXT_TYPE);
		if (type == -1) {
			final String contentType = response.getEntity().getContentType()
					.getValue();
			if (CONTENT_TYPE_JSON.equals(contentType)) {
				type = JSON;
			} else if (contentType.endsWith(CONTENT_TYPE_XML_SUFFIX)) {
				type = XML;
			}
		}
		Node<?> node = null;
		switch (type) {
		case XML:
			XMLNode xml = new XMLNode();
			try {
				xml.parse(response.getEntity().getContent());
				node = new XmlNodeObject(xml);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (FactoryConfigurationError e) {
				e.printStackTrace();
			}
			break;
		case JSON:
			try {
				JsonNode json = new ObjectMapper().readTree(response.getEntity().getContent());
				node = new JsonNodeObject(json);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			default:
				throw new ParserException("No type defined in configuration nor can it be found from content-type");
		}
		onHandleResponse(node, context);
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	protected abstract <T> void onHandleResponse(Node<T> node,
			HttpContext context);
}
