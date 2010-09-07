package novoda.rest.intents;

import android.content.Intent;
import android.net.Uri;

public interface HttpServiceIntent {
    public Uri getHttpUri();
    public int getMethod();
    public Intent getIntent();
}
