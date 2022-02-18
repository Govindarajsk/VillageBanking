package com.villagebanking.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTransaction;
import com.villagebanking.Database.DBHelper;
import com.villagebanking.Utility.StaticUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class DBUtility {
    //region Database Instance
    public static DBHelper dbHelper;
    public static void CreateDB(Context cntxt) {
        dbHelper = new DBHelper(cntxt, 3);
    }
    public static DBHelper getDB() {
        return dbHelper;
    }
    //endregion

    public static void DTOdelete(Integer ID, String TBLNAME) {
        dbHelper.DBdelete(ID, TBLNAME);
    }

    public static <T> String DTOSaveUpdate(T data) {
        Integer key = 0;
        String TBLNAME = "";
        ContentValues contentValues = new ContentValues();
        //Persons =>id integer primary key, FirstName text,LastName text,phone text
        if (data instanceof BOPerson) {
            BOPerson p = (BOPerson) data;
            contentValues.put("FirstName", p.getStrFName());
            contentValues.put("LastName", p.getStrLName());
            contentValues.put("phone", p.getNumMobile());

            key = p.getKeyID();
            TBLNAME = StaticUtility.PERSONS;
        }
        //Groups =>id integer primary key, name text,personsCount integer,amount decimal
        else if (data instanceof BOGroup) {
            BOGroup g = (BOGroup) data;
            contentValues.put("name", g.getName());
            contentValues.put("personsCount", g.getNoOfPerson());
            contentValues.put("amount", g.getAmount());

            key = g.getKeyID();
            TBLNAME = StaticUtility.GROUPS;
        }
        //group_person_link(id integer primary key, group_key integer,person_key integer,order_by integer,person_role text)
        else if (data instanceof BOGroupPersonLink) {
            BOGroupPersonLink g = (BOGroupPersonLink) data;

            contentValues.put("group_key", g.getGroup_key());
            contentValues.put("person_key", g.getPerson_key());
            contentValues.put("order_by", g.getOrderBy());
            contentValues.put("person_role", g.getPerson_role());

            key = g.getKeyID();
            TBLNAME = StaticUtility.GROUP_PERSON_LINK;
        }
        //ID integer primary key,TRANS_DATE text, GROUP_PERSON_KEY integer,AMOUNT decimal,STATUS text
        else if (data instanceof BOPersonTransaction) {
            BOPersonTransaction g = (BOPersonTransaction) data;
            contentValues.put("TRANS_DATE", g.getTransDate().toString());
            contentValues.put("GROUP_PERSON_KEY", g.getGp_key());
            contentValues.put("AMOUNT", g.getAmount());
            contentValues.put("STATUS", g.getStatus());

            key = g.getKeyID();
            TBLNAME = StaticUtility.PERSON_TRANSACTION;
        }
        if (!TBLNAME.isEmpty()) //SAVE TO TABLE
            dbHelper.DBSaveUpdate(key, contentValues, TBLNAME);
        return "";
    }

    public static <T> ArrayList<T> DTOGetAlls(String type) {
        ArrayList<T> returnList = new ArrayList<>();
        Cursor res = null;
        switch (type) {
            case "PERSONS":
                res = dbHelper.DBGetAll(StaticUtility.PERSONS);
                res.moveToFirst();

                while (res.isAfterLast() == false) {
                    BOPerson newData = new BOPerson();
                    newData.setKeyID(res.getInt(0));
                    newData.setStrFName(res.getString(1));
                    newData.setStrLName(res.getString(2));
                    newData.setNumMobile(res.getLong(3));

                    returnList.add((T)newData);
                    res.moveToNext();
                }
                return returnList;// (ArrayList<T>) array_list;
            case "GROUPS":
                res = dbHelper.DBGetAll(StaticUtility.GROUPS);
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    BOGroup newInst = new BOGroup();
                    newInst.setKeyID(res.getInt(0));
                    newInst.setName(res.getString(1));
                    newInst.setAmount(res.getDouble(3));
                    newInst.setNoOfPerson(res.getInt(2));
                    returnList.add((T)newInst);
                    res.moveToNext();
                }
                break;
            case "GROUP_PERSON_LINK":
                res = dbHelper.DBGetAll(StaticUtility.GROUP_PERSON_LINK);
                res.moveToFirst();

                while (res.isAfterLast() == false) {
                    BOGroupPersonLink newInst = new BOGroupPersonLink();
                    newInst.setKeyID(res.getInt(0));
                    newInst.setGroup_key(res.getInt(1));
                    newInst.setPerson_key(res.getInt(2));
                    newInst.setOrderBy(res.getInt(3));
                    newInst.setPerson_role(res.getString(4));
                    returnList.add((T)newInst);
                    res.moveToNext();
                }
                return returnList;
            case "PERSON_TRANSACTION":
                res = dbHelper.DBGetAll(StaticUtility.PERSON_TRANSACTION);
                res.moveToFirst();

                while (res.isAfterLast() == false) {
                    BOPersonTransaction newInst = new BOPersonTransaction();
                    newInst.setKeyID(res.getInt(0));
                    newInst.setTransDate(res.getString(1));
                    newInst.setGp_key(res.getInt(2));
                    newInst.setAmount(res.getDouble(3));
                    newInst.setStatus(res.getString(4));
                    returnList.add((T)newInst);
                    res.moveToNext();
                }
                return returnList;
        }
        return returnList;
    }
}
