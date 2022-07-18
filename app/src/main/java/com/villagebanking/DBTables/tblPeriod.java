package com.villagebanking.DBTables;

public class tblPeriod extends tblBase {
    public static final String Name = "PERIODS";

    public static String getPeriodKeys(long personKey) {
        String periodKeys = String.valueOf(personKey);
        //DBUtility.DTOGetAlls()
        return periodKeys;
    }
}
