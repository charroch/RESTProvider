
package novoda.rest.providers;

import novoda.rest.cursors.EmptyCursor;
import novoda.rest.database.ModularSQLiteOpenHelper;
import novoda.rest.database.SQLiteInserter;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.UriQueryBuilder;
import novoda.rest.services.RESTCallService;
import novoda.rest.utils.DatabaseUtils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class ModularProvider extends ContentProvider {

    private static final String TAG = ModularProvider.class.getSimpleName();

    protected ModularSQLiteOpenHelper dbHelper;

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long ret = -1;
        try {

            ret = dbHelper.getWritableDatabase()
                    .insertOrThrow(uri.getLastPathSegment(), "", values);

        } catch (SQLiteException e) {
            if (e.getMessage().contains("no such table")) {
                // FIXME potential stack overflow
                Log.v(TAG, "creating table: " + uri.getLastPathSegment());

                DatabaseUtils.InsertHelper i = new InsertHelper(dbHelper.getWritableDatabase(),
                        "test");

                dbHelper.getWritableDatabase().execSQL(
                        DatabaseUtils.contentValuestoTableCreate(values, getTableCreator(uri)
                                .getTableName()));
                return insert(uri, values);
            }
        }
        if (ret != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
            return uri.buildUpon().appendEncodedPath("" + ret).build();
        }
        throw new SQLiteException("Can not insert");
    }

    @Override
    public boolean onCreate() {
        dbHelper = (ModularSQLiteOpenHelper) getSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {

        SQLiteQueryBuilder qBuilder = UriQueryBuilder.fromUri(uri);
        
        // Make the query.
        Cursor c = null;
        try {
            c = qBuilder.query(dbHelper.getReadableDatabase(), projection, selection,
                    selectionArgs, null, null, sortOrder);
        } catch (SQLiteException e) {
            Log.i(TAG, "no table created yet for uri " + uri.toString());
            if (e.getMessage().contains("no such table")) {
                c = new EmptyCursor();
            }
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        String query = uri.getQueryParameter("query");
        if (true) {
            Intent intent = new Intent(getContext(), getService().getClass());
            intent.setAction(RESTCallService.ACTION_QUERY);
            intent.setData(uri);
            intent.putExtra(RESTCallService.BUNDLE_PROJECTION, projection);
            intent.putExtra(RESTCallService.BUNDLE_SELECTION, selection);
            intent.putExtra(RESTCallService.BUNDLE_SELECTION_ARG, selectionArgs);
            intent.putExtra(RESTCallService.BUNDLE_SORT_ORDER, sortOrder);
            getContext().startService(intent);
        }
        return c;
    }

    protected abstract RESTCallService getService();

    //TODO ??
    protected abstract SQLiteTableCreator getTableCreator(final Uri uri);

    protected SQLiteOpenHelper getSQLiteOpenHelper(Context context) {
        return new ModularSQLiteOpenHelper(context);
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        return 0;
    }

    public void create(SQLiteTableCreator creator) {
        dbHelper.createTable(creator);
    }

    public void create(Uri creator) {
        SQLiteStatement statement = dbHelper.getWritableDatabase().compileStatement(
                DatabaseUtils.getCreateStatement(getTableCreator(creator)));
        statement.execute();
        statement.close();
    }

    /**
     * @param inserter
     * @return the list of
     */
    public Long[] insert(SQLiteInserter inserter, Uri uri) {

        // Create DB:
        dbHelper.createTable(getTableCreator(uri));

        final String sql = inserter.getInsertStatement(getTableCreator(uri).getTableName());

        final int count = inserter.getCount();

        final String[] columns = inserter.getColumns();
        final List<Long> ret = new ArrayList<Long>(count);

        SQLiteStatement statement = dbHelper.getWritableDatabase().compileStatement(sql);

        // TODO move to TransactionListner (v2 of android)
        // dbHelper.getWritableDatabase().beginTransaction();
        for (int i = 0; i < count; i++) {
            statement.clearBindings();

            /*
             * Bind values to the SQL statement
             */
            Object value;
            for (String column : columns) {
                value = inserter.get(column, i);
                if (value == null) {
                    value = new String("");
                }
                int index = inserter.getInsertIndex(column) + 1;
                switch (inserter.getType(column)) {
                    case BLOB:
                        statement.bindBlob(index, getBytes(value));
                        break;
                    case INTEGER:
                        int integer = (value instanceof String) ? Integer.valueOf((String) value)
                                : ((Number) value).intValue();
                        statement.bindLong(index, integer);
                        break;
                    case REAL:
                        double d = (value instanceof String) ? Double.valueOf((String) value)
                                : ((Number) value).doubleValue();
                        statement.bindDouble(index, d);
                        break;
                    case NULL:
                        statement.bindNull(index);
                        break;
                    case TEXT:
                        statement.bindString(index, value.toString());
                        break;
                }
            }
            long id = statement.executeInsert();
            if (id == -1) {
                if (inserter.onFailure(i) == SQLiteInserter.CONTINUE) {
                    continue;
                } else if (inserter.onFailure(i) == SQLiteInserter.BREAK) {
                    break;
                } else if (inserter.onFailure(i) == SQLiteInserter.ROLLBACK) {
                    break;
                }
            } else {
                ret.add(id);
            }
        }
        return ret.toArray(new Long[] {});
    }

    /**
     * Static method which converts an Object to a stream. This is useful when
     * inserting a BLOB into the database.
     * 
     * @param obj, the object to be converted to byte array
     * @return the byte stream of the object
     */
    public static byte[] getBytes(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "".getBytes();
    }

    public long insert(String tableName, ContentValues values) {
        return dbHelper.getWritableDatabase().insert(tableName, "", values);
    }
    
    public SyncStrategy getSyncStrategy(Uri uri){
        return null;};
}
