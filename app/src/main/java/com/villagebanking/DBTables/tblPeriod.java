package com.villagebanking.DBTables;

import android.database.Cursor;
import android.os.Build;
import android.text.method.NumberKeyListener;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

public class tblPeriod extends tblBase {
    public static final String Name = "PERIODS";

    //region Columns 6 =>column1 = "PERIOD_TYPE"
    private static String column1 = "PERIOD_TYPE";
    private static String column2 = "PERIOD_NAME";
    private static String column3 = "ACTUAL_DATE";
    private static String column4 = "DATE_VALUE";
    private static String column5 = "PERIOD_REMARKS";
    private static String column6 = "PERIOD_STATUS";


    private static BOColumn<Long> DBColumn1 = new BOColumn<Long>(DBCLMTYPE.INT, column1);
    private static BOColumn<String> DBColumn2 = new BOColumn<String>(DBCLMTYPE.TXT, column2);
    private static BOColumn<String> DBColumn3 = new BOColumn<String>(DBCLMTYPE.TXT, column3);
    private static BOColumn<Long> DBColumn4 = new BOColumn<Long>(DBCLMTYPE.INT, column4);
    private static BOColumn<String> DBColumn5 = new BOColumn<String>(DBCLMTYPE.TXT, column5);
    private static BOColumn<String> DBColumn6 = new BOColumn<String>(DBCLMTYPE.TXT, column6);
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
        return columnList;
    }
    //endregion

    public static final String CreateTable = Name + BOColumn.getCreateTableQry(columnList);

    //region Save(flag,data)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String Save(String flag, BOPeriod boPeriod) {
        String sqlQuery = BOColumn.getInsetUpdateQry(flag, Name, getColumnValueMap(boPeriod));
        return DBUtility.DBSave(sqlQuery);
    }

    static ArrayList<BOColumn> getColumnValueMap(BOPeriod boPeriod) {
        DBColumn1.setColumnValue(boPeriod.getPeriodType());
        DBColumn2.setColumnValue(boPeriod.getPeriodName());
        DBColumn3.setColumnValue(boPeriod.getActualDate());
        DBColumn4.setColumnValue(boPeriod.getPeriodValue());
        DBColumn5.setColumnValue(boPeriod.getPeriodRemarks());
        DBColumn6.setColumnValue(boPeriod.getPeriodStatus());
        return columnList;
    }
    //endregion

    //region GetList => primaryKey
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<BOPeriod> GetList(long primaryKey) {
        String qryFilter = (primaryKey > 0 ? " WHERE " + DBColumn0.getClmName() + "=" + primaryKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static ArrayList<BOPeriod> readValue(Cursor res) {
        ArrayList<BOPeriod> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOPeriod newData = new BOPeriod();
            newData.setPrimary_key(res.getInt(0));
            newData.setPeriodType(res.getInt(1));
            newData.setPeriodName(res.getString(2));
            newData.setActualDate(res.getString(3));
            newData.setPeriodValue(res.getLong(4));
            newData.setPeriodRemarks(res.getString(5));
            newData.setPeriodStatus(res.getString(6));
            returnList.add(newData);
            res.moveToNext();
        }
        res.close();

        returnList.sort((t1, t2) -> Long.toString(t1.getPeriodValue()).
                compareTo(Long.toString(t2.getPeriodValue())));
        return returnList;
    }
    //endregion

    //region Special GetList
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<BOPeriod> GetViewList(String periodType) {
        String qryFilter = (periodType.length() > 0 ? " WHERE " + DBColumn1.getClmName() + "=" + periodType : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<BOPeriod> getNextNPeriods(long primaryKey, int N) {
        String qryFilter = "";
        BOPeriod data = tblUtility.GetTData(GetList(primaryKey));
        if (data != null) {
            Long type = data.getPeriodType();
            Long date = data.getPeriodValue();

            qryFilter = " WHERE " + DBColumn1.getClmName() + "=" + type + " AND " +
                    (N > 0 ? ("DATE_VALUE > " + date + " LIMIT " + N) : "DATE_VALUE <= " + date);
        }
        String qry = BOColumn.getListQry(Name, columnList, qryFilter);
        Cursor result = DBUtility.GetDBList(qry);
        return readValue(result);
    }
    //endregion
}
