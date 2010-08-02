
package novoda.rest.test.apps.twitter;

import novoda.rest.RESTProvider;
import novoda.rest.cursors.json.JsonCursor;
import novoda.rest.database.SQLiteConflictClause;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteType;
import novoda.rest.providers.ModularProvider;
import novoda.rest.services.RESTCallService;
import novoda.rest.test.service.TwitterService;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.database.AbstractCursor;
import android.net.Uri;

public class TwitterFeedExampleProvider extends ModularProvider {

    private static final String TAG = TwitterFeedExampleProvider.class.getSimpleName();

    @Override
    protected RESTCallService getService() {
        return new TwitterService();
    }

    @Override
    protected SQLiteTableCreator getTableCreator(Uri uri) {
        return new SQLiteTableCreator() {
            public String getParentColumnName() {
                return null;
            }

            public SQLiteType getParentType() {
                return null;
            }

            public String getPrimaryKey() {
                return null;
            }

            public String[] getTableFields() {
                return new String[] {
                        "track_likes", "set_date", "show_artist", "show_id", "show_title", "id",
                        "set_link", "track_plays", "title", "duration", "set_likes", "small_image",
                        "file", "description", "likes", "medium_image", "show_alias"
                };
            }

            public String getTableName() {
                return "sets";
            }

            public String[] getTriggers() {
                return null;
            }

            public SQLiteType getType(String field) {
                return SQLiteType.TEXT;
            }

            public boolean isNullAllowed(String field) {
                return true;
            }

            public boolean isOneToMany() {
                return false;
            }

            public boolean isUnique(String field) {
                return false;
            }

            public SQLiteConflictClause onConflict(String field) {
                return SQLiteConflictClause.IGNORE;
            }

            public boolean shouldIndex(String field) {
                return false;
            }

            public boolean shouldPKAutoIncrement() {
                return true;
            }
        };
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

}
