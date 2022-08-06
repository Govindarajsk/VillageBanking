package com.villagebanking.DBTables;

import java.util.ArrayList;
import java.util.stream.Stream;

public abstract class tblBase {
    protected static String column0 = "ID";
    protected static BOColumn<Long> DBColumn0 = new BOColumn<Long>(DBCLMTYPE.INT,column0,true);

    public enum DBCLMTYPE{
        INT,TXT,DBL
    }
}
