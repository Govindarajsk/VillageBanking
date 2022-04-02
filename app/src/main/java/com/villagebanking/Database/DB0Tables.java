package com.villagebanking.Database;

import java.util.ArrayList;

public class DB0Tables {
    public static final String PERSONS = "PERSONS";
    public static final String GROUPS = "GROUPS";
    public static final String GROUP_PERSON_LINK = "GROUP_PERSON_LINK";
    public static final String PERSON_TRANSACTION = "PERSON_TRANSACTION";

    public static final String CREATE_PERSON =PERSONS + "(id integer primary key, FirstName text,LastName text,phone text)";

    public static final String CREATE_GROUPS = GROUPS + "(id integer primary key, name text,personsCount integer,amount decimal)";

    public static final String CREATE_GROUP_PERSON_LINK = GROUP_PERSON_LINK + "(id integer primary key, group_key integer,person_key integer,order_by integer,person_role text)";

    public static final String CREATE_PERSON_TRANSACTION= PERSON_TRANSACTION + "(ID integer primary key,TRANS_DATE text, GROUP_PERSON_KEY integer,AMOUNT decimal,STATUS text)";


    private static ArrayList<String> tablesList;
    public static ArrayList<String> getTables() {
        tablesList.add(PERSONS);
        tablesList.add(GROUPS);
        tablesList.add(GROUP_PERSON_LINK);
        tablesList.add(PERSON_TRANSACTION);
        return tablesList;
    }
    private static ArrayList<String> tableCreateList;
    public static ArrayList<String> getCreateTables() {
        tableCreateList.add(CREATE_PERSON);
        tableCreateList.add(CREATE_GROUPS);
        tableCreateList.add(CREATE_GROUP_PERSON_LINK);
        tableCreateList.add(CREATE_PERSON_TRANSACTION);
        return tablesList;
    }
}