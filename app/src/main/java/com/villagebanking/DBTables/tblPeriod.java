package com.villagebanking.DBTables;

public class tblPeriod extends tblBase {
    public static final String Name = "PERIODS";

    public static String getPeriodKeys(long personKey) {
        String periodKeys = String.valueOf(personKey);
        return periodKeys;
    }
    public static final String CREATE_PERIODS =
            Name + "(" +
                    "ID INTEGER PRIMARY KEY," +
                    "PERIOD_TYPE INTEGER, " +
                    "PERIOD_NAME TEXT, " +
                    "ACTUAL_DATE TEXT," +
                    "DATE_VALUE INTEGER," +
                    "PERIOD_REMARKS TEXT," +
                    "PERIOD_STATUS TEXT" +
                    ")";
}
