package novoda.rest.context.command;

import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.database.Persister;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.Node.ParsingOptions;

public abstract class QueryCommand<T> implements Command<Node<T>>, Persister {

	private ModularSQLiteOpenHelper sqlite;

	private ParsingOptions options;

	public void applyOptions(ParsingOptions options) {
		this.options = options;
	}

	@Override
	public void execute(Node<T> data) {
		if (options != null) {
			data.applyOptions(options);
		}
		parse(data);
	}

	@Override
	public void setPersister(ModularSQLiteOpenHelper sqlite) {
		this.sqlite = sqlite;
	}

	protected synchronized ModularSQLiteOpenHelper getDB() {
		return sqlite;
	}

	public abstract void parse(Node<T> data);
}
