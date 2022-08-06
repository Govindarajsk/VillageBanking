package com.villagebanking.DBTables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

public class tblTransDetail extends tblBase {

    public static final String Name = "TRANSACTION_DETAIL";
    //region TRANSACTION_DETAIL
    private static String column1 = "PARENT_KEY";
    private static String column2 = "HEADER_KEY";
    private static String column3 = "DATE";
    private static String column4 = "AMOUNT";
    private static String column5 = "REMARKS";


    private static BOColumn<Long> DBColumn1 = new BOColumn<Long>(DBCLMTYPE.INT, column1);
    private static BOColumn<Long> DBColumn2 = new BOColumn<Long>(DBCLMTYPE.INT, column2);
    private static BOColumn<String> DBColumn3 = new BOColumn<String>(DBCLMTYPE.TXT, column3);
    private static BOColumn<Double> DBColumn4 = new BOColumn<Double>(DBCLMTYPE.DBL, column4);
    private static BOColumn<String> DBColumn5 = new BOColumn<String>(DBCLMTYPE.TXT, column5);

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
        return columnList;
    }
    //endregion

    public static final String CreateTable = Name + BOColumn.getCreateTableQry(columnList);

    //region Save(flag,data)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String Save(String flag, BOTransDetail data) {
        String sqlQuery = BOColumn.getInsetUpdateQry(flag, Name, getColumnValueMap(data));
        return DBUtility.DBSave(sqlQuery);
    }

    static ArrayList<BOColumn> getColumnValueMap(BOTransDetail data) {
        DBColumn1.setColumnValue(data.getParentKey());
        DBColumn2.setColumnValue(data.getHeaderKey());
        DBColumn3.setColumnValue(data.getTransDate());
        DBColumn4.setColumnValue(data.getAmount());
        DBColumn5.setColumnValue(data.getRemarks());
        return columnList;
    }
    //endregion

    //region GetList()
    public static ArrayList<BOTransDetail> GetList(long primaryKey) {
        String qryFilter = (primaryKey > 0 ? " WHERE " + DBColumn0.getClmName() + "=" + primaryKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    static ArrayList<BOTransDetail> readValue(Cursor res, long... keys) {
        long key1 = keys.length > 0 ? keys[0] : 0;

        ArrayList<BOTransDetail> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOTransDetail newData = new BOTransDetail();
            int i = 0;
            newData.setPrimary_key(res.getLong(i++));
            newData.setParentKey(res.getLong(i++));
            newData.setHeaderKey(res.getLong(i++));
            newData.setTransDate(res.getString(i++));
            newData.setAmount(res.getDouble(i++));
            newData.setRemarks(res.getString(i++));
            if (res.getColumnCount() > 6) {
                newData.setPeriodKey(res.getLong(i++));
                String tableName = res.getString(i++);
                long tblLinkKey = res.getLong(i++);
                newData.setTableLinkKey(tblLinkKey);
                newData.setTableName(tableName);
                String remarks = "";
                if (tableName.equals(tblGroupPersonLink.Name)) {
                    BOGroupPersonLink data = tblUtility.GetTData(tblGroupPersonLink.GetList(tblLinkKey));
                    if (data != null) {
                        remarks += (data.getPerson() != null ?
                                data.getPerson().getDisplayValue() : "");
                        BOKeyValue group = data.getGroup();
                        if (key1 > 0 && (group != null && group.getPrimary_key() != key1)) {
                            res.moveToNext();
                            continue;
                        }
                    }
                } else if (tableName.equals(tblLoanHeader.Name)) {
                    BOLoanHeader data = tblUtility.GetTData(tblLoanHeader.GetList(tblLinkKey));
                    if (data != null) {
                        remarks += (data.getPerson() != null ?
                                data.getPerson().getDisplayValue() : "");
                        BOKeyValue group = data.getGroup();
                        if (key1 > 0 && (group != null && group.getPrimary_key() != key1)) {
                            res.moveToNext();
                            continue;
                        }
                    }
                }

                newData.setRemarks(res.getString(i++));
                newData.setTotalAmount(res.getDouble(i++));
                newData.setPaidAmount(res.getDouble(i++));
                newData.setBalanceAmount(res.getDouble(i++));
                newData.setRemarks(remarks);
            }
            returnList.add(newData);
            res.moveToNext();
        }
        res.close();
        return returnList;
    }

    //endregion
    //region special qry
    public static ArrayList<BOTransDetail> GetDetailViewList(long headerKey, long groupKey) {
        Cursor result = DBUtility.GetDBList(detailViewQry(headerKey));
        return readValue(result, groupKey);
    }

    static String detailViewQry(long headerKey) {
        String filter = headerKey > 0 ? " WHERE TH.ID IN (" + headerKey + ")" : "";
        String qry = "SELECT " +
                "TD.ID AS ID," +
                "TD.PARENT_KEY," +
                "TD.HEADER_KEY," +
                "TD.DATE," +
                "TD.AMOUNT AS AMOUNT," +
                "TD.REMARKS," +
                "TH.PERIOD_KEY," +
                "TH.TABLE_NAME," +
                "TH.TABLE_LINK_KEY," +
                "TH.REMARKS," +
                "TH.TOTAL_AMOUNT," +
                "TH.PAID_AMOUNT," +
                "TH.BALANCE_AMOUNT " +
                "FROM " +
                "TRANSACTION_HEADER TH JOIN " +
                "TRANSACTION_DETAIL TD ON TD.HEADER_KEY=TH.ID " + filter;
        return qry;

    }
    //endregion
}
