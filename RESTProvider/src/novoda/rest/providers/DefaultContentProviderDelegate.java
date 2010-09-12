package novoda.rest.providers;

import novoda.rest.intents.HttpServiceIntent;
import novoda.rest.system.IOCLoader;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DefaultContentProviderDelegate implements ContentProviderDelegate {

	private IOCLoader config;

	public DefaultContentProviderDelegate(Context context) {
		config = IOCLoader.getInstance(context);
	}

	@Override
	public HttpServiceIntent query(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		final Uri d = uri;
		HttpServiceIntent intent = new HttpServiceIntent() {

			@Override
			public Uri getHttpUri() {
				return Uri.parse(d.getEncodedFragment());
			}

			@Override
			public String getMethod() {
				return "GET";
			}

			@Override
			public Intent getIntent() {
				return new Intent();
			}
			
		};
		return intent;
	}

	@Override
	public HttpServiceIntent init() {
		return null;
	}
}
