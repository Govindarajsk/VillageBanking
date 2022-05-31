package com.villagebanking.Database;

import android.content.Context;
import android.database.Cursor;

import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;

import java.util.ArrayList;

public class DBUtility {

    static int version = 25;
    //region Database Instance
    public static DBSQLQuery DBSQLQuery;

    public static void CreateDB(Context cntxt) {
        DBSQLQuery = new DBSQLQuery(cntxt, version);
    }

    public static DBSQLQuery getDB() {
        return DBSQLQuery;
    }
    //endregion

    //region Save update and delete
    public static void DTOdelete(long ID, String tableName) {
        DBSQLQuery.DBdelete(ID, tableName);
    }

    public static <T> String DTOSaveUpdate(T data, String tableName) {
        DB1BOMap map = DB2Save.DTOSaveUpdate(data, tableName);
        if (map != null) //SAVE TO TABLE
            DBSQLQuery.DBSaveUpdate(map.getPrimary_key(), map.getContentValues(), tableName);
        return "";
    }

    public static <T> String DTOSaveUpdate(String flag, T data, String tableName) {
        DB1BOMap map = DB2Save.DTOSaveUpdate(data, tableName);
        if (map != null) //SAVE TO TABLE
            DBSQLQuery.DBSaveUpdate(flag,map.getPrimary_key(), map.getContentValues(), tableName);
        return "";
    }

    public static void updateField(String tableName, String columnName, String inputValue, long primaryKey) {
        DBSQLQuery.updateField(tableName, columnName, inputValue, primaryKey);
    }
    //endregion

    //region Getlist
    public static <T> ArrayList<T> DTOGetAlls(String tableName) {
        Cursor res = DBSQLQuery.DBGetAll(tableName);
        ArrayList<T> getList = DB2GetList.DTOGetAlls(res, tableName);
        return getList;
    }

    public static <T> ArrayList<T> DTOGetData(String tableName, long primary_key) {
        Cursor res = DBSQLQuery.DBGetData(tableName, primary_key);
        return DB2GetList.DTOGetAlls(res, tableName);
    }
    //endregion

    public static <T> ArrayList<T> FetchGroupLink(String tableName, String periodKeys) {
        ArrayList<T> retunList = new ArrayList<>();
        if (tableName.equals(tblTransHeader.Name)) {
            Cursor res = DBSQLQuery.DBFetchGroupLink(periodKeys);
            retunList = DB2GetList.DTOGetAlls(res, tableName);
        }
        return retunList;
    }

    //region Trans List
    public static <T> ArrayList<T> DBGetDataFilter(String tableName, String columnName, String inputValue) {
        Cursor res = DBSQLQuery.DBGetDataFilter(tableName, columnName, inputValue);
        return DB2GetList.DTOGetAlls(res, tableName);
    }

    public static ArrayList<BOTransDetail> DTOGetTransData(String parentKeys, String periodKeys, String childKeys, String linkKeys) {
        Cursor res = DBSQLQuery.DBGetTransDetail(parentKeys, periodKeys, childKeys, linkKeys);
        return DB2GetList.DTOGetAlls(res, tblTransDetail.Name);
    }
    //endregion
}
