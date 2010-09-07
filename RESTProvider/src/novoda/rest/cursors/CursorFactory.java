package novoda.rest.cursors;

public interface CursorFactory<T extends ResponseCursor> {
    public T create();
}
