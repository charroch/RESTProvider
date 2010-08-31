/*
 * Copyright (C) 2010 Novoda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package novoda.rest;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.net.Uri;

/**
 * Method needed to do a HTTP call of any sort. This applies for
 * Query/Delete/Update/Insert queries. This interface does not define the
 * parsing mechanism but rather the calls themselves. For parsing, we will have
 * another interface for separation of concern.
 */
public interface UriRequestMap {

	static final public int QUERY = 0;

	static final public int INSERT = 1;

	static final public int UPDATE = 2;

	static final public int DELETE = 3;

	public abstract HttpUriRequest getRequest(Uri uri, int type,
			List<NameValuePair> params);

	public abstract List<NameValuePair> getQueryParams(Uri uri,
			String[] projection, String selection, String[] selectionArg,
			String sortOrder);

	public abstract List<NameValuePair> getUpdateParams(Uri uri,
			ContentValues values, String selection, String[] selectionArg);

	public abstract List<NameValuePair> getInsertParams(Uri uri,
			ContentValues values);

	public abstract List<NameValuePair> getDeleteParams(Uri uri,
			String selection, String[] selectionArg);
}
