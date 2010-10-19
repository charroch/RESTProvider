package novoda.rest.command;

import android.content.ContentProviderClient;
import android.util.Log;
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
		try {
			traverse(data, sqlite);
		} finally {
			// sqlite.close();
		}
	}

	protected abstract void traverse(Node<?> response,
			ModularSQLiteOpenHelper client);
}
