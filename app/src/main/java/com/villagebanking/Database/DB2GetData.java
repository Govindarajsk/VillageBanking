package com.villagebanking.Database;

import android.database.Cursor;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTransaction;

import java.util.ArrayList;

public class DB2GetData {
    public static <T> ArrayList<T> DB2GetData(Cursor res, String tableName) {
        ArrayList<T> returnList = new ArrayList<>();
        res.moveToFirst();

        return returnList;
    }
}
