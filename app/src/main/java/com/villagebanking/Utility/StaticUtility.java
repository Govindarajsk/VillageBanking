package com.villagebanking.Utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.villagebanking.Database.DBColumn;

import java.util.ArrayList;
import java.util.Calendar;

public class StaticUtility {

    //region Tables
    public static String PERSONS = "PERSONS";
    public static String GROUPS = "GROUPS";
    public static String GROUP_PERSON_LINK = "GROUP_PERSON_LINK";
    public static String PERSON_TRANSACTION = "PERSON_TRANSACTION";
    //endregion

    public static ArrayList<DBColumn> getColumns(String tableName) {
        ArrayList<DBColumn> columns = new ArrayList<>();
        switch (tableName) {
            case "PERSON_TRANSACTION":
                columns.add(new DBColumn("ID", "integer", "primary key"));
                columns.add(new DBColumn("GROUP_PERSON_KEY", "integer", ""));
                columns.add(new DBColumn("AMOUNT", "decimal", ""));
                columns.add(new DBColumn("STATUS", "text", ""));
                return columns;
            default:
                throw new IllegalStateException("Unexpected value: " + tableName);
        }
    }

    //region Datepicker Selecter
    public static View.OnClickListener DisplayDate(Context context, EditText dpDispField) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dpDispField.setText(//dayOfMonth + "/"+
                                        (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        };
    }
    //endregion
}
