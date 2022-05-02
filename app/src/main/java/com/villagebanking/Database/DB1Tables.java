package com.villagebanking.Database;

import java.util.ArrayList;

public class DB1Tables {
    public static final String PERSONS = "PERSONS";
    public static final String GROUPS = "GROUPS";
    public static final String PERIODS = "PERIODS";
    public static final String GROUP_PERSON_LINK = "GROUP_PERSON_LINK";
    public static final String PERSON_TRANSACTION = "PERSON_TRANSACTION";

    public static final String CREATE_PERSON =
            PERSONS + "(" +
                    "ID INTEGER PRIMARY KEY, " +
                    "FIRSTNAME TEXT," +
                    "LASTNAME TEXT," +
                    "PHONE TEXT" +
                    ")";

    public static final String CREATE_GROUPS =
            GROUPS + "(" +
                    "ID INTEGER PRIMARY KEY, " +
                    "NAME TEXT," +
                    "NO_OF_PERSON INTEGER," +
                    "AMOUNT DECIMAL," +
                    "START_PERIOD_KEY INTEGER,"+
                    "BOND_CHARGE DECIMAL"+
                    ")";

    public static final String CREATE_GROUP_PERSON_LINK =
            GROUP_PERSON_LINK + "(" +
                    "ID INTEGER PRIMARY KEY, " +
                    "GROUP_KEY INTEGER," +
                    "PERSON_KEY INTEGER," +
                    "ORDER_BY INTEGER," +
                    "PERSON_ROLE TEXT" +
                    ")";

    public static final String CREATE_PERSON_TRANSACTION =
            PERSON_TRANSACTION + "(" +
                    "ID INTEGER PRIMARY KEY," +
                    "PERIOD_KEY INTEGER, " +
                    "TABLE_NAME TEXT,"+
                    "TABLE_LINK_KEY INTEGER,"+
                    "REMARKS TEXT,"+
                    "AMOUNT DECIMAL" +
                    ")";

    public static final String CREATE_PERIODS =
            PERIODS + "(" +
                    "ID INTEGER PRIMARY KEY," +
                    "PERIOD_TYPE INTEGER, " +
                    "PERIOD_NAME TEXT, " +
                    "ACTUAL_DATE TEXT," +
                    "DATEVALUE INTEGER," +
                    "PERIOD_REMARKS TEXT" +
                    ")";

    private static ArrayList<String> tablesList;

    public static ArrayList<String> getTables() {
        tablesList = new ArrayList<>();
        tablesList.add(PERSONS);
        tablesList.add(GROUPS);
        tablesList.add(PERIODS);
        tablesList.add(GROUP_PERSON_LINK);
        tablesList.add(PERSON_TRANSACTION);
        return tablesList;
    }

    private static ArrayList<String> tableCreateList;

    public static ArrayList<String> getCreateTables() {
        tableCreateList = new ArrayList<>();
        tableCreateList.add(CREATE_PERSON);
        tableCreateList.add(CREATE_GROUPS);
        tableCreateList.add(CREATE_PERIODS);
        tableCreateList.add(CREATE_GROUP_PERSON_LINK);
        tableCreateList.add(CREATE_PERSON_TRANSACTION);
        return tableCreateList;
    }
}
