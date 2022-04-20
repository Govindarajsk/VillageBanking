package com.villagebanking.Utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.villagebanking.BOObjects.BOPeriod;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticUtility {
    public static final String LISTBOX = "LISTBOX";
    public static final String DATAGRID = "DATAGRID";

    //region Datepicker Selecter
    public static View.OnClickListener DisplayDate(Context context, DatePicker dpDispField) {
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
                                // dpDispField.se(dayOfMonth + "/"+
                                //       (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        };
    }
    //endregion

    public static String getDateString(String date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        sdf.format(date);
        return date;
    }

    public static long  getDateInteger(long  periodValue )
    {
        return  periodValue;
    }

    public static String getDate(DatePicker strDate) {
        int day = strDate.getDayOfMonth();
        int month = strDate.getMonth();
        int year = strDate.getYear();
        return day + "/" + month + "/" + year;
    }
    public static long getDateInt(DatePicker strDate) {
        int day = strDate.getDayOfMonth();
        int month = strDate.getMonth();
        int year = strDate.getYear();
        return year  + month  + day;
    }

    public static Map<Integer, List<BOPeriod>> GroupByPeriod(ArrayList<BOPeriod> periods) {
        Map<Integer, List<BOPeriod>> map = new HashMap<>();
        for (BOPeriod item : periods) {
            List<BOPeriod> list;
            if (map.containsKey(item.getPeriodType())) {
                list = map.get(item.getPeriodType());
            } else {
                list = new ArrayList<>();
            }
            list.add(item);
            map.put(item.getPeriodType(), list);
        }
        map.values(); // this will give Collection of values.
        return map;
    }
}
