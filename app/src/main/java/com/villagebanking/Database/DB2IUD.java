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

    public static <T> String DTOSaveUpdate(String flag,T data) {
        if (data instanceof BOTransHeader) {
            BOTransHeader th = (BOTransHeader) data;
            return tblTransHeader.getInsertQuery(flag,th);
        }
        if (data instanceof BOTransDetail) {
            BOTransDetail th = (BOTransDetail) data;
            //return BOTransDetail.getInsertQuery(th);
        }
        return null;
    }
}
