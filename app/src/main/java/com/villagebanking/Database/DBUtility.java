package com.villagebanking.Database;

import android.content.Context;
import android.database.Cursor;

import com.villagebanking.BOObjects.BOPersonTrans;

import java.util.ArrayList;

public class DBUtility {

    //region Database Instance
    public static DBSQLQuery DBSQLQuery;

    public static void CreateDB(Context cntxt) {
        DBSQLQuery = new DBSQLQuery(cntxt, 16);
    }

    public static DBSQLQuery getDB() {
        return DBSQLQuery;
    }
    //endregion

    public static void DTOdelete(long ID, String tableName) {
        DBSQLQuery.DBdelete(ID, tableName);
    }

    public static <T> String DTOSaveUpdate(T data, String tableName) {
        BOMap map = DB2Save.DTOSaveUpdate(data, tableName);
        if (map != null) //SAVE TO TABLE
            DBSQLQuery.DBSaveUpdate(map.getPrimary_key(), map.getContentValues(), tableName);
        return "";
    }

    public static <T> ArrayList<T> DTOGetAlls(String tableName) {
        Cursor res = DBSQLQuery.DBGetAll(tableName);
        ArrayList<T> getList = DB2GetList.DTOGetAlls(res, tableName);
        return getList;
    }

    public static void updateField(String tableName, String columnName, String inputValue, long primaryKey) {
        DBSQLQuery.updateField(tableName, columnName, inputValue, primaryKey);
    }

    public static <T> ArrayList<T> DTOGetData(String tableName, long primary_key) {
        Cursor res = DBSQLQuery.DBGetData(tableName, primary_key);
        return DB2GetList.DTOGetAlls(res, tableName);
    }

    public static <T> ArrayList<T> DBGetDataFilter(String tableName, String columnName, String inputValue) {
        Cursor res = DBSQLQuery.DBGetDataFilter(tableName, columnName, inputValue);
        return DB2GetList.DTOGetAlls(res, tableName);
    }

    public static ArrayList<BOPersonTrans> DTOGetTransData(long person_key, String periodkeys, long groupLinkKey) {
        Cursor res = DBSQLQuery.DBGetTransData(person_key, periodkeys, groupLinkKey);
        return DB2GetList.DTOGetAlls(res, DB1Tables.PERSON_TRANSACTION);
    }
}
