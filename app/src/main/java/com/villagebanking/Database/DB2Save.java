package com.villagebanking.Database;

import android.content.ContentValues;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTrans;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;

public class DB2Save {

    public static <T> DB1BOMap DTOSaveUpdate(T data, String tableName) {
        switch (tableName) {
            case tblPerson.Name:
                BOPerson p = (BOPerson) data;
                return new DB1BOMap(p.getPrimary_key(), getContentValue(p));
            case tblGroup.Name:
                BOGroup g = (BOGroup) data;
                return new DB1BOMap(g.getPrimary_key(), getContentValue(g));
            case tblPeriod.Name:
                BOPeriod pd = (BOPeriod) data;
                return new DB1BOMap(pd.getPrimary_key(), getContentValue(pd));
            case tblGroupPersonLink.Name:
                BOGroupPersonLink gp = (BOGroupPersonLink) data;
                return new DB1BOMap(gp.getPrimary_key(), getContentValue(gp));
            case tblTransDetail.Name:
                BOTransDetail td = (BOTransDetail) data;
                return new DB1BOMap(td.getPrimary_key(), tblTransDetail.getContentValue(td));
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
        contentValues.put("GROUP_KEY", g.getGroup_Key());
        contentValues.put("PERSON_KEY", g.getPerson_Key());
        //contentValues.put("ORDER_BY", g.getOrderBy());
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
        "DATE_VALUE INTEGER," +
        "PERIOD_REMARKS TEXT" +
     */
    static ContentValues getContentValue(BOPeriod g) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("PERIOD_TYPE", g.getPeriodType());
        contentValues.put("PERIOD_NAME", g.getPeriodName());

        contentValues.put("ACTUAL_DATE", g.getActualDate());
        contentValues.put("DATE_VALUE", g.getPeriodValue());
        contentValues.put("PERIOD_REMARKS", g.getPeriodRemarks());
        contentValues.put("PERIOD_STATUS", "");
        return contentValues;
    }
}
