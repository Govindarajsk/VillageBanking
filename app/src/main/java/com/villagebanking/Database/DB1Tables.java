package com.villagebanking.Database;

import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;

import java.util.ArrayList;

public class DB1Tables {
    public static final String PERSONS = "PERSONS";
    public static final String GROUPS = "GROUPS";
    public static final String PERIODS = "PERIODS";
    public static final String GROUP_PERSON_LINK = "GROUP_PERSON_LINK";
    public static final String PERSON_TRANSACTION = "PERSON_TRANSACTION";


    //region PERSONS
    public static final String CREATE_PERSON =
            PERSONS + "(" +
                    "ID INTEGER PRIMARY KEY, " +
                    "FIRSTNAME TEXT," +
                    "LASTNAME TEXT," +
                    "PHONE TEXT" +
                    ")";
    //endregion
    //region GROUPS
    public static final String CREATE_GROUPS =
            GROUPS + "(" +
                    "ID INTEGER PRIMARY KEY, " +
                    "NAME TEXT," +
                    "NO_OF_PERSON INTEGER," +
                    "AMOUNT DECIMAL," +
                    "START_PERIOD_KEY INTEGER," +
                    "BOND_CHARGE DECIMAL" +
                    ")";
    //endregion
    //region GROUP_PERSON_LINK
    public static final String CREATE_GROUP_PERSON_LINK =
            GROUP_PERSON_LINK + "(" +
                    "ID INTEGER PRIMARY KEY, " +
                    "GROUP_KEY INTEGER," +
                    "PERSON_KEY INTEGER," +
                    "ORDER_BY INTEGER," +
                    "PERSON_ROLE TEXT" +
                    ")";
    //endregion
    //region PERSON_TRANSACTION
    public static final String CREATE_PERSON_TRANSACTION =
            PERSON_TRANSACTION + "(" +
                    "ID INTEGER PRIMARY KEY," +
                    "PARENT_KEY INTEGER, " +
                    "PERIOD_KEY INTEGER, " +
                    "TABLE_NAME TEXT," +
                    "TABLE_LINK_KEY INTEGER," +
                    "REMARKS TEXT," +
                    "AMOUNT DECIMAL" +
                    ")";
    //endregion
    //region PERIOD
    public static final String CREATE_PERIODS =
            PERIODS + "(" +
                    "ID INTEGER PRIMARY KEY," +
                    "PERIOD_TYPE INTEGER, " +
                    "PERIOD_NAME TEXT, " +
                    "ACTUAL_DATE TEXT," +
                    "DATE_VALUE INTEGER," +
                    "PERIOD_REMARKS TEXT," +
                    "PERIOD_STATUS TEXT" +
                    ")";
    //endregion

    /*region TRANSACTION_HEADER
    public static final String CREATE_TRANSACTION_HEADER =
            TRANSACTION_HEADER + "(" +
                    "ID INTEGER PRIMARY KEY," +
                    "PARENT_KEY INTEGER, " +
                    "CHILD_KEY INTEGER, " +
                    "PERIOD_KEY INTEGER, " +
                    "TABLE_NAME TEXT," +
                    "TABLE_LINK_KEY INTEGER," +
                    "REMARKS TEXT," +
                    "TOTAL_AMOUNT DECIMAL," +
                    "PAID_AMOUNT DECIMAL," +
                    "BALANCE_AMOUNT DECIMAL" +
                    ")";
    endregion*/
    //region TRANSACTION_DETAIL

    //endregion

    //region TABLE LIST
    private static ArrayList<String> tablesList;

    public static ArrayList<String> getTables() {
        tablesList = new ArrayList<>();
        tablesList.add(PERSONS);
        tablesList.add(GROUPS);
        tablesList.add(PERIODS);
        tablesList.add(GROUP_PERSON_LINK);
        tablesList.add(PERSON_TRANSACTION);
        tablesList.add(tblTransHeader.Name);
        tablesList.add(tblTransDetail.Name);
        return tablesList;
    }
    //endregion

    //region TABLECREATION_QUERY LIST
    private static ArrayList<String> tableCreateList;

    public static ArrayList<String> getCreateTables() {
        tableCreateList = new ArrayList<>();
        tableCreateList.add(CREATE_PERSON);
        tableCreateList.add(CREATE_GROUPS);
        tableCreateList.add(CREATE_PERIODS);
        tableCreateList.add(CREATE_GROUP_PERSON_LINK);
        tableCreateList.add(CREATE_PERSON_TRANSACTION);
        tableCreateList.add(tblTransHeader.CreateTable);
        tableCreateList.add(tblTransDetail.CreateTable);
        return tableCreateList;
    }
    //endregion
}
