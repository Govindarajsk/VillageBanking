package com.villagebanking.DBTables;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class tblUtility {
    static final String primaryKey = " INTEGER PRIMARY KEY";
    static final String integerType = " INTEGER";
    static final String textType = " TEXT";
    static final String decimalType = " DECIMAL";
    static final String comma = ",";
    static final String sQuate = "'";

    //region To String
    public static final String getDBInteger(Long input) {
        return String.valueOf(input);
    }

    public static final String getDBStrings(String input) {
        return sQuate + input + sQuate;
    }

    public static final String getDBDecimal(double input) {
        return String.valueOf(input);
    }
    //endregion

    //region To Columns
    public static final String setDBPrimary(String input, boolean withComma) {
        return input + primaryKey + (withComma ? comma : "");
    }

    public static final String setDBInteger(String input, boolean withComma) {
        return input + integerType + (withComma ? comma : "");
    }

    public static final String setDBStrings(String input, boolean withComma) {
        return input + textType + (withComma ? comma : "");
    }

    public static final String setDBDecimal(String input, boolean withComma) {
        return input + decimalType + (withComma ? comma : "");
    }
    //endregion

    public static final String getStrWithComma(ArrayList<String> inputList) {
        String tempStr = "";
        int n = inputList.size();
        for (int i = 0; i < n; i++) {
            tempStr = tempStr + inputList.get(i) + (i == n - 1 ? "" : ",");
        }
        return tempStr;
    }

    public static final String getTblUpdate(ArrayList<String> columnsList, ArrayList<String> valueList) {
        String columns = "";
        int n = columnsList.size();
        for (int i = 1; i < n; i++) {
            String clmName = columnsList.get(i);
            String clmValue = valueList.get(i);

            String str4 = clmName + "=" + clmValue;
            columns = columns + str4 + (i == n - 1 ? "": ",");
        }
        return columns;
    }

    //region getTables getCreateTables
    public static ArrayList<String> getTables() {
        ArrayList<String> tablesList = new ArrayList<>();
        tablesList.add(tblPerson.Name);
        tablesList.add(tblGroup.Name);
        tablesList.add(tblPeriod.Name);
        tablesList.add(tblGroupPersonLink.Name);
        tablesList.add(tblTransHeader.Name);
        tablesList.add(tblTransDetail.Name);
        tablesList.add(tblLoanHeader.Name);
        return tablesList;
    }

    public static ArrayList<String> getCreateTables() {
        ArrayList<String> tableCreateList = new ArrayList<>();
        tableCreateList.add(tblPerson.CREATE_PERSON);
        tableCreateList.add(tblGroup.CREATE_GROUPS);
        tableCreateList.add(tblPeriod.CREATE_PERIODS);
        tableCreateList.add(tblGroupPersonLink.CREATE_GROUP_PERSON_LINK);
        tableCreateList.add(tblTransHeader.CreateTable);
        tableCreateList.add(tblTransDetail.CreateTable);
        tableCreateList.add(tblLoanHeader.CreateTable);
        return tableCreateList;
    }
   //endregion
}
