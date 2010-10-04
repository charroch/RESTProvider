package novoda.rest.services;

import java.io.IOException;

import novoda.rest.parsers.Node;

import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public abstract class JsonHttpService extends TypedHttpService<JsonNode> {

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	protected Node<JsonNode> getNode(HttpResponse response) {
		try {
			mapper.readTree(response.getEntity().getContent());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.getEntity().consumeContent();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
