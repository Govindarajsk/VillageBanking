package com.villagebanking.DBTables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblUtility;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

import kotlin.text.UStringsKt;

public class tblTransHeader extends tblBase {

    //region TRANSACTION_HEADER
    public static final String Name = "TRANSACTION_HEADER";

    private static String column1 = "PERIOD_KEY";
    private static String column2 = "TABLE_NAME";
    private static String column3 = "TABLE_LINK_KEY";
    private static String column4 = "REMARKS";
    private static String column5 = "TRANS_DATE";
    private static String column6 = "TOTAL_AMOUNT";
    private static String column7 = "PAID_AMOUNT";
    private static String column8 = "BALANCE_AMOUNT";

    private static BOColumn<Long> DBColumn1 = new BOColumn<Long>(DBCLMTYPE.INT, column1);
    private static BOColumn<String> DBColumn2 = new BOColumn<String>(DBCLMTYPE.TXT, column2);
    private static BOColumn<Long> DBColumn3 = new BOColumn<Long>(DBCLMTYPE.INT, column3);
    private static BOColumn<String> DBColumn4 = new BOColumn<String>(DBCLMTYPE.TXT, column4);
    private static BOColumn<String> DBColumn5 = new BOColumn<String>(DBCLMTYPE.TXT, column5);
    private static BOColumn<Double> DBColumn6 = new BOColumn<Double>(DBCLMTYPE.DBL, column6);
    private static BOColumn<Double> DBColumn7 = new BOColumn<Double>(DBCLMTYPE.DBL, column7);
    private static BOColumn<Double> DBColumn8 = new BOColumn<Double>(DBCLMTYPE.DBL, column8);

    //endregion

    //region Columns List => getColumns
    static ArrayList<BOColumn> columnList = getColumns();

    static ArrayList<BOColumn> getColumns() {
        if (columnList == null) columnList = new ArrayList<>();
        columnList.add(DBColumn0);
        columnList.add(DBColumn1);
        columnList.add(DBColumn2);
        columnList.add(DBColumn3);
        columnList.add(DBColumn4);
        columnList.add(DBColumn5);
        columnList.add(DBColumn6);
        columnList.add(DBColumn7);
        columnList.add(DBColumn8);
        return columnList;
    }
    //endregion

    public static final String CreateTable = Name + BOColumn.getCreateTableQry(columnList);

