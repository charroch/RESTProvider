package novoda.rest.command;

import novoda.rest.parsers.Node;

public interface Command {
	public void setData(Node<?> data);

	public void execute();
}
