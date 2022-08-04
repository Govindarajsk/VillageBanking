package com.villagebanking.Database;

import android.content.ContentValues;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblPerson;

public class DB2Save {

    public static <T> DB1BOMap DTOSaveUpdate(T data, String tableName) {
        switch (tableName) {
            case tblPerson.Name:
                BOPerson p = (BOPerson) data;
                return new DB1BOMap(p.getPrimary_key(), getContentValue(p));
            case tblGroup.Name:
                BOGroup g = (BOGroup) data;
                return new DB1BOMap(g.getPrimary_key(), getContentValue(g));
            case tblGroupPersonLink.Name:
                BOGroupPersonLink gp = (BOGroupPersonLink) data;
                return new DB1BOMap(gp.getPrimary_key(), getContentValue(gp));
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
}
