package novoda.rest.parsers;

public abstract class SQliteFactory {
    /*
     * root name
     * node name
     * mapper list
     */
    public abstract SQLiteParser createSQLiteParser(java.io.Reader r); 
}
