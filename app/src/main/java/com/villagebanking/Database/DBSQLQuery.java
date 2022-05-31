package com.villagebanking.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;

public class DBSQLQuery extends SQLiteOpenHelper {

    //region COMMON CREATE UPGRADE
    public static final String DATABASE_NAME = "MyDBName.db";

    public DBSQLQuery(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String tableCreateQuery : DB1Tables.getCreateTables()) {
            db.execSQL("CREATE TABLE " + tableCreateQuery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String tableName : DB1Tables.getTables()) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }
        onCreate(db);
    }
    //endregion

    //region INSERT UPDATE DELETE
    public Integer DBdelete(long key, String TBLNAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TBLNAME,
                "id = ? ",
                new String[]{Long.toString(key)});
    }

    public boolean DBSaveUpdate(Long key, ContentValues data, String TBLNAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (key > 0)
            db.update(TBLNAME, data, "id = ? ",
                    new String[]{Long.toString(key)});
        else
            db.insert(TBLNAME, null, data);
        return true;
    }

    public void updateField(String TBLNAME, String CLMNAME, String VALUE, long ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TBLNAME + " SET " + CLMNAME + "='" + VALUE + "' WHERE ID=" + ID);

    }

    public boolean DBSaveUpdate(String flag,Long key, ContentValues data, String TBLNAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (flag=="U")
            db.update(TBLNAME, data, "id = ? ",
                    new String[]{Long.toString(key)});
        else
            db.insert(TBLNAME, null, data);
        return true;
    }
    //endregion

    //region OTHER GET LIST
    /*
        GROUPS => "ID INTEGER PRIMARY KEY,NAME TEXT,NO_OF_PERSON INTEGER,AMOUNT DECIMAL,START_PERIOD_KEY INTEGER,
                   BOND_CHARGE DECIMAL"

        GROUP_PERSON_LINK =>"ID INTEGER PRIMARY KEY,GROUP_KEY INTEGER,PERSON_KEY INTEGER,ORDER_BY INTEGER,
                    PERSON_ROLE TEXT"

        PERSON_TRANSACTION =>ID INTEGER PRIMARY KEY,PARENT_KEY INTEGER,PERIOD_KEY INTEGER,TABLE_NAME TEXT,
                            TABLE_LINK_KEY INTEGER,REMARKS TEXT,AMOUNT DECIMAL"

        PERIODS => ID INTEGER PRIMARY KEY,PERIOD_TYPE INTEGER,PERIOD_NAME TEXT,ACTUAL_DATE TEXT,
                   DATE_VALUE INTEGER,PERIOD_REMARKS TEXT" +
     */
    public Cursor DBGetAll(String TBLNAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TBLNAME, null);
        return res;
    }

    public Cursor DBGetData(String TBLNAME, long primary_key) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TBLNAME + " WHERE ID=" + primary_key, null);
        return res;
    }

    public Cursor DBGetDataFilter(String TBLNAME, String CLMNAME, String INPUT) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TBLNAME + " WHERE " + CLMNAME + "=" + INPUT, null);
        return res;
    }

    public Cursor DBFetchGroupLink(String periodkeys) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = tblTransHeader.getQuery(db, periodkeys);
        return res;
    }

    public Cursor DBGetTransDetail(String parentKeys, String periodKeys, String childKeys, String linkKeys) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = tblTransDetail.DBGetTransDetail(db, parentKeys, periodKeys, childKeys, linkKeys);
        return res;
    }
    //endregion
}