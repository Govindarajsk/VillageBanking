package com.villagebanking.DBTables;

import java.util.ArrayList;

public class tblBase {
    protected static String column0 = "ID";
    protected static String insertUpdateQuery(String flag, String tblName, ArrayList<String> columnList, ArrayList<String> valueList){
        if (flag == "I") {
            return  "INSERT INTO " + tblName + "(" +
                    tblUtility.getStrWithComma(columnList) +
                    ") VALUES (" +
                    tblUtility.getStrWithComma(valueList) +
                    ")";
        } else {
            return  "UPDATE " + tblName + " SET " +
                    tblUtility.getTblUpdate(columnList, valueList) +
                    " WHERE " + columnList.get(0) + " = " + valueList.get(0);
        }
    }
}
