package novoda.rest.system;

import org.xmlpull.v1.XmlPullParserException;

import android.content.pm.PackageManager.NameNotFoundException;

/*
 * Runtime exception that might happen upon class loading.
 */
public class LoaderException extends RuntimeException {

    public static final int INSTANTIATION_EXCEPTION = 0;

    public static final int ILLEGAL_ACCESS_EXCEPTION = 1;

    public static final int CLASS_NOT_FOUND_EXCEPTION = 2;

    public static final int NAME_NOT_FOUND_EXCEPTION = 3;

    public static final int XML_FAILURE = 4;

    private static final long serialVersionUID = 1092262456874490603L;

    private int type;

    public LoaderException(Exception e) {
        this(e.getMessage(), e);
    }

    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(String message, Exception e) {
        super(message, e);
        if (e instanceof InstantiationException) {
            this.setType(INSTANTIATION_EXCEPTION);
        } else if (e instanceof IllegalAccessException) {
            this.setType(ILLEGAL_ACCESS_EXCEPTION);
        } else if (e instanceof ClassNotFoundException) {
            this.setType(CLASS_NOT_FOUND_EXCEPTION);
        } else if (e instanceof NameNotFoundException) {
            this.setType(NAME_NOT_FOUND_EXCEPTION);
        } else if (e instanceof XmlPullParserException) {
            this.setType(XML_FAILURE);
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String getMessage() {
        switch (type) {
            case NAME_NOT_FOUND_EXCEPTION:
                return "Name not found, have you declared it in the manifest?";
        }
        return super.getMessage();
    }

}