package com.villagebanking.DBTables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;


public class tblGroupPersonLink extends tblBase {

    public static final String Name = "GROUP_PERSON_LINK";
    public static final String CREATE_GROUP_PERSON_LINK =
            Name + "(" +
                    "ID INTEGER PRIMARY KEY, " +
                    "GROUP_KEY INTEGER," +
                    "PERSON_KEY INTEGER," +
                    "ORDER_BY INTEGER," +
                    "PERSON_ROLE TEXT" +
                    ")";

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

        ArrayList<BOPerson> boPeople = DBUtility.DTOGetData(tblPerson.Name, newData.getPerson_Key());
        if (boPeople.size() > 0)
            newData.PersonDetail = boPeople.get(0);
        ArrayList<BOGroup> groups = DBUtility.DTOGetData(tblGroup.Name, newData.getGroup_Key());
        if (groups.size() > 0)
            newData.GroupDetail = groups.get(0);

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
