
package novoda.rest.utils;

/**
 * A minimal event based XPath parser which takes a generic document node and
 * push events against its parsing to return the correct node within the tree.
 * Could probably use Regex here.
 * 
 * @author Carl-Gustaf Harroch
 * @param <T> The response node to process
 */
public abstract class MiXPath<T> {

    char bracket_o = '[';

    char bracket_c = ']';

    char separator = '/';

    public MiXPath() {
        super();
    }

    public MiXPath(char separator) {
        this.separator = separator;
    }

    public abstract T eventArray(T data, int index);

    public abstract T eventPath(T data, String path);

    public T parse(T data, String xpath) {
        // No need to parse
        if (xpath == null || xpath.length() == 0) {
            return data;
        }
        int start = 0;
        int end = 0;
        int start_b = 0;
        for (char c : xpath.toCharArray()) {
            end++;
            if (c == separator) {
                String s = xpath.substring(start, end - 1);
                start = end;
                data = eventPath(data, s);
            } else if (c == bracket_o) {
                start_b = end;
            } else if (c == bracket_c) {
                try {
                    int index = Integer.parseInt(xpath.substring(start_b, end-1));
                    data = eventArray(data, index);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("can't parse values" + e);
                }
            }
        }
        return data;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public char getSeparator() {
        return separator;
    }
}
