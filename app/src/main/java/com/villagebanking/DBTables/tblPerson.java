package com.villagebanking.DBTables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

public class tblPerson extends tblBase{
    public static String Name = "PERSONS";

    public static ArrayList<BOGroupPersonLink> getDetails() {
        Cursor res = DBUtility.DBSQLQuery.DBGetAll(Name);
        return readValue(res);
    }

    public static BOGroupPersonLink getData(long primaryKey) {
        Cursor res = DBUtility.DBSQLQuery.DBGetData(Name, primaryKey);
        ArrayList<BOGroupPersonLink> fullList = readValue(res);
        return fullList.size() > 0 ? fullList.get(0) : new BOGroupPersonLink();
    }

    static ArrayList<BOGroupPersonLink> readValue(Cursor res) {
        ArrayList<BOGroupPersonLink> groupPersonLinks = new ArrayList<>();
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            BOGroupPersonLink newData = new BOGroupPersonLink();

        }
        return groupPersonLinks;
    }
}
