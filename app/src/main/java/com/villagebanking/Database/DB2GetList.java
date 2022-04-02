package com.villagebanking.Database;

import android.database.Cursor;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTransaction;

import java.util.ArrayList;

public class DB2GetList {

    public static <T> ArrayList<T> DTOGetAlls(Cursor res, String tableName) {
        ArrayList<T> returnList = new ArrayList<>();
        switch (tableName) {
            case "PERSONS":
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    BOPerson newData = getValue(res, new BOPerson());
                    returnList.add((T) newData);
                    res.moveToNext();
                }
                return returnList;
            case "GROUPS":
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    BOGroup newInst = getValue(res, new BOGroup());
                    returnList.add((T) newInst);
                    res.moveToNext();
                }
                break;
            case "GROUP_PERSON_LINK":
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    BOGroupPersonLink newInst = getValue(res, new BOGroupPersonLink());
                    returnList.add((T) newInst);
                    res.moveToNext();
                }
                return returnList;
            case "PERSON_TRANSACTION":
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    BOPersonTransaction newInst = getValue(res, new BOPersonTransaction());
                    returnList.add((T) newInst);
                    res.moveToNext();
                }
                return returnList;
        }
        return returnList;
    }

    static BOPerson getValue(Cursor res, BOPerson test) {
        BOPerson newData = new BOPerson();
        newData.setKeyID(res.getInt(0));
        newData.setStrFName(res.getString(1));
        newData.setStrLName(res.getString(2));
        newData.setNumMobile(res.getLong(3));
        return newData;
    }

    static BOGroup getValue(Cursor res, BOGroup test) {
        BOGroup newData = new BOGroup();
        newData.setKeyID(res.getInt(0));
        newData.setName(res.getString(1));
        newData.setNoOfPerson(res.getInt(2));
        newData.setAmount(res.getDouble(3));
        return newData;
    }

    static BOGroupPersonLink getValue(Cursor res, BOGroupPersonLink test) {
        BOGroupPersonLink newData = new BOGroupPersonLink();
        newData.setKeyID(res.getInt(0));
        newData.setGroup_key(res.getInt(1));
        newData.setPerson_key(res.getInt(2));
        newData.setOrderBy(res.getInt(3));
        newData.setPerson_role(res.getString(4));
        return newData;
    }

    static BOPersonTransaction getValue(Cursor res, BOPersonTransaction test) {
        BOPersonTransaction newData = new BOPersonTransaction();
        newData.setKeyID(res.getInt(0));
        newData.setTransDate(res.getString(1));
        newData.setGp_key(res.getInt(2));
        newData.setAmount(res.getDouble(3));
        newData.setStatus(res.getString(4));
        return newData;
    }
}
