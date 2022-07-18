package com.villagebanking.DBTables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.villagebanking.BOObjects.BOGroupPersonLink;


public class tblGroupPersonLink extends tblBase {

    public static final String Name = "GROUP_PERSON_LINK";

    /*
        "ID INTEGER PRIMARY KEY, " +
        "GROUP_KEY INTEGER," +
        "PERSON_KEY INTEGER," +
        "ORDER_BY INTEGER," +
        "PERSON_ROLE TEXT" +
    */
    public static BOGroupPersonLink readValue(Cursor res) {
        BOGroupPersonLink newData = new BOGroupPersonLink();
        newData.setPrimary_key(res.getLong(0));
        newData.setGroup_Key(res.getLong(1));
        newData.setPerson_Key(res.getLong(2));
       // newData.setOrderBy(res.getInt(3));
        newData.setPerson_role(res.getString(3));
        return newData;
    }

    public static Cursor GetDetailQry(SQLiteDatabase db) {
        return db.rawQuery(
                "SELECT " +
                        "ID," +
                        "GROUP_KEY," +
                        "PERSON_KEY," +
                        "PERSON_ROLE " +
                        "FROM " +
                        Name
                , null);

    }
}
