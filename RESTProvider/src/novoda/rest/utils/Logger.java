
package novoda.rest.utils;

import android.content.Context;
import android.util.Log;

public class Logger {

    // TODO find a way to load this values from properties files
    private static final boolean IS_DEBUG_ENABLED = true;

    private static final boolean IS_INFO_ENABLED = true;

    private static final boolean IS_WARNING_ENABLED = true;

    private static final boolean IS_ERROR_ENABLED = true;

    private volatile static Logger logger;

    private String tag;

    public static Logger getLogger(final Context context) {
        if (logger == null) {
            synchronized (Logger.class) {
                if (logger == null)
                    logger = new Logger(context);
            }
        }
        return logger;
    }

    private Logger(final Context context) {
    }

    private Logger(@SuppressWarnings("rawtypes") Class clazz) {
        this.tag = clazz.getSimpleName();
    }

    private Logger(String tag) {
        this.tag = tag;
    }

    public final static Logger getLogger(String tag) {
        return new Logger(tag);
    }

    public final static Logger getLogger(@SuppressWarnings("rawtypes") Class clazz) {
        return new Logger(clazz);
    }

    public final void warn(String message) {
        Log.w(tag, message);
    }

    public final void warn(String message, Throwable t) {
        Log.w(tag, message);
        if (t != null) {
            Log.w(tag, t.getMessage() + "");
        }
    }

    public final void debug(String message) {
        Log.d(tag, message);
    }

    public final void debug(String message, Throwable t) {
        Log.d(tag, message);
        if (t != null) {
            Log.d(tag, t.getMessage() + "");
        }
    }

    public final void info(String message) {
        Log.i(tag, message);
    }

    public final void info(String message, Throwable t) {
        Log.i(tag, message);
        if (t != null) {
            Log.i(tag, t.getMessage() + "");
        }
    }

    public final void error(String message) {
        Log.e(tag, message);
    }

    public final void error(String message, Throwable t) {
        Log.e(tag, message);
        t.printStackTrace();
    }

    public final void error(Throwable t) {
        t.printStackTrace();
    }

    public static final boolean isDebugEnabled() {
        return IS_DEBUG_ENABLED;
    }

    public static final boolean isInfoEnabled() {
        return IS_INFO_ENABLED;
    }

    public static final boolean isErrorEnabled() {
        return IS_ERROR_ENABLED;
    }

    public static final boolean isWarnEnabled() {
        return IS_WARNING_ENABLED;
    }
}
