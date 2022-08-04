package com.villagebanking.DBTables;

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
import com.villagebanking.Database.DBUtility;

import java.util.ArrayList;

public class tblLoanHeader extends tblBase {
    public static final String Name = "LOAN_HEADER";

    //region Columns 8 => LOAN_HEADER
    private static String column1 = "PERSON_KEY";
    private static String column2 = "GROUP_KEY";
    private static String column3 = "LOAN_TYPE";
    private static String column4 = "LOAN_AMOUNT";
    private static String column5 = "OTHER_AMOUNT";
    private static String column6 = "CHARGES";
    private static String column7 = "TRANS_DATE";
    private static String column8 = "REMARKS";
    private static String column9 = "REFERENCE1";
    private static String column10 = "REPAY_AMOUNT";


    private static BOColumn<Long> DBColumn1 = new BOColumn<Long>(DBCLMTYPE.INT, column1);
    private static BOColumn<Long> DBColumn2 = new BOColumn<Long>(DBCLMTYPE.INT, column2);
    private static BOColumn<Long> DBColumn3 = new BOColumn<Long>(DBCLMTYPE.INT, column3);
    private static BOColumn<Double> DBColumn4 = new BOColumn<Double>(DBCLMTYPE.DBL, column4);
    private static BOColumn<Double> DBColumn5 = new BOColumn<Double>(DBCLMTYPE.DBL, column5);
    private static BOColumn<Double> DBColumn6 = new BOColumn<Double>(DBCLMTYPE.DBL, column6);
    private static BOColumn<String> DBColumn7 = new BOColumn<String>(DBCLMTYPE.TXT, column7);
    private static BOColumn<String> DBColumn8 = new BOColumn<String>(DBCLMTYPE.TXT, column8);
    private static BOColumn<Long> DBColumn9 = new BOColumn<Long>(DBCLMTYPE.INT, column9);
    private static BOColumn<Double> DBColumn10 = new BOColumn<Double>(DBCLMTYPE.DBL, column10);
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
        columnList.add(DBColumn10);
        return columnList;
    }
    //endregion

    public static final String CreateTable = Name + BOColumn.getCreateTableQry(columnList);

    //region Save(flag,data)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String Save(String flag, BOLoanHeader data) {
        String sqlQuery = BOColumn.getInsetUpdateQry(flag, Name, getColumnValueMap(data));
        return DBUtility.DBSave(sqlQuery);
    }

    static ArrayList<BOColumn> getColumnValueMap(BOLoanHeader data) {
        DBColumn1.setColumnValue(data.getPersonKey().getPrimary_key());
        DBColumn2.setColumnValue(data.getGroupKey().getPrimary_key());
        DBColumn3.setColumnValue(data.getLoanType().getPrimary_key());
        DBColumn4.setColumnValue(data.getLoanAmount());
        DBColumn5.setColumnValue(data.getOtherAmount());
        DBColumn6.setColumnValue(data.getCharges());
        DBColumn7.setColumnValue(data.getTransDate());
        DBColumn8.setColumnValue(data.getRemarks());
        DBColumn9.setColumnValue(data.getReference1().getPrimary_key());
        DBColumn10.setColumnValue(data.getRepayAmount());
        return columnList;
    }
    //endregion

    //region GetList => primaryKey
    /* ID,PERSON_KEY */
    public static ArrayList<BOLoanHeader> GetList(long... Keys) {
        String qryFilter = getQryFilter(Keys);
        Cursor result = DBUtility.GetDBList(BOColumn.getListQry(Name, columnList, qryFilter));
        return readValue(result);
    }

    static ArrayList<BOLoanHeader> readValue(Cursor res) {
        ArrayList<BOLoanHeader> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BOLoanHeader newData = new BOLoanHeader();
            int i = 0;
            newData.setPrimary_key(res.getLong(i++));
            //newData.getPersonKey().setPrimary_key(res.getLong(i++));
            long personKey = res.getLong(i++);
            BOPerson person = (BOPerson) DBUtility.GetData(tblPerson.Name, personKey);
            if (person != null)
                newData.setPersonKey(new BOKeyValue(person.getPrimary_key(), person.getFullName()));

            long grpKey = res.getLong(i++);
            BOGroup group = (BOGroup) DBUtility.GetData(tblGroup.Name, grpKey);
            if (group != null)
                newData.setGroupKey(new BOKeyValue(group.getPrimary_key(), group.getName()));

            newData.getLoanType().setPrimary_key(res.getLong(i++));
            newData.setLoanAmount(res.getDouble(i++));
            newData.setOtherAmount(res.getDouble(i++));
            newData.setCharges(res.getDouble(i++));
            newData.setTransDate(res.getString(i++));
            newData.setRemarks(res.getString(i++));

            long refKey = res.getLong(i++);
            BOPerson ref1 = DBUtility.GetData(tblPerson.Name, refKey);
            if (ref1 != null)
                newData.setReference1(new BOKeyValue(ref1.getPrimary_key(), ref1.getFullName()));
            newData.setRepayAmount(res.getDouble(i++));
            returnList.add(newData);
            res.moveToNext();
        }
        res.close();
        return returnList;
    }
    //endregion

    //region getQryFilter
    static String getQryFilter(long... keys) {
        long primaryKey = keys.length > 0 ? keys[0] : 0;
        long personKey = keys.length > 1 ? keys[1] : 0;
        String suffix = "";
        if (primaryKey > 0)
            suffix += " ID = " + primaryKey;
        if (personKey > 0)
            suffix += (suffix.length() > 0 ? " AND " : "") + " PERSON_KEY = " + personKey;

        suffix = suffix.length() > 0 ? " WHERE " + suffix : suffix;
        return suffix;
    }
    //endregion
}
