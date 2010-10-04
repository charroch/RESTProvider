package novoda.rest.command;

import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.database.Persister;
import novoda.rest.parsers.Node;

public abstract class PersistableCommand implements Command, Persister {

	protected Node<?> data;

	protected ModularSQLiteOpenHelper sqlite;

	@Override
	public void setPersister(ModularSQLiteOpenHelper sqlite) {
		this.sqlite = sqlite;
	}

	@Override
	public void setData(Node<?> data) {
		this.data = data;
	}

	@Override
	public void execute() {
		// TODO locking?
		traverse(data, sqlite);
	}

	protected abstract void traverse(Node<?> response,
			ModularSQLiteOpenHelper dbHelper);
}
