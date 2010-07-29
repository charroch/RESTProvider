
package novoda.rest.utils;

public abstract class MiXPath<T> {
    
    char bracket_o = '[';

    char bracket_c = ']';

    char separator = '/';

    public abstract T eventArray(T data, int index);

    public abstract T eventPath(T data, String path);
    
    public void parse(String parser) {
    }
}
