package com.villagebanking.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.DBTables.tblUtility;

import java.util.ArrayList;

public class DBSQLQuery extends SQLiteOpenHelper {

    private static final String SUCCESS = "Success!";

    //region UPGRADE
    public static final String DATABASE_NAME = "MyDBName.db";

    public DBSQLQuery(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String tableCreateQuery : tblUtility.getCreateTables()) {
            db.execSQL("CREATE TABLE " + tableCreateQuery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String tableName : tblUtility.getTables()) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }
        onCreate(db);
    }
    //endregion

    //region Get DB Details
    public SQLiteDatabase GetWritableDB() {
        return this.getWritableDatabase();
    }

    public SQLiteDatabase GetReadableDB() {
        return this.getReadableDatabase();
    }
    //endregion

    //region Basic Select and Delete
    public Cursor DBGetData(String tblName, long primary_key) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + tblName + (primary_key > 0 ? " WHERE ID=" + primary_key : ""), null);
        return res;
    }

    public String DBDelete(String tblName, long primary_key) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.rawQuery("DELETE FROM " + tblName + (primary_key > 0 ? " WHERE ID=" + primary_key : ""), null);
            return SUCCESS;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    //endregion

    //region INSERT UPDATE
    public boolean DBSaveUpdate(Long key, ContentValues data, String TBLNAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (key > 0)
            db.update(TBLNAME, data, "id = ? ",
                    new String[]{Long.toString(key)});
        else
            db.insert(TBLNAME, null, data);
        return true;
    }

    public String DBDMLQuery(String sqlQry) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(sqlQry);
            return SUCCESS;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public void updateField(String TBLNAME, String CLMNAME, String VALUE, long ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TBLNAME + " SET " + CLMNAME + "='" + VALUE + "' WHERE ID=" + ID);

    }

    //endregion

    //region GETLIST
    public Cursor DBGetDataFilter(String TBLNAME, String CLMNAME, String INPUT) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TBLNAME + " WHERE " + CLMNAME + "=" + INPUT, null);
        return res;
    }

    public Cursor DBGroupPerson() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = tblGroupPersonLink.GetDetailQry(db);
        return res;
    }

    public Cursor DBFetchGroupLink(String periodkeys) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = tblTransHeader.DBGenTransHeader(db, periodkeys);
        return res;
    }
    public Cursor DBFetchLoanTransHeader(String periodkeys) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = tblTransHeader.DBGenLoanTransHeader(db, periodkeys);
        return res;
    }

    public Cursor DBFetchTransHeader(String periodkeys) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = tblTransHeader.DBGetTransHeader(db, periodkeys, "");
        return res;
    }

    public Cursor DBGetTransDetail(long headerKey) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = tblTransDetail.DBGetTransDetail(db, headerKey);
        return res;
    }
    //endregion
}