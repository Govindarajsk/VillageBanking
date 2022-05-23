package com.villagebanking.Database;

import android.database.Cursor;

import com.villagebanking.BOObjects.BOAutoComplete;
import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTrans;

import java.util.ArrayList;

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
                    returnList.add((T) getValue(res, new BOPersonTrans()));
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
        ArrayList<Object> periodList = DBUtility.DTOGetData(DB1Tables.PERIODS, period_key);
        if (periodList.size() > 0) {
            BOPeriod item = (BOPeriod) periodList.get(0);
            newData.setPeriodDetail(item);
        }

        newData.setBondCharge(res.getDouble(5));
        return newData;
    }

    /*
        "ID INTEGER PRIMARY KEY, " +
        "GROUP_KEY INTEGER," +
        "PERSON_KEY INTEGER," +
        "ORDER_BY INTEGER," +
        "PERSON_ROLE TEXT" +
    */
    static BOGroupPersonLink getValue(Cursor res, BOGroupPersonLink test) {
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
        return newData;
    }

    /*
       "ID INTEGER PRIMARY KEY," +
       "PERIOD_KEY INTEGER, " +
       "TABLE_NAME TEXT,"+
       "TABLE_LINK_KEY INTEGER,"+
       "REMARKS TEXT,"+
       "AMOUNT DECIMAL," +

       "PTR.ID AS ID," +
        "PTR.PARENT_KEY AS PARENTKEY,"+
        "PERIOD_KEY," +
        "'GROUP_PERSON_LINK' AS TABLE_NAME," +
        "GPL.ID AS TABLE_LINK_KEY," +
        "G.NAME AS REMARKS," +
        "PTR.AMOUNT AS AMOUNT," +
        "G.AMOUNT AS TRANSAMOUNT," +
        "G.ID AS GROUP_KEY " +
     */
    static BOPersonTrans getValue(Cursor res, BOPersonTrans test) {
        BOPersonTrans newData = new BOPersonTrans();
        newData.setPrimary_key(res.getLong(0));
        newData.setParentKey(res.getLong(1));
        newData.getPeriod_detail().setPrimary_key(res.getLong(2));
        newData.setTableName(res.getString(3));
        newData.setTable_link_key(res.getLong(4));

        //if (newData.getTableName() == DB1Tables.GROUP_PERSON_LINK) {
            ArrayList<BOGroupPersonLink> tableLinkDetail = DBUtility.DTOGetData(DB1Tables.GROUP_PERSON_LINK, newData.getTable_link_key());
            if (tableLinkDetail.size() > 0) {
                BOGroupPersonLink linkData = tableLinkDetail.get(0);
                newData.setDetail1(new BOAutoComplete(linkData.getGroup_Detail().getPrimary_key(),linkData.getGroup_Detail().getName()));
                newData.setDetail2(new BOAutoComplete(linkData.getPerson_Detail().getPrimary_key(),linkData.getPerson_Detail().getFullName()));
            }
       // }

        newData.setRemarks(res.getString(5));
        newData.setNewAmount(res.getDouble(6));
        if (res.getColumnCount() > 8)
            newData.setActualAmount(res.getDouble(7));
        if (res.getColumnCount() > 9)
            newData.setForien_key(res.getLong(8));
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
        //newData.setPeriodStatus(res.getString(6));
        return newData;
    }
}
