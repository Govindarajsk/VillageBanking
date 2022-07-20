package com.villagebanking.DBTables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblUtility;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

public class tblTransHeader extends tblBase {

    //region CreateTable
    public static final String Name = "TRANSACTION_HEADER";

    private static String column1 = "PERIOD_KEY";
    private static String column2 = "TABLE_NAME";
    private static String column3 = "TABLE_LINK_KEY";
    private static String column4 = "REMARKS";
    private static String column5 = "TRANS_DATE";
    private static String column6 = "TOTAL_AMOUNT";
    private static String column7 = "PAID_AMOUNT";
    private static String column8 = "BALANCE_AMOUNT";

    public static final String CreateTable = Name + "(" +
            tblUtility.setDBPrimary(column0, true) +
            tblUtility.setDBInteger(column1, true) +
            tblUtility.setDBStrings(column2, true) +
            tblUtility.setDBInteger(column3, true) +
            tblUtility.setDBStrings(column4, true) +
            tblUtility.setDBStrings(column5, true) +
            tblUtility.setDBDecimal(column6, true) +
            tblUtility.setDBDecimal(column7, true) +
            tblUtility.setDBDecimal(column8, false) +
            ")";

    //endregion

    //region Columns Values Mapping
    static ArrayList<String> columnValueMap() {
        ArrayList<String> columnList = new ArrayList<>();
        columnList.add(column0);
        columnList.add(column1);
        columnList.add(column2);
        columnList.add(column3);
        columnList.add(column4);
        columnList.add(column5);
        columnList.add(column6);
        columnList.add(column7);
        columnList.add(column8);
        return columnList;
    }

    //Column Value
    static ArrayList<String> columnValues(BOTransHeader transHeader) {
        ArrayList<String> valueList = new ArrayList<>();
        valueList.add(tblUtility.getDBInteger(transHeader.getPrimary_key()));
        valueList.add(tblUtility.getDBInteger(transHeader.getPeriodKey()));
        valueList.add(tblUtility.getDBStrings(transHeader.getTableName()));
        valueList.add(tblUtility.getDBInteger(transHeader.getTableLinkKey()));
        valueList.add(tblUtility.getDBStrings(transHeader.getRemarks()));
        valueList.add(tblUtility.getDBStrings(transHeader.getTransDate()));
        valueList.add(tblUtility.getDBDecimal(transHeader.getTotalAmount()));
        valueList.add(tblUtility.getDBDecimal(transHeader.getPaidAmount()));
        valueList.add(tblUtility.getDBDecimal(transHeader.getBalanceAmount()));
        return valueList;
    }
    //endregion

    public static String getInsertQuery(String flag, BOTransHeader transHeader) {

        ArrayList<String> columnList = columnValueMap();
        ArrayList<String> valueList = columnValues(transHeader);
        String insertQry = "";
        if (flag == "I") {
            insertQry = "INSERT INTO " + Name + "(" +
                    tblUtility.getStrWithComma(columnList) +
                    ") VALUES (" +
                    tblUtility.getStrWithComma(valueList) +
                    ")";
        } else {
            insertQry = "UPDATE " + Name + " SET " +
                    tblUtility.getTblUpdate(columnList, valueList) +
                    " WHERE " + columnList.get(0) + " = " + valueList.get(0);
        }
        return insertQry;
    }

    public static BOTransHeader readValue(Cursor res) {
        BOTransHeader newData = new BOTransHeader();
        int i = 0;
        newData.setPrimary_key(res.getLong(i++));
        newData.setPeriodKey(res.getLong(i++));
        newData.setTableName(res.getString(i++));
        newData.setTableLinkKey(res.getLong(i++));

        if (newData.getTableName().contentEquals(tblGroupPersonLink.Name)) {
            ArrayList<BOGroupPersonLink> groupPersonLinks = DBUtility.DTOGetData(tblGroupPersonLink.Name, newData.getTableLinkKey());
            BOGroupPersonLink linkData = groupPersonLinks.get(0);
            BOGroup boGroup = linkData.GroupDetail;
            BOPerson boPerson = linkData.PersonDetail;
            newData.link1Key = boGroup.getPrimary_key();
            newData.link2Key = boPerson.getPrimary_key();
            newData.link1Detail = boGroup.getName();
            newData.link2Detail = boPerson.getFullName();
        }

        newData.setRemarks(res.getString(i++));
        newData.setTransDate(res.getString(i++));
        newData.setTotalAmount(res.getDouble(i++));
        newData.setPaidAmount(res.getDouble(i++));
        newData.setBalanceAmount(res.getDouble(i++));
        return newData;
    }

    public static Cursor DBGetTransHeader(SQLiteDatabase db, String periodKeys, String linkKeys) {
        return db.rawQuery(
                "SELECT " +
                        "TH.ID AS ID," +
                        "TH.PERIOD_KEY," +
                        "TH.TABLE_NAME," +
                        "TH.TABLE_LINK_KEY," +
                        "TH.REMARKS," +
                        "TH.TRANS_DATE," +
                        "TH.TOTAL_AMOUNT," +
                        "TH.PAID_AMOUNT," +
                        "TH.BALANCE_AMOUNT " +
                        "FROM " +
                        "TRANSACTION_HEADER TH" +
                        (periodKeys.length() > 0 ? " WHERE PERIOD_KEY IN (" + periodKeys + ")" : "") +
                        (linkKeys.length() > 0 ? " WHERE TABLE_LINK_KEY IN (" + linkKeys + ")" : "")
                , null);

    }

    public static Cursor DBGenTransHeader(SQLiteDatabase db, String periodkeys) {
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
}
