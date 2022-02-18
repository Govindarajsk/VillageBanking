package com.villagebanking.Database;

public class DBColumn {

    public DBColumn(String columnName, String dataType, String specialType) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.dataType = dataType;
        this.specialType = specialType;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public String getSpecialType() {
        return specialType;
    }

    private String tableName;
    private String columnName;
    private String dataType;
    private String specialType;//primarykey or foreign key
}
