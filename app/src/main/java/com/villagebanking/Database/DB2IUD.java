package com.villagebanking.Database;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTrans;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;

public class DB2IUD {

    public static <T> String DTOSaveUpdate(T data) {
        if (data instanceof BOTransHeader) {
            BOTransHeader th = (BOTransHeader) data;
            return tblTransHeader.getInsertQuery(th);
        }
        return null;
    }
}
