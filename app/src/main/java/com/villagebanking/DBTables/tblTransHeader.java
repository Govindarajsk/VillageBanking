package com.villagebanking.DBTables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOLoanDetail;
import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblUtility;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

import kotlin.text.UStringsKt;

public class tblTransHeader extends tblBase {

    //region TRANSACTION_HEADER
    public static final String Name = "TRANSACTION_HEADER";

    static String column1 = "PERIOD_KEY";
    static String column2 = "TABLE_NAME";
    static String column3 = "TABLE_LINK_KEY";
    static String column4 = "REMARKS";
    static String column5 = "TRANS_DATE";
    static String column6 = "TOTAL_AMOUNT";
    static String column7 = "PAID_AMOUNT";
    static String column8 = "BALANCE_AMOUNT";
    static String column9 = "PARENT_KEY";

    static BOColumn<Long> DBColumn1 = new BOColumn<Long>(DBCLMTYPE.INT, column1);
    static BOColumn<String> DBColumn2 = new BOColumn<String>(DBCLMTYPE.TXT, column2);
    static BOColumn<Long> DBColumn3 = new BOColumn<Long>(DBCLMTYPE.INT, column3);
    static BOColumn<String> DBColumn4 = new BOColumn<String>(DBCLMTYPE.TXT, column4);
    static BOColumn<String> DBColumn5 = new BOColumn<String>(DBCLMTYPE.TXT, column5);
    static BOColumn<Double> DBColumn6 = new BOColumn<Double>(DBCLMTYPE.DBL, column6);
    static BOColumn<Double> DBColumn7 = new BOColumn<Double>(DBCLMTYPE.DBL, column7);
    static BOColumn<Double> DBColumn8 = new BOColumn<Double>(DBCLMTYPE.DBL, column8);
    static BOColumn<Long> DBColumn9 = new BOColumn<Long>(DBCLMTYPE.INT, column9);

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
        columnList.add(DBColumn9);
        return columnList;
    }
    //endregion

    //region String <= Save(flag,data)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String Save(String flag, BOTransHeader data) {
        columnList = getColumnValueMap(data);
        String sqlQuery = BOColumn.getInsetUpdateQry(flag, Name, columnList);
        String returnVal = DBUtility.DBSave(sqlQuery);
        return returnVal;
    }

    static ArrayList<BOColumn> getColumnValueMap(BOTransHeader data) {
        DBColumn0.setColumnValue(data.getPrimary_key());
        DBColumn1.setColumnValue(data.getPeriodKey());
        DBColumn2.setColumnValue(data.getTableName());
        DBColumn3.setColumnValue(data.getTableLinkKey());
        DBColumn4.setColumnValue(data.getRemarks());
        DBColumn5.setColumnValue(data.getTransDate());
        DBColumn6.setColumnValue(data.getTotalAmount());
        DBColumn7.setColumnValue(data.getPaidAmount());
        DBColumn8.setColumnValue(data.getBalanceAmount());
        DBColumn9.setColumnValue(data.getParentKey());
        return columnList;
    }
    //endregion

    //region GetList => primaryKey
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<BOTransHeader> GetList(long primaryKey) {
        String qryFilter = (primaryKey > 0 ? " WHERE " + DBColumn0.getClmName() + "=" + primaryKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<BOTransHeader> GetViewList(String tableName, String periodKeys, String personKeys) {
        String qry = "";
        if (tableName.equals(tblGroupPersonLink.Name)) {
            qry = getGPLinkQry(periodKeys);
        } else if (tableName.equals(tblLoanHeader.Name)) {
            qry = getLoanHeaderQry(periodKeys);
        } else if (tableName.equals(tblTransHeader.Name)) {
            qry = getTransHeaderQry(periodKeys, personKeys);
        }
        Cursor result = DBUtility.GetDBList(qry);
        return readValue(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<BOTransHeader> readValue(Cursor res) {
        ArrayList<BOTransHeader> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOTransHeader newData = new BOTransHeader();
            int i = 0;
            long primaryKey = res.getLong(i++);
            newData.setPrimary_key(primaryKey);
            ArrayList<BOTransDetail> transDetails = tblTransDetail.GetDetailViewList(primaryKey, 0);
            newData.setTransDetails(transDetails);
            newData.setPeriodKey(res.getLong(i++));
            newData.setTableName(res.getString(i++));
            newData.setTableLinkKey(res.getLong(i++));
            if (newData.getTableName().contentEquals(tblGroupPersonLink.Name)) {
                ArrayList<BOGroupPersonLink> groupPersonLinks = tblGroupPersonLink.GetList(newData.getTableLinkKey());
                if (groupPersonLinks.size() > 0) {
                    BOGroupPersonLink linkData = groupPersonLinks.get(0);
                    BOKeyValue boGroup = linkData.getGroup();
                    BOKeyValue boPerson = linkData.getPerson();
                    newData.setLinkDetail1(boGroup);
                    newData.setLinkDetail2(boPerson);
                }
            } else if (newData.getTableName().contentEquals(tblLoanDetail.Name)) {
                ArrayList<BOLoanDetail> loanDetails = tblLoanDetail.GetViewList(newData.getTableLinkKey());
                if (loanDetails.size() > 0) {
                    BOLoanHeader linkData = loanDetails.get(0);
                    BOKeyValue boGroup = linkData.getGroup();
                    BOKeyValue boPerson = linkData.getPerson();
                    newData.setLinkDetail1(boGroup);
                    newData.setLinkDetail2(boPerson);
                }
            }
            newData.setRemarks(res.getString(i++));
            newData.setTransDate(res.getString(i++));
            Double totalAmount = res.getDouble(i++);

            Double paidAmount = transDetails.stream().mapToDouble(x -> x.getAmount()).sum();
            if (transDetails.size() > 0)
                newData.settD_Period_Key(transDetails.stream().findFirst().get().getPeriodKey());
            Double balAmount = totalAmount - paidAmount;

            newData.setTotalAmount(totalAmount);
            newData.setPaidAmount(paidAmount);
            newData.setBalanceAmount(balAmount);

            returnList.add(newData);
            res.moveToNext();
        }
        res.close();
        return returnList;
    }
    //endregion

    //region SQL Query SQL
    public static final String CreateTable = Name + BOColumn.getCreateTableQry(columnList);

    public static String getTransHeaderQry(String periodKeys, String personKeys) {
        String qryFilter = "";
        if (periodKeys.length() > 0)
            qryFilter += " AND TH.PERIOD_KEY IN (" + periodKeys + ")";
        if (personKeys.length() > 0)
            qryFilter += (qryFilter.length() > 0 ? " AND " : "") + "GPL.PERSON_KEY IN (" + personKeys + ")";

        qryFilter = " WHERE TH.TABLE_NAME = " + "'" + tblGroupPersonLink.Name + "'" + qryFilter;

        String qryFilter1 = "";
        if (periodKeys.length() > 0)
            qryFilter1 += " AND TH.PERIOD_KEY IN (" + periodKeys + ")";
        if (personKeys.length() > 0)
            qryFilter1 += (qryFilter1.length() > 0 ? " AND " : "") + "LH.PERSON_KEY IN (" + personKeys + ")";

        qryFilter1 = " WHERE TH.TABLE_NAME = " + "'" + tblLoanDetail.Name + "'" + qryFilter1;

        String qry =
                "SELECT " +
                        "TH.ID AS ID,"
                        + "TH.PERIOD_KEY AS PERIOD_KEY,"
                        + "TH.TABLE_NAME AS TABLE_NAME,"
                        + "TH.TABLE_LINK_KEY AS TABLE_LINK_KEY,"
                        + "TH.REMARKS AS REMARKS,"
                        + "TH.TRANS_DATE AS TRANS_DATE,"
                        + "TH.TOTAL_AMOUNT AS TOTAL_AMOUNT,"
                        + "TH.PAID_AMOUNT AS PAID_AMOUNT,"
                        + "TH.BALANCE_AMOUNT AS BALANCE_AMOUNT,"
                        + "GPL.GROUP_KEY AS GROUP_KEY,"
                        + "GPL.PERSON_KEY AS PERSON_KEY,"
                        + "TH.PARENT_KEY AS PARENT_KEY"
                        + " FROM "
                        + "TRANSACTION_HEADER TH JOIN "
                        + "GROUP_PERSON_LINK GPL ON TH.TABLE_LINK_KEY=GPL.ID"
                        + qryFilter;

        qry += " UNION  SELECT " +
                "TH.ID AS ID,"
                + "TH.PERIOD_KEY AS PERIOD_KEY,"
                + "TH.TABLE_NAME AS TABLE_NAME,"
                + "TH.TABLE_LINK_KEY AS TABLE_LINK_KEY,"
                + "TH.REMARKS AS REMARKS,"
                + "TH.TRANS_DATE AS TRANS_DATE,"
                + "TH.TOTAL_AMOUNT AS TOTAL_AMOUNT,"
                + "TH.PAID_AMOUNT AS PAID_AMOUNT,"
                + "TH.BALANCE_AMOUNT AS BALANCE_AMOUNT,"
                + "LH.GROUP_KEY AS GROUP_KEY,"
                + "LH.PERSON_KEY AS PERSON_KEY,"
                + "TH.PARENT_KEY AS PARENT_KEY"
                + " FROM "
                + "TRANSACTION_HEADER TH JOIN "
                + tblLoanDetail.Name + " LD ON TH.TABLE_LINK_KEY=LD.ID JOIN "
                + tblLoanHeader.Name + " LH ON LH.ID = LD.HEADER_KEY"
                + qryFilter1;

        return qry;
    }

    public static String getGPLinkQry(String periodkeys) {
        String qryFilter = (periodkeys.length() > 0 ? " WHERE G.START_PERIOD_KEY IN (" + periodkeys + ")" : "");
        String qry =
                "SELECT " +
                        "TH.ID AS ID,"
                        + "G.START_PERIOD_KEY AS PERIOD_KEY,"
                        + "'GROUP_PERSON_LINK' AS TABLE_NAME,"
                        + "GPL.ID AS TABLE_LINK_KEY,"
                        + "G.NAME AS REMARKS,"
                        + "'' AS TRANS_DATE,"
                        + "G.AMOUNT AS TOTAL_AMOUNT,"
                        + "0 AS PAID_AMOUNT,"
                        + "G.AMOUNT AS BALANCE_AMOUNT,"
                        + "GPL.GROUP_KEY AS GROUP_KEY,"
                        + "GPL.PERSON_KEY AS PERSON_KEY,"
                        + "TH.PARENT_KEY AS PARENT_KEY,"
                        + "0 AS TD_PERIOD_KEY"
                        + " FROM "
                        + "GROUPS G  JOIN "
                        + "GROUP_PERSON_LINK GPL ON G.ID=GPL.GROUP_KEY LEFT JOIN "
                        + "TRANSACTION_HEADER TH ON TH.ID = GPL.ID"
                        + qryFilter;
        return qry;
    }

    public static String getLoanHeaderQry(String periodkeys) {
        String qryFilter = (periodkeys.length() > 0 ? " WHERE LD.PERIOD_KEY IN (" + periodkeys + ")" : "");
        String qry = "SELECT " +
                "0 AS ID,"
                + "LD.PERIOD_KEY AS PERIOD_KEY,"
                + "'" + tblLoanDetail.Name + "' AS TABLE_NAME,"
                + "LD.ID AS TABLE_LINK_KEY,"
                + "'' AS REMARKS,"
                + "'' AS TRANS_DATE,"
                + "LD.EMI_AMOUNT AS TOTAL_AMOUNT,"
                + "0 AS PAID_AMOUNT,"
                + "LD.EMI_AMOUNT AS BALANCE_AMOUNT,"
                + "LH.GROUP_KEY AS GROUP_KEY,"
                + "LH.PERSON_KEY AS PERSON_KEY,"
                + "0 AS PARENT_KEY,"
                + "0 AS TD_PERIOD_KEY"
                + " FROM "
                + tblLoanDetail.Name + " LD JOIN "
                + tblLoanHeader.Name + " LH ON LH.ID=LD.HEADER_KEY" + qryFilter;
        return qry;
    }
    //endregion
}
