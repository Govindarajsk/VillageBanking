package com.villagebanking.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";

    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String tableCreateQuery : DB0Tables.getCreateTables()) {
            db.execSQL("CREATE TABLE " + tableCreateQuery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (String tableName : DB0Tables.getTables()) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }
        onCreate(db);
    }

    //Persons,Groups,group_person_link
    public Integer DBdelete(Integer key, String TBLNAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TBLNAME,
                "id = ? ",
                new String[]{Integer.toString(key)});
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

    public Cursor DBGetAll(String TBLNAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TBLNAME, null);
        return res;
    }
}