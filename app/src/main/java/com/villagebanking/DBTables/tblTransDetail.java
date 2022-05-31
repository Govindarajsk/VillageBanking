package com.villagebanking.DBTables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.villagebanking.BOObjects.BOTransDetail;

public class tblTransDetail extends tblBase {

    public static final String Name = "TRANSACTION_DETAIL";
    public static final String CreateTable =
            Name + "(" +
                    "ID INTEGER PRIMARY KEY," +
                    "PARENT_KEY INTEGER, " +
                    "HEADER_KEY INTEGER, " +
                    "DATE TEXT, " +
                    "AMOUNT DECIMAL, " +
                    "REMARKS TEXT" +
                    ")";

    public static BOTransDetail getValue(Cursor res) {
        BOTransDetail newData = new BOTransDetail();
        int i = 0;
        newData.setPrimary_key(res.getLong(i++));
        newData.setParentKey(res.getLong(i++));
        newData.setHeaderKey(res.getLong(i++));
        newData.setTransDate(res.getString(i++));
        newData.setAmount(res.getDouble(i++));
        if (res.getColumnCount() > 6) {
            newData.setPeriodKey(res.getLong(i++));
            newData.setTableName(res.getString(i++));
            newData.setTableLinkKey(res.getLong(i++));
            newData.setRemarks(res.getString(i++));
            newData.setTotalAmount(res.getDouble(i++));
            newData.setPaidAmount(res.getDouble(i++));
            newData.setBalanceAmount(res.getDouble(i++));
        }
        return newData;
    }

    public static ContentValues getContentValue(BOTransDetail g) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("HEADER_KEY", g.getHeaderKey());
        contentValues.put("DATE", g.getTransDate());
        contentValues.put("AMOUNT", g.getAmount());
        contentValues.put("REMARKS", g.getRemarks());
        return contentValues;
    }

    public static Cursor DBGetTransDetail(SQLiteDatabase db, String parentKeys, String periodKeys, String childKeys, String linkKeys) {
        return db.rawQuery(
                "SELECT " +
                        "TD.ID AS ID," +
                        "TD.PARENT_KEY," +
                        "TD.HEADER_KEY," +
                        "TD.DATE," +
                        "TD.AMOUNT AS AMOUNT," +
                        "TH.PERIOD_KEY," +
                        "TH.TABLE_NAME," +
                        "TH.TABLE_LINK_KEY," +
                        "TH.REMARKS," +
                        "TH.TOTAL_AMOUNT," +
                        "TH.PAID_AMOUNT," +
                        "TH.BALANCE_AMOUNT " +
                        "FROM " +
                        "TRANSACTION_HEADER TH JOIN " +
                        "TRANSACTION_DETAIL TD ON TD.HEADER_KEY=TH.ID " +
                        (childKeys.length() > 0 ? " WHERE PARENT_KEY IN (" + childKeys + ")" : "") +
                        (periodKeys.length() > 0 ? " WHERE PERIOD_KEY IN (" + periodKeys + ")" : "") +
                        (linkKeys.length() > 0 ? " WHERE TABLE_LINK_KEY IN (" + linkKeys + ")" : "")
                , null);

    }
}
