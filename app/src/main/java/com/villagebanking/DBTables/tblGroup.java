package com.villagebanking.DBTables;

public class tblGroup extends tblBase {
    public static final String Name = "GROUPS";
    public static final String CREATE_GROUPS =
            Name + "(" +
                    "ID INTEGER PRIMARY KEY, " +
                    "NAME TEXT," +
                    "NO_OF_PERSON INTEGER," +
                    "AMOUNT DECIMAL," +
                    "START_PERIOD_KEY INTEGER," +
                    "BOND_CHARGE DECIMAL" +
                    ")";
}