    //region String <= Save(flag,data)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String Save(String flag, BOTransHeader data) {
        String sqlQuery = BOColumn.getInsetUpdateQry(flag, Name, getColumnValueMap(data));
        String returnVal = DBUtility.DBSave(sqlQuery);
        return returnVal;
    }

    static ArrayList<BOColumn> getColumnValueMap(BOTransHeader data) {
        DBColumn1.setColumnValue(data.getPeriodKey());
        DBColumn2.setColumnValue(data.getTableName());
        DBColumn3.setColumnValue(data.getTableLinkKey());
        DBColumn4.setColumnValue(data.getRemarks());
        DBColumn5.setColumnValue(data.getTransDate());
        DBColumn6.setColumnValue(data.getTotalAmount());
        DBColumn7.setColumnValue(data.getPaidAmount());
        DBColumn8.setColumnValue(data.getBalanceAmount());
        return columnList;
    }
    //endregion

    //region GetList => primaryKey
    public static ArrayList<BOTransHeader> GetList(long primaryKey) {
        String qryFilter = (primaryKey > 0 ? " WHERE " + DBColumn0.getClmName() + "=" + primaryKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    public static ArrayList<BOTransHeader> readValue(Cursor res) {
        ArrayList<BOTransHeader> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOTransHeader newData = new BOTransHeader();
            int i = 0;
            newData.setPrimary_key(res.getLong(i++));
            newData.setPeriodKey(res.getLong(i++));
            newData.setTableName(res.getString(i++));
            newData.setTableLinkKey(res.getLong(i++));

            if (newData.getTableName().contentEquals(tblGroupPersonLink.Name)) {
                ArrayList<BOGroupPersonLink> groupPersonLinks = tblGroupPersonLink.GetList(newData.getTableLinkKey());
                if (groupPersonLinks.size() > 0) {
                    BOGroupPersonLink linkData = groupPersonLinks.get(0);
                    BOKeyValue boGroup = linkData.GroupDetail;
                    BOKeyValue boPerson = linkData.PersonDetail;
                    newData.setLinkDetail1(boGroup);
                    newData.setLinkDetail2(boPerson);
                }
            } else if (newData.getTableName().contentEquals(tblLoanHeader.Name)) {
                ArrayList<BOLoanHeader> loanHeaders = tblLoanHeader.GetList(newData.getTableLinkKey(), 0);
                if (loanHeaders.size() > 0) {
                    BOLoanHeader linkData = loanHeaders.get(0);
                    BOKeyValue boGroup = linkData.getGroupKey();
                    BOKeyValue boPerson = linkData.getPersonKey();
                    newData.setLinkDetail1(boGroup);
                    newData.setLinkDetail2(boPerson);
                }
            }

            newData.setRemarks(res.getString(i++));
            newData.setTransDate(res.getString(i++));
            newData.setTotalAmount(res.getDouble(i++));
            newData.setPaidAmount(res.getDouble(i++));
            newData.setBalanceAmount(res.getDouble(i++));

            returnList.add(newData);
            res.moveToNext();
        }
        res.close();
        return returnList;
    }
    //endregion

    //region Special get List
    public static ArrayList<BOTransHeader> GetViewList(String tableName, String periodKeys, String linkKeys) {
        String qry = "";
        if (tableName.equals(tblGroupPersonLink.Name)) {
            qry = DBGenTransHeader(periodKeys);
        } else if (tableName.equals(tblLoanHeader.Name)) {
            qry = DBGenLoanTransHeader(periodKeys);
        } else if (tableName.equals(tblTransHeader.Name)) {
            qry = DBGetTransHeader(periodKeys, linkKeys);
        }
        Cursor result = DBUtility.GetDBList(qry);
        return readValue(result);
    }

    public static String DBGetTransHeader(String periodKeys, String linkKeys) {
        String qryFilter =
                (periodKeys.length() > 0 ? " WHERE PERIOD_KEY IN (" + periodKeys + ")" : "") +
                        (linkKeys.length() > 0 ? " WHERE TABLE_LINK_KEY IN (" + linkKeys + ")" : "");
        String qry =
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
                        "TRANSACTION_HEADER TH" + qryFilter;
        return qry;
    }

    public static String DBGenTransHeader(String periodkeys) {
        String qryFilter = (periodkeys.length() > 0 ? " WHERE G.START_PERIOD_KEY IN (" + periodkeys + ")" : "");
        String qry =
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
                        + "GROUP_PERSON_LINK GPL ON G.ID=GPL.GROUP_KEY" + qryFilter;
        return qry;
    }

    public static String DBGenLoanTransHeader(String periodkeys) {
        String qryFilter = (periodkeys.length() > 0 ? " WHERE G.START_PERIOD_KEY IN (" + periodkeys + ")" : "");
        String qry = "SELECT " +
                "0 AS ID,"
                + "G.START_PERIOD_KEY AS PERIOD_KEY,"
                + "'" + tblLoanHeader.Name + "' AS TABLE_NAME,"
                + "LH.ID AS TABLE_LINK_KEY,"
                + "G.NAME AS REMARKS,"
                + "'' AS TRANS_DATE,"
                + "LH.LOAN_AMOUNT AS TOTAL_AMOUNT,"
                + "0 AS PAID_AMOUNT,"
                + "LH.LOAN_AMOUNT AS BALANCE_AMOUNT "
                + "FROM "
                + "GROUPS G JOIN "
                + tblLoanHeader.Name + " LH ON G.ID=LH.GROUP_KEY" + qryFilter;
        return qry;
    }
    //endregion
}
