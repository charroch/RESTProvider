package novoda.rest.database;

public interface CachingStrategy {
    public static int REPLACE = 0;
    public static int APPEND = 1;
    public static int TRANSIENT = 2;
    public int onNewResults();
}
