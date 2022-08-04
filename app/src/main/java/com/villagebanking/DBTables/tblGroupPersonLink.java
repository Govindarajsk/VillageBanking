package com.villagebanking.DBTables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;


public class tblGroupPersonLink extends tblBase {

    public static final String Name = "GROUP_PERSON_LINK";

    //region GROUP_PERSON_LINK
    private static String column1 = "GROUP_KEY";
    private static String column2 = "PERSON_KEY";
    private static String column3 = "ORDER_BY";
    private static String column4 = "PERSON_ROLE";

    private static BOColumn<Long> DBColumn1 = new BOColumn<Long>(DBCLMTYPE.INT, column1);
    private static BOColumn<Long> DBColumn2 = new BOColumn<Long>(DBCLMTYPE.INT, column2);
    private static BOColumn<Integer> DBColumn3 = new BOColumn<Integer>(DBCLMTYPE.INT, column3);
    private static BOColumn<String> DBColumn4 = new BOColumn<String>(DBCLMTYPE.TXT, column4);

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
        return columnList;
    }
    //endregion

    public static final String CreateTable = Name + BOColumn.getCreateTableQry(columnList);

    //region Save(flag,data)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String Save(String flag, BOGroupPersonLink data) {
        String sqlQuery = BOColumn.getInsetUpdateQry(flag, Name, getColumnValueMap(data));
        return DBUtility.DBSave(sqlQuery);
    }

    static ArrayList<BOColumn> getColumnValueMap(BOGroupPersonLink data) {
        DBColumn1.setColumnValue(data.getGroup_Key());
        DBColumn2.setColumnValue(data.getPerson_Key());
        DBColumn3.setColumnValue(data.getOrderBy());
        DBColumn4.setColumnValue(data.getPerson_role());
        return columnList;
    }
    //endregion

    //region GetList => primaryKey
    public static ArrayList<BOGroupPersonLink> GetList(long primaryKey) {
        String qryFilter = (primaryKey > 0 ? " WHERE " + DBColumn0.getClmName() + "=" + primaryKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    static ArrayList<BOGroupPersonLink> readValue(Cursor res) {
        ArrayList<BOGroupPersonLink> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOGroupPersonLink newData = new BOGroupPersonLink();
            newData.setPrimary_key(res.getLong(0));
            newData.setGroup_Key(res.getLong(1));
            newData.setPerson_Key(res.getLong(2));

            BOGroup group = (BOGroup) DBUtility.GetData(tblGroup.Name, newData.getGroup_Key());
            if (group != null)
                newData.GroupDetail = new BOKeyValue(group.getPrimary_key(), group.getName());

            BOPerson person = (BOPerson) DBUtility.GetData(tblPerson.Name, newData.getPerson_Key());
            if (person != null)
                newData.PersonDetail = new BOKeyValue(person.getPrimary_key(), person.getFullName());

            newData.setPerson_role(res.getString(3));
            returnList.add(newData);
            res.moveToNext();
        }
        res.close();
        return returnList;
    }
    //endregion

    //region Special get List
    public static ArrayList<BOGroupPersonLink> GetViewList(long groupKey) {
        String qryFilter = (groupKey > 0 ? " WHERE " + DBColumn1.getClmName() + "=" + groupKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }
    //endregion
}
