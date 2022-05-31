package com.villagebanking.DBTables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.villagebanking.BOObjects.BOTransHeader;

public class tblTransHeader extends tblBase {

    //ID,PERIOD_KEY,TABLE_NAME,TABLE_LINK_KEY,REMARKS,TRANS_DATE,TOTAL_AMOUNT,PAID_AMOUNT,BALANCE_AMOUNT

    //region Static
    public static final String Name = "TRANSACTION_HEADER";
    public static final String CreateTable =
            Name + "(" +
                    "ID INTEGER PRIMARY KEY," +
                    "PERIOD_KEY INTEGER, " +
                    "TABLE_NAME TEXT," +
                    "TABLE_LINK_KEY INTEGER," +
                    "REMARKS TEXT," +
                    "TRANS_DATE TEXT," +
                    "TOTAL_AMOUNT DECIMAL," +
                    "PAID_AMOUNT DECIMAL," +
                    "BALANCE_AMOUNT DECIMAL" +
                    ")";
    //endregion

    public static ContentValues getContentValue(BOTransHeader g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PERIOD_KEY", g.getPeriodKey());
        contentValues.put("TABLE_NAME", g.getTableName());
        contentValues.put("TABLE_LINK_KEY", g.getTableLinkKey());
        contentValues.put("REMARKS", g.getRemarks());
        contentValues.put("TRANS_DATE", g.getTransDate());
        contentValues.put("TOTAL_AMOUNT", g.getTotalAmount());
        contentValues.put("PAID_AMOUNT", g.getPaidAmount());
        contentValues.put("BALANCE_AMOUNT", g.getBalanceAmount());
        return contentValues;
    }

    public static BOTransHeader getValue(Cursor res) {
        BOTransHeader newData = new BOTransHeader();
        int i = 0;
        newData.setPrimary_key(res.getLong(i++));
        newData.setPeriodKey(res.getLong(i++));
        newData.setTableName(res.getString(i++));
        newData.setTableLinkKey(res.getLong(i++));
        newData.setRemarks(res.getString(i++));
        newData.setTransDate(res.getString(i++));
        newData.setTotalAmount(res.getDouble(i++));
        newData.setPaidAmount(res.getDouble(i++));
        newData.setBalanceAmount(res.getDouble(i++));
        return newData;
    }

    public static Cursor getQuery(SQLiteDatabase db, String periodkeys) {
        Cursor res = db.rawQuery(
                "SELECT " +
                        "0 AS ID,"
                        + "G.START_PERIOD_KEY AS PERIOD_KEY,"
                        + "'GROUP_PERSON_LINK' AS TABLE_NAME,"
                        + "GPL.ID AS TABLE_LINK_KEY,"
                        + "G.NAME AS REMARKS,"
                        + "'' AS TRANS_DATE,"
                        + "G.AMOUNT AS TOTAL_AMOUNT,"
                        + "0 AS PAID_AMOUNT,"
                        + "G.AMOUNT AS BALANCE_AMOUNT "
                        + "FROM "
                        + "GROUPS G JOIN "
                        + "GROUP_PERSON_LINK GPL ON G.ID=GPL.GROUP_KEY" +
                        (periodkeys.length() > 0 ? " WHERE G.START_PERIOD_KEY IN (" + periodkeys + ")" : "")
                ,
                null);
        return res;
    }

    public Cursor DBGetTransHeader(SQLiteDatabase db, String parentKeys, String periodKeys, String childKeys, String linkKeys) {
        Cursor res = db.rawQuery(
                "SELECT " +
                        "ID," +
                        "PARENT_KEY," +
                        "CHILD_KEY," +
                        "PERIOD_KEY," +
                        "TABLE_NAME," +
                        "TABLE_LINK_KEY," +
                        "REMARKS," +
                        "TOTAL_AMOUNT," +
                        "PAID_AMOUNT," +
                        "BALANCE_AMOUNT " +
                        "FROM " +
                        "TRANSACTION_HEADER " +
                        (childKeys.length() > 0 ? " WHERE PARENT_KEY IN (" + childKeys + ")" : "") +
                        (periodKeys.length() > 0 ? " WHERE PERIOD_KEY IN (" + periodKeys + ")" : "") +
                        (linkKeys.length() > 0 ? " WHERE TABLE_LINK_KEY IN (" + linkKeys + ")" : "")
                , null);


        return res;
    }

}
