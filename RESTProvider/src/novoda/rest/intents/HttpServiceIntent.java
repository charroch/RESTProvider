package novoda.rest.intents;

import android.content.Intent;
import android.net.Uri;

public interface HttpServiceIntent {
    public Uri getHttpUri();
    public String getMethod();
    
    // change intent what intent
    // TODO
    public Intent getIntent();
}
