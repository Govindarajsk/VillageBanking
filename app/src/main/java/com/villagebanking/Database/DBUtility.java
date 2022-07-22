package com.villagebanking.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblLoanHeader;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;

import java.util.ArrayList;

public class DBUtility<T> {

    static int version = 5;
    public static DBSQLQuery DBSQLQuery;
    //region Basic

    public static void CreateDB(Context cntxt) {
        DBSQLQuery = new DBSQLQuery(cntxt, version);
    }

    public static void DTOdelete(long ID, String tableName) {
        if (ID == 0)
            DBSQLQuery.DBDelete(tableName, ID);
        else
            DBSQLQuery.DBDelete(tableName, ID);
    }
    //endregion

    //region Insert/update
    public static <T> String DTOInsertUpdate(String flag, T data) {
        String insertQry = DB2IUD.DTOSaveUpdate(flag, data);
        return DBSQLQuery.DBDMLQuery(insertQry);
    }

    public static <T> String DTOSaveUpdate(T data, String tableName) {
        DB1BOMap map = DB2Save.DTOSaveUpdate(data, tableName);
        if (map != null) //SAVE TO TABLE
            DBSQLQuery.DBSaveUpdate(map.getPrimary_key(), map.getContentValues(), tableName);
        return "";
    }

    public static void updateField(String tableName, String columnName, String inputValue, long primaryKey) {
        DBSQLQuery.updateField(tableName, columnName, inputValue, primaryKey);
    }
    //endregion

    //region Getlist
    public static <T> ArrayList<T> DTOGetAlls(String tableName) {
        Cursor res = DBSQLQuery.DBGetData(tableName, 0);
        ArrayList<T> getList = DB2GetList.DTOGetAlls(res, tableName);
        return getList;
    }

    public static <T> ArrayList<T> DTOGetData(String tableName, long primary_key) {
        Cursor res = DBSQLQuery.DBGetData(tableName, primary_key);
        return DB2GetList.DTOGetAlls(res, tableName);
    }

    public static <T> ArrayList<T> FetchPeriodTrans(String tableName, String periodKeys) {
        ArrayList<T> retunList = new ArrayList<>();
        if (tableName.equals(tblGroupPersonLink.Name)) {
            Cursor res = DBSQLQuery.DBFetchGroupLink(periodKeys);
            retunList = DB2GetList.DTOGetAlls(res, tblTransHeader.Name);
        } else if (tableName.equals(tblLoanHeader.Name)) {
            Cursor res = DBSQLQuery.DBFetchLoanTransHeader(periodKeys);
            retunList = DB2GetList.DTOGetAlls(res, tblTransHeader.Name);
        } else if (tableName.equals(tblTransHeader.Name)) {
            Cursor res = DBSQLQuery.DBFetchTransHeader(periodKeys);
            retunList = DB2GetList.DTOGetAlls(res, tblTransHeader.Name);
        }
        return retunList;
    }


    public static <T> ArrayList<T> DBGetGroupPerson(String tblName) {
        Cursor res = DBSQLQuery.DBGroupPerson();
        return DB2GetList.DTOGetAlls(res, tblName);
    }

    //endregion

    //region Trans List
    public static <T> ArrayList<T> DBGetDataFilter(String tableName, String columnName, String inputValue) {
        Cursor res = DBSQLQuery.DBGetDataFilter(tableName, columnName, inputValue);
        return DB2GetList.DTOGetAlls(res, tableName);
    }

    public static ArrayList<BOTransDetail> DTOGetTransData(long headerKey) {
        Cursor res = DBSQLQuery.DBGetTransDetail(headerKey);
        return DB2GetList.DTOGetAlls(res, tblTransDetail.Name);
    }
    //endregion

    //region new Format create/insert/update/delete
    public static void AlterTable(String tblName, String creteTableQry) {
        DBSQLQuery.GetWritableDB().execSQL("DROP TABLE IF EXISTS " + tblName);
        DBSQLQuery.GetWritableDB().execSQL("CREATE TABLE " + creteTableQry);
    }

    public static String DBSave(String sqlQuery) {
        try {
            DBSQLQuery.GetWritableDB().execSQL(sqlQuery);
            return "Success!";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public static Cursor GetDBList(String sqlQuery) {
        return DBSQLQuery.GetReadableDB().rawQuery(sqlQuery, null);
    }

    public static <T> T GetData(String tableName, long primary_key) {
        Cursor res = DBSQLQuery.DBGetData(tableName, primary_key);
        ArrayList<T> list = DB2GetList.DTOGetAlls(res, tableName);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    //endregion
}
