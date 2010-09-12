package novoda.rest.providers;

import novoda.rest.intents.HttpServiceIntent;
import android.net.Uri;

public interface ContentProviderDelegate {
	
	public HttpServiceIntent query(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder);

	public HttpServiceIntent init();
	
}
