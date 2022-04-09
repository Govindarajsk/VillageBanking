package com.villagebanking.Database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class DBUtility {
    public static final String dateFormat="EEE MMM d HH:mm:ss zz yyyy";

    //region Database Instance
    public static DBHelper dbHelper;

    public static void CreateDB(Context cntxt) {
        dbHelper = new DBHelper(cntxt, 5);
    }

    public static DBHelper getDB() {
        return dbHelper;
    }
    //endregion

    public static void DTOdelete(Integer ID, String tableName) {
        dbHelper.DBdelete(ID, tableName);
    }

    public static <T> String DTOSaveUpdate(T data,String tableName) {
        BOMap map = DB2Save.DTOSaveUpdate(data,tableName);
        if (map != null) //SAVE TO TABLE
            dbHelper.DBSaveUpdate(map.getPrimary_key(), map.getContentValues(), tableName);
        return "";
    }

    public static <T> ArrayList<T> DTOGetAlls(String tableName) {
        Cursor res = dbHelper.DBGetAll(tableName);
        return DB2GetList.DTOGetAlls(res, tableName);
    }
}
