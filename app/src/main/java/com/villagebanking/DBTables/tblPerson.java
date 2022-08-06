package com.villagebanking.DBTables;

import android.database.Cursor;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

public class tblPerson extends tblBase {
    public static final String Name = "PERSONS";

    //region Columns 6 =>column1 = "FIRSTNAME"
    private static String column1 = "FIRST_NAME";
    private static String column2 = "LAST_NAME";
    private static String column3 = "PHONE";
    private static String column4 = "REFERENCE_1";
    private static String column5 = "REFERENCE_2";
    private static String column6 = "SCORE";


    private static BOColumn<String> DBColumn1 = new BOColumn<String>(DBCLMTYPE.TXT, column1);
    private static BOColumn<String> DBColumn2 = new BOColumn<String>(DBCLMTYPE.TXT, column2);
    private static BOColumn<Long> DBColumn3 = new BOColumn<Long>(DBCLMTYPE.INT, column3);
    private static BOColumn<Long> DBColumn4 = new BOColumn<Long>(DBCLMTYPE.INT, column4);
    private static BOColumn<Long> DBColumn5 = new BOColumn<Long>(DBCLMTYPE.INT, column5);
    private static BOColumn<Long> DBColumn6 = new BOColumn<Long>(DBCLMTYPE.INT, column6);
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
    public static String Save(String flag, BOPerson person) {
        String sqlQuery = BOColumn.getInsetUpdateQry(flag, Name, getColumnValueMap(person));
        return DBUtility.DBSave(sqlQuery);
    }

    static ArrayList<BOColumn> getColumnValueMap(BOPerson data) {
        DBColumn1.setColumnValue(data.getFName());
        DBColumn2.setColumnValue(data.getLName());
        DBColumn3.setColumnValue(data.getMobile());
        DBColumn4.setColumnValue(data.getReference1().getPrimary_key());
        DBColumn5.setColumnValue(data.getReference2().getPrimary_key());
        DBColumn6.setColumnValue(data.getScore());
        return columnList;
    }

    //endregion

    //region GetList => primaryKey
    public static ArrayList<BOPerson> GetList(long primaryKey) {
        String qryFilter = (primaryKey > 0 ? " WHERE " + DBColumn0.getClmName() + "=" + primaryKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    static ArrayList<BOPerson> readValue(Cursor res) {
        ArrayList<BOPerson> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOPerson newData = new BOPerson();
            int i=0;
            newData.setPrimary_key(res.getLong(i++));
            newData.setFName(res.getString(i++));
            newData.setLName(res.getString(i++));
            newData.setMobile(res.getLong(i++));
            newData.getReference1().setPrimary_key(res.getLong(i++));
            newData.getReference2().setPrimary_key(res.getLong(i++));
            newData.setScore(res.getLong(i++));
            returnList.add(newData);
            res.moveToNext();
        }
        res.close();
        return returnList;
    }
    //endregion
}
