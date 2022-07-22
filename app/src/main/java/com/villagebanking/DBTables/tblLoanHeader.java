package com.villagebanking.DBTables;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    //region Columns 8 => column1
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

    //endregion

    //region Columns Type Set => CreateTableQRY
    public static final String CreateTable = Name + "(" +
            tblUtility.setDBPrimary(column0, true) +
            tblUtility.setDBInteger(column1, true) +
            tblUtility.setDBInteger(column2, true) +
            tblUtility.setDBInteger(column3, true) +
            tblUtility.setDBDecimal(column4, true) +
            tblUtility.setDBDecimal(column5, true) +
            tblUtility.setDBDecimal(column6, true) +
            tblUtility.setDBStrings(column7, true) +
            tblUtility.setDBStrings(column8, true) +
            tblUtility.setDBInteger(column9, true) +
            tblUtility.setDBInteger(column10, false) +
            ")";
    //endregion

    //region Columns List => getColumns
    static ArrayList<String> getColumns() {
        ArrayList<String> columnList = new ArrayList<>();
        //columnList.add(column0);
        columnList.add(column1);
        columnList.add(column2);
        columnList.add(column3);
        columnList.add(column4);
        columnList.add(column5);
        columnList.add(column6);
        columnList.add(column7);
        columnList.add(column8);
        columnList.add(column9);
        columnList.add(column10);
        return columnList;
    }
    //endregion

    //region Columns Value => getMapValues
    static ArrayList<String> getMapValues(BOLoanHeader loanHeader) {
        ArrayList<String> valueList = new ArrayList<>();
        valueList.add(tblUtility.getDBInteger(loanHeader.getPersonKey().getPrimary_key()));
        valueList.add(tblUtility.getDBInteger(loanHeader.getGroupKey().getPrimary_key()));
        valueList.add(tblUtility.getDBInteger(loanHeader.getLoanType().getPrimary_key()));
        valueList.add(tblUtility.getDBDecimal(loanHeader.getLoanAmount()));
        valueList.add(tblUtility.getDBDecimal(loanHeader.getOtherAmount()));
        valueList.add(tblUtility.getDBDecimal(loanHeader.getCharges()));
        valueList.add(tblUtility.getDBStrings(loanHeader.getTransDate()));
        valueList.add(tblUtility.getDBStrings(loanHeader.getRemarks()));
        valueList.add(tblUtility.getDBInteger(loanHeader.getReference1().getPrimary_key()));
        valueList.add(tblUtility.getDBDecimal(loanHeader.getRepayAmount()));
        return valueList;
    }
    //endregion

    //region Save
    public static String Save(String flag, BOLoanHeader loanHeader) {
        String sqlQuery = insertUpdateQuery(flag, Name, getColumns(), getMapValues(loanHeader));
        return DBUtility.DBSave(sqlQuery);
    }
    //endregion

    //region GetList
    public static ArrayList<BOLoanHeader> GetList(long primaryKey) {
        ArrayList<BOLoanHeader> returnList = new ArrayList<>();
        Cursor res = DBUtility.GetDBList(getListQuery(primaryKey));
        res.moveToFirst();
        while (!res.isAfterLast()) {
            returnList.add(readValue(res));
            res.moveToNext();
        }
        return returnList;
    }

    static String getListQuery(long primaryKey) {
        return
                "SELECT " +
                        column0 + "," +
                        column1 + "," +
                        column2 + "," +
                        column3 + "," +
                        column4 + "," +
                        column5 + "," +
                        column6 + "," +
                        column7 + "," +
                        column8 + "," +
                        column9 + "," +
                        column10 +
                        " FROM " + Name +
                        (primaryKey > 0 ? " WHERE ID = " + primaryKey : "");
    }

    static BOLoanHeader readValue(Cursor res) {
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
        if (person != null)
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
            newData.setPersonKey(new BOKeyValue(ref1.getPrimary_key(), ref1.getFullName()));
        newData.setRepayAmount(res.getDouble(i++));
        return newData;
    }
    //endregion

}
