package com.villagebanking.Utility;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOAutoComplete;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPersonTrans;
import com.villagebanking.Controls.CCAutoComplete;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticUtility {

    //region Constants
    public static final String V_NUMBER = "NUMBER";
    public static final String V_STRING = "STRING";


    public static final String dateFormat = "dd/MM/yyyy";
    public static final String dateValue = "yyyyMMdd";
    //endregion

    //region Validation
    static final String V_StringRequired = "This field is required";
    static final String V_NumberRequired = "Value should be greater than zero";

    public static boolean IsFieldEmpty(String type, long min, long max, TextView field) {
        switch (type) {
            case V_NUMBER:
                Double Val2 = Double.parseDouble(field.getText().length() == 0 ? "0" : field.getText().toString());
                if (Val2 <= 0) {
                    field.setError(V_NumberRequired);
                    return true;
                }
            case V_STRING:
                if (field.length() == 0) {
                    field.setError(V_StringRequired);
                    return true;
                }

        }
        return false;
    }
    //endregion

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
                                dpDispField.updateDate(year, monthOfYear, dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        };
    }

    public static DatePicker.OnDateChangedListener DisplayDate1(Context context, DatePicker dpDispField) {
        return new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            }
        };
    }

    public static String getDateString(DatePicker strDate) {
        int day = strDate.getDayOfMonth();
        int month = strDate.getMonth() + 1;
        int year = strDate.getYear();
        return String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + year;
    }

    public static long getDateInt(DatePicker strDate) {
        int day = strDate.getDayOfMonth();
        int month = strDate.getMonth() + 1;
        int year = strDate.getYear();
        return Long.valueOf(year + String.format("%02d", month) + String.format("%02d", day));
    }

    public static DatePicker getDateFromString(String strDate, DatePicker dp) {
        String[] strings = strDate.split("/");

        int day = Integer.valueOf(strings[0]);
        int month = Integer.valueOf(strings[1]) + 1;
        int year = Integer.valueOf(strings[2]);
        dp.updateDate(year, month, day);
        return dp;
    }
    //endregion

    //region AutoCompleteBox
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void SetAutoCompleteBox(Context cnxt, ArrayList<BOAutoComplete> list, AutoCompleteTextView control, long... key) {
        CCAutoComplete autoComplete = new CCAutoComplete(cnxt, list);
        control.setAdapter(autoComplete);
        autoComplete.SetDefault(control, key);
    }

    public static long getAutoBoxKey(Object item) {
        if (item instanceof BOAutoComplete) {
            BOAutoComplete itemSelected = (BOAutoComplete) item;
            return itemSelected.getPrimary_key();
        }
        return 0;
    }
    //endregion

    //region Other
    public static Map<String, List<BOPeriod>> GroupByPeriod(ArrayList<BOPeriod> periods) {
        Map<String, List<BOPeriod>> map = new HashMap<>();
        for (BOPeriod item : periods) {
            String value = item.getPeriodType() + ":" + item.getPeriodName();
            List<BOPeriod> list;
            if (map.containsKey(value)) {
                list = map.get(value);
            } else {
                list = new ArrayList<>();
            }
            list.add(item);
            map.put(value, list);
        }
        map.values(); // this will give Collection of values.
        return map;
    }

    public static Map<Long, ArrayList<Long>> GetPersonAmount(ArrayList<BOPersonTrans> transDetails) {

        Map<Long, ArrayList<Long>> map = new HashMap<>();
        for (BOPersonTrans item : transDetails) {
            Long value = item.getTable_link_key();
            ArrayList<Long> list=new ArrayList<>();
            if (map.containsKey(value)) {
                list = map.get(value);
            } else {
                list = new ArrayList<>();
            }
            list.add(item.getPrimary_key());
            map.put(value, list);
        }
        map.values(); // this will give Collection of values.
        return map;
    }

    public static void ApplyTextWatcher(Context context, EditText editText1, EditText editText2, TextView txtView) {
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //ShowMessage(context, "afterTextChanged");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ShowMessage(context, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double Val1 = Double.parseDouble(editText1.getText().length() == 0 ? "1" : editText1.getText().toString());
                Double Val2 = Double.parseDouble(editText2.getText().length() == 0 ? "1" : editText2.getText().toString());

                Double oldValue = Val1.doubleValue() * Val2.doubleValue();
                txtView.setText(oldValue.toString());
            }
        };
        editText1.addTextChangedListener(fieldValidatorTextWatcher);
        editText2.addTextChangedListener(fieldValidatorTextWatcher);

    }

    public static void ShowMessage(Context cntxt, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cntxt);
        builder.setMessage(message);
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog,
                                int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //endregion

    //region Get/Apply
    public static void applyValue(TextView txtView, Object value) {
        String val = value != null ? value.toString() : "";
        if (value instanceof Double) {
            DecimalFormat formater = new DecimalFormat("0.00");
            //formater.format(value instanceof  Double);
            // txtView.setText(String.format("%.##",val));
        }
        txtView.setText(val);
    }

    public static void disableField(TextView txtView) {
        txtView.setEnabled(false);
    }
    //endregion
}
