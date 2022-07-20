package com.villagebanking.Database;

import android.database.Cursor;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.DBTables.tblTransDetail;

import java.util.ArrayList;

public class DB2GetList {

    public static <T> ArrayList<T> DTOGetAlls(Cursor res, String tableName) {
        ArrayList<T> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            switch (tableName) {
                case tblPerson.Name:
                    returnList.add((T) getValue(res, new BOPerson()));
                    break;
                case tblGroup.Name:
                    returnList.add((T) getValue(res, new BOGroup()));
                    break;
                case tblPeriod.Name:
                    returnList.add((T) getValue(res, new BOPeriod()));
                    break;
                case tblGroupPersonLink.Name:
                    returnList.add((T) tblGroupPersonLink.readValue(res));
                    break;
                case tblTransHeader.Name:
                    returnList.add((T) tblTransHeader.readValue(res));
                    break;
                case tblTransDetail.Name:
                    returnList.add((T) tblTransDetail.getValue(res));
                    break;
            }
            res.moveToNext();
        }
        return returnList;
    }

    /*
       "ID INTEGER PRIMARY KEY, " +
       "FIRSTNAME TEXT," +
       "LASTNAME TEXT," +
       "PHONE TEXT"+;
    */
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

        long period_key = res.getLong(4);
        ArrayList<Object> periodList = DBUtility.DTOGetData(tblPeriod.Name, period_key);
        if (periodList.size() > 0) {
            BOPeriod item = (BOPeriod) periodList.get(0);
            newData.setPeriodDetail(item);
        }

        newData.setBondCharge(res.getDouble(5));
        return newData;
    }
    /*
        "ID INTEGER PRIMARY KEY," +
        "PERIOD_TYPE INTEGER, " +
        "PERIOD_NAME TEXT, " +
        "ACTUAL_DATE TEXT," +
        "DATEVALUE INTEGER," +
        "PERIOD_REMARKS TEXT" +
     */
    static BOPeriod getValue(Cursor res, BOPeriod test) {
        BOPeriod newData = new BOPeriod();
        newData.setPrimary_key(res.getInt(0));
        newData.setPeriodType(res.getInt(1));
        newData.setPeriodName(res.getString(2));
        newData.setActualDate(res.getString(3));
        newData.setPeriodValue(res.getLong(4));
        newData.setPeriodRemarks(res.getString(5));
        newData.setPeriodStatus(res.getString(6));
        return newData;
    }

}
