package novoda.rest.providers;

import novoda.rest.intents.HttpServiceIntent;
import novoda.rest.system.IOCLoader;
import android.net.Uri;

public class DefaultContentProviderDelegate implements ContentProviderDelegate {

	public DefaultContentProviderDelegate(IOCLoader loader) {
		loader.getMetaData();
	}

	@Override
	public HttpServiceIntent query(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public HttpServiceIntent init() {
		return null;
	}
}
