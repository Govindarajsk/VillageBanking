package com.villagebanking.DBTables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBSQLQuery;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;
import java.util.List;

public class tblGroupPersonLink extends tblBase {

    public static String Name = "GROUP_PERSON_LINK";

    public static String GetDetailQuery() {
        return "";
    }

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
            newData.setPrimary_key(res.getLong(0));
            long group_key = res.getLong(1);
            long person_key = res.getLong(2);
            newData.setOrderBy(res.getInt(3));
            newData.setPerson_role(res.getString(4));

            ArrayList<Object> groupList = DBUtility.DTOGetData(DB1Tables.GROUPS, group_key);
            if (groupList.size() > 0) {
                BOGroup group = (BOGroup) groupList.get(0);
                newData.setGroup_Detail(group);
            }
            ArrayList<Object> personList = DBUtility.DTOGetData(DB1Tables.PERSONS, person_key);
            if (personList.size() > 0) {
                BOPerson person = (BOPerson) personList.get(0);
                newData.setPerson_Detail(person);
            }
            groupPersonLinks.add(newData);
        }
        return groupPersonLinks;
    }
}
