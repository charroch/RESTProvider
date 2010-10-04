package novoda.rest.context.command;


public interface Command<T> {
	public void execute(T data);
}