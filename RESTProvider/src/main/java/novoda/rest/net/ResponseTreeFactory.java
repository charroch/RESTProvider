package novoda.rest.net;

import java.io.InputStream;

import android.net.Uri;

public interface ResponseTreeFactory {
    public ResponseTree parse(InputStream in, Uri uri);
}

class JsonTreeFactory implements ResponseTreeFactory {

    @Override
    public ResponseTree parse(InputStream in, Uri uri) {
        return null;
    }
}