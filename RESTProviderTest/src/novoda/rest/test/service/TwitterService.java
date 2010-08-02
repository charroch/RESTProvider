
package novoda.rest.test.service;

import novoda.rest.database.SQLiteConflictClause;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteType;
import novoda.rest.parsers.NodeParser;
import novoda.rest.parsers.Node.Options;
import novoda.rest.parsers.json.JsonNodeParser;
import novoda.rest.services.RESTCallService;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.ContentValues;
import android.net.Uri;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// FIXME move this to the content provider
public class TwitterService extends RESTCallService {

    public TwitterService() {
        super();
    }

    public TwitterService(String name) {
        super(name);
    }

    public List<NameValuePair> getDeleteParams(Uri uri, String selection, String[] selectionArg) {
        return null;
    }

    public List<NameValuePair> getInsertParams(Uri uri, ContentValues values) {
        return null;
    }

    public NodeParser<?> getParser(Uri uri) {
        Options o = new Options();
        o.table = "tracks";
        o.insertUri = Uri.parse("content://test");
        Map<String, Options> map = new HashMap<String, Options>();
        map.put("tracks", o);
        return new JsonNodeParser.Builder().withRootNode("sets").withTableName("sets")
                .withChildren(map).build(JsonNodeParser.class);
    }

    public List<NameValuePair> getQueryParams(Uri uri, String[] projection, String selection,
            String[] selectionArg, String sortOrder) {
        return null;
    }

    public HttpUriRequest getRequest(Uri uri, int type, List<NameValuePair> params) {
        return new HttpGet("http://mugasha.com/api/private/sets?extended=yes&filter=newest");
    }

    public SQLiteTableCreator getTableCreator(Uri uri) {
        if (uri.compareTo(Uri.parse("content://test")) == 0) {
            return new SQLiteTableCreator(){
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
                    return null;
                }

                public String getTableName() {
                    return null;
                }

                public String[] getTriggers() {
                    return null;
                }

                public SQLiteType getType(String field) {
                    return null;
                }

                public boolean isNullAllowed(String field) {
                    return false;
                }

                public boolean isOneToMany() {
                    return false;
                }

                public boolean isUnique(String field) {
                    return false;
                }

                public SQLiteConflictClause onConflict(String field) {
                    return null;
                }

                public boolean shouldIndex(String field) {
                    return false;
                }

                public boolean shouldPKAutoIncrement() {
                    return false;
                }
            };
        }
        
        return new SQLiteTableCreator (){
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
                return new String[] {"track_likes", "set_date", "show_artist", "show_id", 
                        "show_title", "id", "set_link", "track_plays", "title", "duration", 
                        "set_likes", "small_image", "file", "description","likes", "medium_image", "show_alias"
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

    public List<NameValuePair> getUpdateParams(Uri uri, ContentValues values, String selection,
            String[] selectionArg) {
        return null;
    }

}
