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

import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.parsers.Node;
import novoda.rest.parsers.NodeParser;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.net.Uri;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface UriRequestMap.
 */
public interface UriRequestMap {

    /**
     * The Constant QUERY which correspond to a a query call made against the
     * content resolver for a URI that is answered by this content provider.
     */
    static final public int QUERY = 0;

    /** The Constant INSERT. */
    static final public int INSERT = 1;

    /** The Constant UPDATE. */
    static final public int UPDATE = 2;

    /** The Constant DELETE. */
    static final public int DELETE = 3;

    /**
     * Gets the request.
     * 
     * @param uri the uri
     * @param type the type
     * @param params the params
     * @return the request
     */
    public abstract HttpUriRequest getRequest(Uri uri, int type, List<NameValuePair> params);

    /**
     * Gets the query params.
     * 
     * @param uri the uri
     * @param projection the projection
     * @param selection the selection
     * @param selectionArg the selection arg
     * @param sortOrder the sort order
     * @return the query params
     */
    public abstract List<NameValuePair> getQueryParams(Uri uri, String[] projection,
            String selection, String[] selectionArg, String sortOrder);

    public abstract List<NameValuePair> getUpdateParams(Uri uri, ContentValues values,
            String selection, String[] selectionArg);

    public abstract List<NameValuePair> getInsertParams(Uri uri, ContentValues values);

    public abstract List<NameValuePair> getDeleteParams(Uri uri, String selection,
            String[] selectionArg);

    public abstract SQLiteTableCreator getTableCreator(Uri uri);

    public abstract <T> NodeParser<? extends Node<T>> getParser(Uri uri);
}
