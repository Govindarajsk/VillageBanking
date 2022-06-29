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

    public static final String getDBInteger(Long input) {
        return String.valueOf(input);
    }

    public static final String getDBStrings(String input) {
        return sQuate + input + sQuate;
    }

    public static final String getDBDecimal(double input) {
        return String.valueOf(input);
    }


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

    public static final String setDBColumns(ArrayList<String> columnsList) {
        String columns = "";
        int n = columnsList.size();
        for (int i = 0; i < n; i++) {
            columns = columns + columnsList.get(i) + (i == n - 1 ? "" : ",");
        }
        return columns;
    }
}
