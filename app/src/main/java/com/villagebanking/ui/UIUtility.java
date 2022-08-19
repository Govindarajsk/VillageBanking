package com.villagebanking.ui;

import android.content.Context;
import android.media.metrics.Event;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Controls.AutoBox;

import java.security.PublicKey;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UIUtility {

    //region Constants
    public static final String V_NUMBER = "NUMBER";
    public static final String V_STRING = "STRING";
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

    public static String getCurrentDate() {
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        return date;
    }
    //endregion

    //region AutoCompleteBox

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static AutoBox getAutoBox(Context cnxt, ArrayList<BOKeyValue> list, AutoCompleteTextView control, AdapterView.OnItemClickListener listener, long... keys) {
        AutoBox autoBox = new AutoBox(cnxt, list);
        autoBox.LoadAutoBox(control, listener, keys);
        return autoBox;
    }

    public static BOKeyValue GetAutoBoxSelected(AutoCompleteTextView control) {
        BOKeyValue selectedValue = ((AutoBox) control.getAdapter()).getSelectedItem();
        return selectedValue;
    }
    //endregion

    //region Other
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
    //endregion

    //region Get/Apply
    public static void applyValue(TextView txtView, Object value) {
        String val = value != null ? value.toString() : "";
        if (value instanceof Double) {
            DecimalFormat formater = new DecimalFormat("0.00");
        }
        txtView.setText(val);
    }

    //endregion

    //region Converters
    public static String ToString(long input) {
        return input > 0 ? String.valueOf(input) : "";
    }

    public static String ToString(Double input) {
        return input > 0 ? String.valueOf(input) : "";
    }

    public static String ToString(TextView input) {
        return input != null && input.getText().length() > 0 ?
                input.getText().toString() : "";
    }

    public static Double ToDouble(String input) {
        return input.length() > 0 ? Double.parseDouble(input) : 0;
    }

    public static Double ToDouble(TextView input) {
        return input != null && input.getText().length() > 0 ?
                Double.parseDouble(input.getText().toString()) : 0;
    }

    public static Long ToLong(TextView input) {
        return input != null && input.getText().length() > 0 ?
                Long.parseLong(input.getText().toString()) : 0;
    }
    public static Long ToLong(String input) {
        return input != null && input.length() > 0 ?
                Long.parseLong(input) : 0;
    }
    //endregion
}
