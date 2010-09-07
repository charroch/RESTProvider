package novoda.rest.configuration;

import android.content.res.XmlResourceParser;

import novoda.rest.database.SQLiteConflictClause;
import novoda.rest.database.SQLiteTableCreator;
import novoda.rest.database.SQLiteType;

public class XmlSQLiteTableCreator implements SQLiteTableCreator {

    public XmlSQLiteTableCreator(XmlResourceParser xpp) {
    }

    @Override
    public String getParentColumnName() {
        return null;
    }

    @Override
    public String getParentPrimaryKey() {
        return null;
    }

    @Override
    public String getParentTableName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLiteType getParentType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPrimaryKey() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getTableFields() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTableName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getTriggers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLiteType getType(String field) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isNullAllowed(String field) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isOneToMany() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isUnique(String field) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public SQLiteConflictClause onConflict(String field) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean shouldIndex(String field) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean shouldPKAutoIncrement() {
        // TODO Auto-generated method stub
        return false;
    }

}
