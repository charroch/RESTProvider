
package novoda.rest.net;

public class RequestExecutionException extends Throwable {

    private static final long serialVersionUID = -6805126414398671526L;

    public enum Type {
        NOT_MODIFIED, NULL_RESPONSE, UNKNOWN_STATUS_CODE
    }

    private Type type;

    private Object obj;

    public Type getType() {
        return type;
    }

    public Object getObject() {
        return obj;
    }

    public RequestExecutionException(Type type) {
        this.type = type;
    }

    public RequestExecutionException(Type type, Object obj) {
        this.type = type;
        this.obj = obj;
    }

}
