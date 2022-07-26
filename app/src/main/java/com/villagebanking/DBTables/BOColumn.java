package com.villagebanking.DBTables;

import java.util.ArrayList;

public class BOColumn<T> {

    private tblBase.DBCLMTYPE clmType;
    private String clmName;
    private boolean isPrimaryKey;
    private String columnValue;

    public BOColumn(tblBase.DBCLMTYPE type, String clmName, boolean... isPrimaryKey) {
        this.clmType = type;
        this.clmName = clmName;
        if (isPrimaryKey.length > 0)
            this.isPrimaryKey = isPrimaryKey[0];
    }

    public tblBase.DBCLMTYPE getClmType() {
        return clmType;
    }

    public void setClmType(tblBase.DBCLMTYPE clmType) {
        this.clmType = clmType;
    }

    public String getClmName() {
        return clmName;
    }

    public boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(T columnValue) {
        String value = "";
        if (columnValue instanceof String)
            value = "'" + (String) columnValue + "'";
        else value = String.valueOf(columnValue);
        this.columnValue = value;
    }

    static String getType(tblBase.DBCLMTYPE clmType) {
        String output = "";
        switch (clmType) {
            case DBL:
            case INT:
                output = " INTEGER ";
                break;
            case TXT:
                output = " TEXT ";
                break;
        }
        return output;
    }

    //region DB Query
    public static String getCreateTableQry(ArrayList<BOColumn> clmList) {
        String qry = "(";
        int n = clmList.size();
        for (int i = 0; i < n; i++) {
            BOColumn item = clmList.get(i);
            String clmType = item.getClmName() + getType(item.getClmType());
            if (item.getIsPrimaryKey()) {
                qry += clmType + "PRIMARY KEY";
            } else {
                qry += item.getClmName() + getType(item.getClmType());
            }
            if (i == n - 1)
                qry += ")";
            else
                qry += ",";
        }
        return qry;
    }

    public static String getInsetUpdateQry(String flag, String tblName, ArrayList<BOColumn> clmList) {
        String qry = "";
        int n = clmList.size();
        if (flag == "I") {
            String prefix = "INSERT INTO " + tblName + "(";
            String columnName = "";
            String middle = ") VALUES (";
            String columnValue = "";
            String suffix = ") RETURNING ID";

            for (int i = 0; i < n; i++) {
                BOColumn item = clmList.get(i);
                if (!item.getIsPrimaryKey()) {
                    columnName += item.getClmName();
                    columnValue += item.getColumnValue();
                    if (i != n - 1) {
                        columnName += ",";
                        columnValue += ",";
                    }
                }
            }
            qry = prefix + columnName + middle + columnValue + suffix;
        } else {
            String prefix = "UPDATE " + tblName + " SET ";
            String updateSet = "";
            String suffix = "";

            for (int i = 0; i < n; i++) {
                BOColumn item = clmList.get(i);
                if (!item.getIsPrimaryKey()) {
                    updateSet += item.getClmName() + "=" + item.getColumnValue();
                    if (i != n - 1) {
                        updateSet += ",";
                    }
                } else {
                    suffix = " WHERE " + item.getClmName() + "=" + item.getColumnValue();
                }
            }
            qry = prefix + updateSet + suffix;
        }
        return qry;
    }

    public static String getListQry(String tblName, ArrayList<BOColumn> clmList, String filter) {
        String prefix = "SELECT";
        String columnName = " ";
        String suffix = " FROM ";

        int n = clmList.size();
        for (int i = 0; i < n; i++) {
            BOColumn item = clmList.get(i);
            columnName += item.getClmName();
            if (item.getIsPrimaryKey()) {
                suffix += tblName + filter;
            }
            if (i != n - 1)
                columnName += ",";
        }
        String qry = prefix + columnName + suffix;
        return qry;
    }
    //endregion
}
