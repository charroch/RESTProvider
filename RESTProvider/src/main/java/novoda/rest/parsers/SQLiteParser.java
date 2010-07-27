
package novoda.rest.parsers;

public interface SQLiteParser {
    // NULL, INTEGER, REAL, TEXT, BLOB

    public abstract byte[] getByteValue();

    public abstract int getIntValue();

    public abstract long getLongValue();

    public abstract boolean getBoolean();

    public abstract String getString();

    public abstract short getShortValue();

    public abstract float getFloatValue();
}
