package com.villagebanking.Database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class DBUtility {
    public static final String dateFormat="EEE MMM d HH:mm:ss zz yyyy";

    //region Database Instance
    public static DBSQLQuery DBSQLQuery;

    public static void CreateDB(Context cntxt) {
        DBSQLQuery = new DBSQLQuery(cntxt, 8);
    }

    public static DBSQLQuery getDB() {
        return DBSQLQuery;
    }
    //endregion

    public static void DTOdelete(long ID, String tableName) {
        DBSQLQuery.DBdelete(ID, tableName);
    }

    public static <T> String DTOSaveUpdate(T data,String tableName) {
        BOMap map = DB2Save.DTOSaveUpdate(data,tableName);
        if (map != null) //SAVE TO TABLE
            DBSQLQuery.DBSaveUpdate(map.getPrimary_key(), map.getContentValues(), tableName);
        return "";
    }

    public static <T> ArrayList<T> DTOGetAlls(String tableName) {
        Cursor res = DBSQLQuery.DBGetAll(tableName);
        return DB2GetList.DTOGetAlls(res, tableName);
    }
    public static <T> ArrayList<T> DTOGetData(String tableName, long primary_key) {
        Cursor res = DBSQLQuery.DBGetData(tableName,primary_key);
        return DB2GetList.DTOGetAlls(res, tableName);
    }
}
