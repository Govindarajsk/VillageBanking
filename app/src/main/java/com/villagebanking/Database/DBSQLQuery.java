package com.villagebanking.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSQLQuery extends SQLiteOpenHelper {

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

    //Persons,Groups,group_person_link
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

    public Cursor DBGetAll(String TBLNAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TBLNAME, null);
        return res;
    }

    public Cursor DBGetData(String TBLNAME, long primary_key) {
        if (TBLNAME == DB1Tables.PERSON_TRANSACTION) {
            return DBGetTrans(primary_key);
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TBLNAME + " WHERE ID=" + primary_key, null);
        return res;
    }

    /*
        GROUPS => "ID INTEGER PRIMARY KEY, " +
                    "NAME TEXT," +
                    "NO_OF_PERSON INTEGER," +
                    "AMOUNT DECIMAL," +
                    "START_PERIOD_KEY INTEGER,"+
                    "BOND_CHARGE DECIMAL"
    */
    /*
        GROUP_PERSON_LINK =>"ID INTEGER PRIMARY KEY, " +
                            "GROUP_KEY INTEGER," +
                            "PERSON_KEY INTEGER," +
                            "ORDER_BY INTEGER," +
                            "PERSON_ROLE TEXT"
    */
    /*
        PERSON_TRANSACTION + "(" +
                        "ID INTEGER PRIMARY KEY," +
                         PARENT_KEY INTEGER
                        "PERIOD_KEY INTEGER, " +
                        "TABLE_NAME TEXT,"+
                        "TABLE_LINK_KEY INTEGER,"+
                        "REMARKS TEXT,"+
                        "AMOUNT DECIMAL"

    */
    public Cursor DBGetTrans(long person_key) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT " +
                        "PTR.ID AS ID," +
                        "PTR.PARENT_KEY AS PARENTKEY,"+
                        "PERIOD_KEY," +
                        "'GROUP_PERSON_LINK' AS TABLE_NAME," +
                        "GPL.ID AS TABLE_LINK_KEY," +
                        "G.NAME AS REMARKS," +
                        "PTR.AMOUNT AS AMOUNT," +
                        "G.AMOUNT AS TRANSAMOUNT," +
                        "G.ID AS GROUP_KEY " +
                        "FROM " +
                        "GROUPS G JOIN "+
                        "GROUP_PERSON_LINK GPL ON G.ID=GPL.GROUP_KEY LEFT JOIN " +
                        "PERSON_TRANSACTION PTR ON TABLE_LINK_KEY=GPL.ID  WHERE PERSON_KEY ="+person_key, null);
        return res;
    }
}