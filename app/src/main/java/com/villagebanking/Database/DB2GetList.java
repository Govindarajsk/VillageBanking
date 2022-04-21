package com.villagebanking.Database;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTransaction;
import com.villagebanking.Utility.StaticUtility;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DB2GetList {

    public static <T> ArrayList<T> DTOGetAlls(Cursor res, String tableName) {
        ArrayList<T> returnList = new ArrayList<>();
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            switch (tableName) {
                case DB1Tables.PERSONS:
                    returnList.add((T) getValue(res, new BOPerson()));
                    break;
                case DB1Tables.GROUPS:
                    returnList.add((T) getValue(res, new BOGroup()));
                    break;
                case DB1Tables.PERIODS:
                    returnList.add((T) getValue(res, new BOPeriod()));
                    break;
                case DB1Tables.GROUP_PERSON_LINK:
                    returnList.add((T) getValue(res, new BOGroupPersonLink()));
                    break;
                case DB1Tables.PERSON_TRANSACTION:
                    returnList.add((T) getValue(res, new BOPersonTransaction()));
                    break;
            }
            res.moveToNext();
        }
        return returnList;
    }

    static BOPerson getValue(Cursor res, BOPerson test) {
        BOPerson newData = new BOPerson();
        newData.setPrimary_key(res.getLong(0));
        newData.setStrFName(res.getString(1));
        newData.setStrLName(res.getString(2));
        newData.setNumMobile(res.getLong(3));
        return newData;
    }

    /*
                "ID INTEGER PRIMARY KEY, " +
                "NAME TEXT," +
                "NO_OF_PERSON INTEGER," +
                "AMOUNT DECIMAL" +
                "START_PERIOD_KEY INTEGER"+
                "BOND_CHARGE DECIMAL"+
     */
    static BOGroup getValue(Cursor res, BOGroup test) {
        BOGroup newData = new BOGroup();
        newData.setPrimary_key(res.getInt(0));
        newData.setName(res.getString(1));
        newData.setNoOfPerson(res.getInt(2));
        newData.setAmount(res.getDouble(3));
        newData.setStartPeriodKey(res.getInt(4));
        newData.setBondCharge(res.getDouble(5));
        return newData;
    }

    static BOGroupPersonLink getValue(Cursor res, BOGroupPersonLink test) {
        BOGroupPersonLink newData = new BOGroupPersonLink();
        newData.setPrimary_key(res.getLong(0));
        long group_key = res.getLong(1);

        ArrayList<Object> groupList = DBUtility.DTOGetData(DB1Tables.GROUPS, group_key);
        if(groupList.size()>0) {
            BOGroup group = (BOGroup) groupList.get(0);
            newData.setGroup_Detail(group);
        }
        long person_key = res.getLong(2);
        ArrayList<Object> personList=DBUtility.DTOGetData(DB1Tables.PERSONS, person_key);
        if(personList.size()>0) {
            BOPerson person = (BOPerson) personList.get(0);
            newData.setPerson_Detail(person);
        }
        newData.setOrderBy(res.getInt(3));
        newData.setPerson_role(res.getString(4));
        return newData;
    }

    static BOPersonTransaction getValue(Cursor res, BOPersonTransaction test) {
        BOPersonTransaction newData = new BOPersonTransaction();
        newData.setKeyID(res.getInt(0));
        newData.setTransDate(res.getString(1));
        newData.setGp_key(res.getInt(2));
        newData.setAmount(res.getDouble(3));
        newData.setStatus(res.getString(4));
        return newData;
    }

    //PERIODS =>ID integer primary key,PERIOD_TYPE INTEGER,PERIOD_NAME TEXT,ACTUAL_DATE DATE,PERIOD_REMARKS TEXT
    static BOPeriod getValue(Cursor res, BOPeriod test) {
        BOPeriod newData = new BOPeriod();
        newData.setPrimary_key(res.getInt(0));
        newData.setPeriodType(res.getInt(1));
        newData.setPeriodName(res.getString(2));
        newData.setActualDate(StaticUtility.getDateString(res.getString(3)));
        newData.setPeriodRemarks(res.getString(4));
        return newData;
    }
}
