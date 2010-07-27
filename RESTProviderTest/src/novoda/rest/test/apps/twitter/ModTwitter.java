
package novoda.rest.test.apps.twitter;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;

import novoda.rest.database.SQLiteConflictClause;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteType;
import novoda.rest.providers.ModularProvider;
import novoda.rest.services.RESTCallService;
import novoda.rest.test.service.TwitterService;

public class ModTwitter extends ModularProvider {

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
                if (field.equals("id"))
                    return true;
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
                return null;
            }
        };
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

}
