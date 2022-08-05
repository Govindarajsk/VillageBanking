package com.villagebanking.DBTables;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOLoanDetail;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

public class tblLoanDetail extends tblBase {
    public static final String Name = "LOAN_DETAIL";

    //region Columns 6 =>column1 = "HEADER_KEY"
    private static String column1 = "HEADER_KEY";
    private static String column2 = "PERIOD_KEY";
    private static String column3 = "EMI_NUMBER";
    private static String column4 = "EMI_AMOUNT";
    private static String column5 = "EMI_STATUS";

    private static BOColumn<Long> DBColumn1 = new BOColumn<Long>(DBCLMTYPE.INT, column1);
    private static BOColumn<Long> DBColumn2 = new BOColumn<Long>(DBCLMTYPE.INT, column2);
    private static BOColumn<Long> DBColumn3 = new BOColumn<Long>(DBCLMTYPE.INT, column3);
    private static BOColumn<Double> DBColumn4 = new BOColumn<Double>(DBCLMTYPE.DBL, column4);
    private static BOColumn<String> DBColumn5 = new BOColumn<String>(DBCLMTYPE.TXT, column5);
    //endregion

    static ArrayList<BOColumn> columnList = getColumns();
    public static final String CreateTable = Name + BOColumn.getCreateTableQry(columnList);

    //region Columns List => getColumns
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


    //region Save(flag,data)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String Save(String flag, BOLoanDetail data) {
        String sqlQuery = BOColumn.getInsetUpdateQry(flag, Name, getColumnValueMap(data));
        String b = DBUtility.DBSave(sqlQuery);
        return b;
    }

    static ArrayList<BOColumn> getColumnValueMap(BOLoanDetail data) {
        DBColumn0.setColumnValue(data.getPrimary_key());
        DBColumn1.setColumnValue(data.getLoanHeaderKey());
        DBColumn2.setColumnValue(data.getPeriodInfo().getPrimary_key());
        DBColumn3.setColumnValue(data.getEmiNo());
        DBColumn4.setColumnValue(data.getEmiAmount());
        DBColumn5.setColumnValue(data.getEmiStatus());
        return columnList;
    }
    //endregion

    //region GetList()
    public static ArrayList<BOLoanDetail> GetList(long headerKey) {
        String qryFilter = (headerKey > 0 ? " WHERE " + DBColumn1.getClmName() + "=" + headerKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    static ArrayList<BOLoanDetail> readValue(Cursor res) {
        ArrayList<BOLoanDetail> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOLoanDetail newData = new BOLoanDetail();
            int i=0;
            newData.setPrimary_key(res.getInt(i++));
            newData.setLoanHeaderKey(res.getInt(i++));
            long periodKey= res.getInt(i++);
            BOPeriod data = tblUtility.GetTData(tblPeriod.GetList(periodKey));
            if(data!=null)
            newData.setPeriodInfo(new BOKeyValue(data.getPrimary_key(),data.getActualDate()));
            newData.setEmiNo(res.getInt(i++));
            newData.setEmiAmount(res.getDouble(i++));
            newData.setEmiStatus(res.getString(i++));
            returnList.add(newData);
            res.moveToNext();
        }
        res.close();
        return returnList;
    }
    //endregion
}