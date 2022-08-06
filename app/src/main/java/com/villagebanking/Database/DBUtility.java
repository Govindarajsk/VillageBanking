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

    static int version = 6;
    public static DBSQLQuery DBSQLQuery;
    //region Basic

    public static void CreateDB(Context cntxt) {
        DBSQLQuery = new DBSQLQuery(cntxt, version);
    }

    public static void DTOdelete(long ID, String tableName) {
        if (ID == 0)
            DBSQLQuery.DBDelete(ID, tableName);
        else
            DBSQLQuery.DBDelete(ID, tableName);
    }
    //endregion

    //region Insert/update

    public static void updateField(String tableName, String columnName, String inputValue, long primaryKey) {
        DBSQLQuery.updateField(tableName, columnName, inputValue, primaryKey);
    }
    //endregion

    //region Getlist
    public static <T> ArrayList<T> DTOGetData(String tableName, long primary_key) {
        Cursor res = DBSQLQuery.DBGetData(tableName, primary_key);
        return DB2GetList.DTOGetAlls(res, tableName);
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

    public static String DBSQLWriteQry(String sqlQuery) {
        try {
            return DBSQLQuery.ExecuteSQL(sqlQuery);
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
