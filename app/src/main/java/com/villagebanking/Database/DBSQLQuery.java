package com.villagebanking.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblLoanHeader;
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

    public Integer DBDelete(long key, String TBLNAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TBLNAME,
                "id = ? ",
                new String[]{Long.toString(key)});
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

    public void updateField(String TBLNAME, String CLMNAME, String VALUE, long ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TBLNAME + " SET " + CLMNAME + "='" + VALUE + "' WHERE ID=" + ID);

    }

    //endregion


    public String ExecuteSQL(String sqlQuery) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlQuery);

        Cursor res = db.rawQuery("SELECT MAX(ID)  FROM " + tblLoanHeader.Name, null);
        String value = "0";
        while (res.moveToNext()) {//there is no need to use while, if condition is good.
            value = String.valueOf(res.getLong(0));
            value = res.getString(0);
            System.out.println(value);
        }
        return value;
    }
}