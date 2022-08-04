package com.villagebanking.DBTables;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

public class tblGroup extends tblBase {
    public static final String Name = "GROUPS";

    //region Columns 5 =>column1 = "NAME"
    private static String column1 = "NAME";
    private static String column2 = "NO_OF_PERSON";
    private static String column3 = "AMOUNT";
    private static String column4 = "START_PERIOD_KEY";
    private static String column5 = "BOND_CHARGE";


    private static BOColumn<String> DBColumn1 = new BOColumn<String>(DBCLMTYPE.TXT, column1);
    private static BOColumn<Integer> DBColumn2 = new BOColumn<Integer>(DBCLMTYPE.INT, column2);
    private static BOColumn<Double> DBColumn3 = new BOColumn<Double>(DBCLMTYPE.DBL, column3);
    private static BOColumn<String> DBColumn4 = new BOColumn<String>(DBCLMTYPE.TXT, column4);
    private static BOColumn<Double> DBColumn5 = new BOColumn<Double>(DBCLMTYPE.DBL, column5);
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
    public static String Save(String flag, BOGroup data) {
        String sqlQuery = BOColumn.getInsetUpdateQry(flag, Name, getColumnValueMap(data));
        return DBUtility.DBSave(sqlQuery);
    }

    static ArrayList<BOColumn> getColumnValueMap(BOGroup data) {
        DBColumn0.setColumnValue(data.getPrimary_key());
        DBColumn1.setColumnValue(data.getName());
        DBColumn2.setColumnValue(data.getNoOfPerson());
        DBColumn3.setColumnValue(data.getAmount());
        DBColumn4.setColumnValue(String.valueOf(data.getPeriodDetail().getPrimary_key()));
        DBColumn5.setColumnValue(data.getBondCharge());
        return columnList;
    }
    //endregion

    //region GetList()
    public static ArrayList<BOGroup> GetList(long primaryKey) {
        String qryFilter = (primaryKey > 0 ? " WHERE " + DBColumn0.getClmName() + "=" + primaryKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    static ArrayList<BOGroup> readValue(Cursor res) {
        ArrayList<BOGroup> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOGroup newData = new BOGroup();
            newData.setPrimary_key(res.getInt(0));
            newData.setName(res.getString(1));
            newData.setNoOfPerson(res.getInt(2));
            newData.setAmount(res.getDouble(3));

            long period_key = res.getLong(4);
            ArrayList<BOPeriod> periodList = tblPeriod.GetList(period_key);
            if (periodList.size() > 0) {
                BOPeriod item = (BOPeriod) periodList.get(0);
                newData.setPeriodDetail(new BOKeyValue(item.getPrimary_key(), item.getActualDate() + "-" + item.getPeriodName()));
            }

            newData.setType(String.valueOf(period_key));
            newData.setBondCharge(res.getDouble(5));

            //newData.setStatus(res.getString(6));
            returnList.add(newData);
            res.moveToNext();
        }
        res.close();
        return returnList;
    }
    //endregion
}
