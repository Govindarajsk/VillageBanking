package com.villagebanking.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Utility.StaticUtility;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";

    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        ArrayList<String> createQury= new ArrayList<>();
        String PersonTable = "create table "+StaticUtility.PERSONS+"(id integer primary key, FirstName text,LastName text,phone text)";
        createQury.add(PersonTable);
        db.execSQL(PersonTable);//Persons
        String GroupsTable = "create table "+StaticUtility.GROUPS+"(id integer primary key, name text,personsCount integer,amount decimal)";
        db.execSQL(GroupsTable);//Groups group_person_link
        String GroupPersonLink = "create table "+StaticUtility.GROUP_PERSON_LINK+"(id integer primary key, group_key integer,person_key integer,order_by integer,person_role text)";
        db.execSQL(GroupPersonLink);//Groups
        String PersonTransaction = "CREATE TABLE "+ StaticUtility.PERSON_TRANSACTION +"(ID integer primary key,TRANS_DATE text, GROUP_PERSON_KEY integer,AMOUNT decimal,STATUS text)";
        db.execSQL(PersonTransaction);//PersonTrans
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+StaticUtility.PERSONS);
        db.execSQL("DROP TABLE IF EXISTS "+StaticUtility.GROUPS);
        db.execSQL("DROP TABLE IF EXISTS "+StaticUtility.GROUP_PERSON_LINK);
        db.execSQL("DROP TABLE IF EXISTS "+StaticUtility.PERSON_TRANSACTION);
        onCreate(db);
    }

    //Persons,Groups,group_person_link
    public Integer DBdelete(Integer key, String TBLNAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TBLNAME,
                "id = ? ",
                new String[]{Integer.toString(key)});
    }

    public boolean DBSaveUpdate(Integer key, ContentValues data,String TBLNAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (key > 0)
            db.update(TBLNAME, data, "id = ? ",
                    new String[]{Integer.toString(key)});
        else
            db.insert(TBLNAME, null, data);
        return true;
    }
    public Cursor DBGetAll(String TBLNAME) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TBLNAME, null);
        return  res;
    }
}