package novoda.rest.net;

import android.database.AbstractCursor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import novoda.rest.cursors.json.JsonCursor;

public class JsonMarshaller extends ResponseMarshaller<TestCursor> {

    @Override
    public TestCursor getChildCursor(long parentId, String segment) {
        return null;
    }

    @Override
    public List<String> getChildUriSegment() {
        return null;
    }

    @Override
    public TestCursor getCursor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void parse(InputStream response) throws IOException {
        // TODO Auto-generated method stub
        
    }

}
