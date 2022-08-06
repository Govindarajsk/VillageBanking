package com.villagebanking.Database;

import android.database.Cursor;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.DBTables.tblTransDetail;

import java.util.ArrayList;

public class DB2GetList {

    public static <T> ArrayList<T> DTOGetAlls(Cursor res, String tableName) {
        ArrayList<T> returnList = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()) {
            switch (tableName) {
                case tblTransHeader.Name:
                    returnList.add((T) tblTransHeader.readValue(res));
                    break;
            }
            res.moveToNext();
        }
        res.close();
        return returnList;
    }
}
