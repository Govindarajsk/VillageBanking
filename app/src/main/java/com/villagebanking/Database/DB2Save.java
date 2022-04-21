package com.villagebanking.Database;

import android.content.ContentValues;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTransaction;
import com.villagebanking.Utility.StaticUtility;

import java.text.SimpleDateFormat;
import java.util.Locale;

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
                BOPersonTransaction pt = (BOPersonTransaction) data;
                return new BOMap(pt.getKeyID(), getContentValue(pt));
        }
        return null;
    }

    //ID integer primary key,TRANS_DATE text, GROUP_PERSON_KEY integer,AMOUNT decimal,STATUS text
    static ContentValues getContentValue(BOPersonTransaction g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TRANS_DATE", g.getTransDate().toString());
        contentValues.put("GROUP_PERSON_KEY", g.getGp_key());
        contentValues.put("AMOUNT", g.getAmount());
        contentValues.put("STATUS", g.getStatus());
        return contentValues;
    }

    //group_person_link(id integer primary key, group_key integer,person_key integer,order_by integer,person_role text)
    static ContentValues getContentValue(BOGroupPersonLink g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("group_key",g.getGroup_Detail().getPrimary_key());
        contentValues.put("person_key", g.getPerson_Detail().getPrimary_key());
        contentValues.put("order_by", g.getOrderBy());
        contentValues.put("person_role", g.getPerson_role());
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
        contentValues.put("START_PERIOD_KEY", g.getStartPeriodKey());
        contentValues.put("BOND_CHARGE", g.getBondCharge());
        return contentValues;
    }

    //Persons =>id integer primary key, FirstName text,LastName text,phone text
    static ContentValues getContentValue(BOPerson g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName", g.getStrFName());
        contentValues.put("LastName", g.getStrLName());
        contentValues.put("phone", g.getNumMobile());
        return contentValues;
    }

    // PERIODS =>ID integer primary key,PERIOD_TYPE INTEGER,PERIOD_NAME TEXT,ACTUAL_DATE DATE,PERIOD_REMARKS TEXT
    static ContentValues getContentValue(BOPeriod g) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("PERIOD_TYPE", g.getPeriodType());
        contentValues.put("PERIOD_NAME", g.getPeriodName());

        String formattedDateString = StaticUtility.getDateString(g.getActualDate());
        contentValues.put("ACTUAL_DATE", formattedDateString);

        contentValues.put("DATEVALUE",StaticUtility.getDateInteger(g.getPeriodValue()));

        contentValues.put("PERIOD_REMARKS", g.getPeriodRemarks());
        return contentValues;
    }
}
