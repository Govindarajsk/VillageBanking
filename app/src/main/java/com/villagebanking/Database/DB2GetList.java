package com.villagebanking.Database;

import android.database.Cursor;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTransaction;

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
        newData.setKeyID(res.getInt(0));
        newData.setStrFName(res.getString(1));
        newData.setStrLName(res.getString(2));
        newData.setNumMobile(res.getLong(3));
        return newData;
    }

    static BOGroup getValue(Cursor res, BOGroup test) {
        BOGroup newData = new BOGroup();
        newData.setKeyID(res.getInt(0));
        newData.setName(res.getString(1));
        newData.setNoOfPerson(res.getInt(2));
        newData.setAmount(res.getDouble(3));
        return newData;
    }

    static BOGroupPersonLink getValue(Cursor res, BOGroupPersonLink test) {
        BOGroupPersonLink newData = new BOGroupPersonLink();
        newData.setKeyID(res.getInt(0));
        newData.setGroup_key(res.getInt(1));
        newData.setPerson_key(res.getInt(2));
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

        String sDate1 = res.getString(3);
        ParsePosition parsePosition = new ParsePosition(0);
        newData.setActualDate(new SimpleDateFormat(DBUtility.dateFormat).parse(sDate1, parsePosition));

        newData.setPeriodRemarks(res.getString(4));
        return newData;
    }
}
