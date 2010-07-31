
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

            public boolean shouldPKAutoIncrement() {
                return false;
            }

            public boolean shouldIndex(String field) {
                return false;
            }

            public SQLiteConflictClause onConflict(String field) {
                return SQLiteConflictClause.IGNORE;
            }

            public boolean isUnique(String field) {
                return false;
            }

            public boolean isOneToMany() {
                return false;
            }

            public boolean isNullAllowed(String field) {
                return true;
            }

            public SQLiteType getType(String field) {
                return SQLiteType.TEXT;
            }

            public String getTableName() {
                return "feed";
            }

            public String[] getTableFields() {
                return new String[] {
                        "profile_image_url", "created_at", "from_user", "to_user_id", "text", "id",
                        "from_user_id", "geo", "iso_language_code", "source"
                };
            }

            public String getPrimaryKey() {
                return "id";
            }

            public String getParentColumnName() {
                // TODO Auto-generated method stub
                return null;
            }

            public SQLiteType getParentType() {
                // TODO Auto-generated method stub
                return null;
            }

            public String[] getTriggers() {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

}
