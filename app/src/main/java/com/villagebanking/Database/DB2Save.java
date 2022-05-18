package com.villagebanking.Database;

import android.content.ContentValues;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTrans;

public class DB2Save {

    public static <T> BOMap DTOSaveUpdate(T data, String tableName) {
        switch (tableName) {
            case DB1Tables.PERSONS:
                BOPerson p = (BOPerson) data;
                return new BOMap(p.getPrimary_key(), getContentValue(p));
            case DB1Tables.GROUPS:
                BOGroup g = (BOGroup) data;
                return new BOMap(g.getPrimary_key(), getContentValue(g));
            case DB1Tables.PERIODS:
                BOPeriod pd = (BOPeriod) data;
                return new BOMap(pd.getPrimary_key(), getContentValue(pd));
            case DB1Tables.GROUP_PERSON_LINK:
                BOGroupPersonLink gp = (BOGroupPersonLink) data;
                return new BOMap(gp.getPrimary_key(), getContentValue(gp));
            case DB1Tables.PERSON_TRANSACTION:
                BOPersonTrans pt = (BOPersonTrans) data;
                return new BOMap(pt.getPrimary_key(), getContentValue(pt));
        }
        return null;
    }

    /*
        "ID INTEGER PRIMARY KEY, " +
        "FIRSTNAME TEXT," +
        "LASTNAME TEXT," +
        "PHONE TEXT"+;
     */
    static ContentValues getContentValue(BOPerson g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("FIRSTNAME", g.getStrFName());
        contentValues.put("LASTNAME", g.getStrLName());
        contentValues.put("PHONE", g.getNumMobile());
        return contentValues;
    }

    /*
        "ID INTEGER PRIMARY KEY," +
        "PERIOD_KEY INTEGER, " +
        "TABLE_NAME TEXT,"+
        "TABLE_LINK_KEY INTEGER,"+
        "REMARKS TEXT,"+
        "AMOUNT DECIMAL," +
     */
    static ContentValues getContentValue(BOPersonTrans g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PARENT_KEY", g.getParentKey());
        contentValues.put("PERIOD_KEY", g.getPeriod_detail().getPrimary_key());
        contentValues.put("TABLE_NAME", g.getTableName());
        contentValues.put("TABLE_LINK_KEY", g.getTable_link_key());
        contentValues.put("REMARKS", g.getRemarks());
        contentValues.put("AMOUNT", g.getNewAmount());
        //contentValues.put("AMOUNT", g.getForien_key());
        return contentValues;
    }

    /*
        "ID INTEGER PRIMARY KEY, " +
        "GROUP_KEY INTEGER," +
        "PERSON_KEY INTEGER," +
        "ORDER_BY INTEGER," +
        "PERSON_ROLE TEXT" +
     */
    static ContentValues getContentValue(BOGroupPersonLink g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("GROUP_KEY", g.getGroup_Detail().getPrimary_key());
        contentValues.put("PERSON_KEY", g.getPerson_Detail().getPrimary_key());
        contentValues.put("ORDER_BY", g.getOrderBy());
        contentValues.put("PERSON_ROLE", g.getPerson_role());
        return contentValues;
    }

    /*
        "ID INTEGER PRIMARY KEY, " +
        "NAME TEXT," +
        "NO_OF_PERSON INTEGER," +
        "AMOUNT DECIMAL" +
        "START_PERIOD_KEY INTEGER"+
        "BOND_CHARGE DECIMAL"+
     */
    static ContentValues getContentValue(BOGroup g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", g.getName());
        contentValues.put("NO_OF_PERSON", g.getNoOfPerson());
        contentValues.put("AMOUNT", g.getAmount());
        contentValues.put("START_PERIOD_KEY", g.getPeriodDetail().getPrimary_key());
        contentValues.put("BOND_CHARGE", g.getBondCharge());
        return contentValues;
    }

    /*
        "ID INTEGER PRIMARY KEY," +
        "PERIOD_TYPE INTEGER, " +
        "PERIOD_NAME TEXT, " +
        "ACTUAL_DATE TEXT," +
        "DATEVALUE INTEGER," +
        "PERIOD_REMARKS TEXT" +
     */
    static ContentValues getContentValue(BOPeriod g) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("PERIOD_TYPE", g.getPeriodType());
        contentValues.put("PERIOD_NAME", g.getPeriodName());

        contentValues.put("ACTUAL_DATE",  g.getActualDate());

        contentValues.put("DATEVALUE", g.getPeriodValue());

        contentValues.put("PERIOD_REMARKS", g.getPeriodRemarks());
        return contentValues;
    }
}
