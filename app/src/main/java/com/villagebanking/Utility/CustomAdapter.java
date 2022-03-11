package com.villagebanking.Utility;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTransaction;
import com.villagebanking.R;

import java.util.ArrayList;

public class CustomAdapter<T> extends ArrayAdapter {
    String displayType;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<T> objects, String _reqstType) {
        super(context, textViewResourceId, objects);
        displayType = _reqstType;
    }

    @Override
    public void setDropDownViewResource(int resource) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, null);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        T data = (T) super.getItem(position);
        convertView = customeView(position + 1, data);
        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T data = (T) super.getItem(position);
        convertView = customeView(position + 1, data);
        return convertView;
    }

    private View customeView(int row, T data) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.activity_gridview, null);
        String value1, value2 = "", value3 = "", value4 = "", value5 = "";

        value1 = Integer.toString(row);

        if (data instanceof BOGroupPersonLink) {
            BOGroupPersonLink bindData = (BOGroupPersonLink) data;
            value2 = Integer.toString(bindData.getGroup_key());
            value3 = Integer.toString(bindData.getPerson_key());
            value4 = Integer.toString(bindData.getOrderBy());
            value5 = bindData.getPerson_role();
        } else if (data instanceof BOPerson) {
            BOPerson bindData = (BOPerson) data;
            value2 = bindData.getStrFName() + "-" + bindData.getKeyID();
            value3 = bindData.getStrLName();
            value4 = Long.toString(bindData.getNumMobile());
            value5 = bindData.getReference1() + " & " + bindData.getReference2();
        } else if (data instanceof BOGroup) {
            BOGroup bindData = (BOGroup) data;
            value2 = bindData.getName();
            value3 = String.format("%.2f", bindData.getAmount());
            value4 = Integer.toString(bindData.getNoOfPerson());
            value5 = bindData.getStatus();
        } else if (data instanceof BOPersonTransaction) {
            BOPersonTransaction bindData = (BOPersonTransaction) data;
            value2 = bindData.getLinkName();
            value3 = String.format("%.2f", bindData.getCalcAmount());
            value4 = String.format("%.2f", bindData.getAmount());
        }
        TextView column1 = ((TextView) convertView.findViewById(R.id.txtColumn1));
        TextView column2 = ((TextView) convertView.findViewById(R.id.txtColumn2));
        TextView column3 = ((TextView) convertView.findViewById(R.id.txtColumn3));
        TextView column4 = ((TextView) convertView.findViewById(R.id.txtColumn4));


        column1.setText(value1);
        column2.setText(value2);
        column3.setText(value3);
        column4.setText(value4);
        return dgAllignment(convertView,displayType);
    }

    View dgAllignment(View convertView,String type) {

        TextView column1 = ((TextView) convertView.findViewById(R.id.txtColumn1));
        TextView column2 = ((TextView) convertView.findViewById(R.id.txtColumn2));
        TextView column3 = ((TextView) convertView.findViewById(R.id.txtColumn3));
        TextView column4 = ((TextView) convertView.findViewById(R.id.txtColumn4));

        switch (type) {
            case "A":
                column1.setVisibility(View.VISIBLE);
                column2.setVisibility(View.VISIBLE);
                column3.setVisibility(View.VISIBLE);
                column4.setVisibility(View.VISIBLE);
                column1.setWidth(100);
                column2.setWidth(300);
                column3.setWidth(300);
                column4.setWidth(300);
                break;
            case "B":
                column2.setVisibility(View.VISIBLE);
                break;
            case "C":
                column2.setVisibility(View.VISIBLE);
                break;
        }
        return convertView;
    }
}
