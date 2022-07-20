package com.villagebanking.DBTables;

public class tblPerson extends tblBase{
    public static final String Name  = "PERSONS";
    public static final String CREATE_PERSON =
            Name + "(" +
                    "ID INTEGER PRIMARY KEY, " +
                    "FIRSTNAME TEXT," +
                    "LASTNAME TEXT," +
                    "PHONE TEXT" +
                    ")";
}
