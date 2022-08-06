package com.villagebanking.DBTables;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOLoanDetail;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<BOLoanDetail> GetList(long headerKey) {
        String qryFilter = (headerKey > 0 ? " WHERE " + DBColumn1.getClmName() + "=" + headerKey : "");
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    static ArrayList<BOLoanDetail> readValue(Cursor res) {
        ArrayList<BOLoanDetail> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOLoanDetail newData = new BOLoanDetail();
            int i = 0;
            newData.setPrimary_key(res.getInt(i++));
            newData.setLoanHeaderKey(res.getInt(i++));
            long periodKey = res.getInt(i++);
            BOPeriod data = tblUtility.GetTData(tblPeriod.GetList(periodKey));
            if (data != null)
                newData.setPeriodInfo(new BOKeyValue(data.getPrimary_key(), data.getActualDate()));
            newData.setEmiNo(res.getInt(i++));
            newData.setEmiAmount(res.getDouble(i++));
            newData.setEmiStatus(res.getString(i++));

            if (res.getColumnCount() > 6) {
                long personKey = res.getLong(i++);
                BOPerson person = tblUtility.GetTData(tblPerson.GetList(personKey));
                if (person != null)
                    newData.setPerson(new BOKeyValue(person.getPrimary_key(), person.getFullName()));

                long grpKey = res.getLong(i++);
                BOGroup group = tblUtility.GetTData(tblGroup.GetList(grpKey));
                if (group != null)
                    newData.setGroup(new BOKeyValue(group.getPrimary_key(), group.getName()));
            }
            returnList.add(newData);
            res.moveToNext();
        }
        res.close();
        return returnList;
    }
    //endregion

    //region Special get List
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<BOLoanDetail> GetViewList(long... keys) {
        String qry = getLoanDetailQry(keys);
        Cursor result = DBUtility.GetDBList(qry);
        return readValue(result);
    }

    public static String getLoanDetailQry(long... Keys) {
        String qryFilter = getQryFilter(Keys);
        String qry = "SELECT " +
                "LD.ID AS ID,"
                + "LD.HEADER_KEY AS HEADER_KEY,"
                + "LD.PERIOD_KEY AS PERIOD_KEY,"
                + "LD.EMI_NUMBER AS EMI_NUMBER,"
                + "LD.EMI_AMOUNT AS EMI_AMOUNT,"
                + "LD.EMI_STATUS AS EMI_STATUS,"
                + "LH.GROUP_KEY AS GROUP_KEY,"
                + "LH.PERSON_KEY AS PERSON_KEY"
                + " FROM "
                + tblLoanDetail.Name + " LD JOIN "
                + tblLoanHeader.Name + " LH ON LH.ID=LD.HEADER_KEY" + qryFilter;
        return qry;
    }
    //endregion

    //region getQryFilter
    static String getQryFilter(long... keys) {
        long primaryKey = keys.length > 0 ? keys[0] : 0;
        long personKey = keys.length > 1 ? keys[1] : 0;
        String suffix = "";
        if (primaryKey > 0)
            suffix += " LD.ID = " + primaryKey;
        if (personKey > 0)
            suffix += (suffix.length() > 0 ? " AND " : "") + " LD.HEADER_KEY = " + personKey;

        suffix = suffix.length() > 0 ? " WHERE " + suffix : suffix;
        return suffix;
    }
    //endregion
}