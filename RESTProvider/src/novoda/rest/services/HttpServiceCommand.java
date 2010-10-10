package novoda.rest.services;

import novoda.rest.command.Command;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.database.Persister;
import novoda.rest.exception.ParserException;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.json.JsonNodeParser;
import novoda.rest.system.IOCLoader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.protocol.HttpContext;

import android.content.Intent;

import java.io.IOException;

public abstract class HttpServiceCommand extends HttpService {

	private static final int JSON = 0;

	@Override
	protected void onHandleResponse(HttpResponse response, HttpContext context) {
		Command command = getCommand(getIntent());
		command.setData(getData(response, context));
		if (command instanceof Persister) {
			ModularSQLiteOpenHelper db = new ModularSQLiteOpenHelper(
					getApplicationContext());
			((Persister) command).setPersister(db);
		}
		command.execute();
	}

	private Node<?> getData(HttpResponse response, HttpContext context) {
		int type = IOCLoader.getInstance(getApplicationContext()).getFormat();
		switch (type) {
		case JSON:
			return getJsonNode(response, context);
		}
		throw new IllegalArgumentException("can not find format");
	}

	private Node<?> getJsonNode(HttpResponse response, HttpContext context) {
		JsonNodeParser parser = new JsonNodeParser();
		try {
			return parser.handleResponse(response);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		throw new ParserException("can not parse stream");
	}

	protected abstract Command getCommand(Intent intent);
}
