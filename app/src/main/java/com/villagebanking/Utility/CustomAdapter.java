package com.villagebanking.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
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
        View convertView = inflater.inflate(R.layout.app_gridview, null);
        String value1, value2 = "", value3 = "", value4 = "", value5 = "";

        value1 = Integer.toString(row);

        if (data instanceof BOGroupPersonLink) {
            BOGroupPersonLink bindData = (BOGroupPersonLink) data;
            value2 = Long.toString(bindData.getGroup_Detail().getPrimary_key());
            value3 = Long.toString(bindData.getPerson_Detail().getPrimary_key());
            value4 = Integer.toString(bindData.getOrderBy());
            value5 = bindData.getPerson_role();
        } else if (data instanceof BOPersonTransaction) {
            BOPersonTransaction bindData = (BOPersonTransaction) data;

        }
        TextView column1 = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView column2 = ((TextView) convertView.findViewById(R.id.txtFirstName));
        TextView column3 = ((TextView) convertView.findViewById(R.id.txtLastName));
        TextView column4 = ((TextView) convertView.findViewById(R.id.txtPhoneNo));


       // column1.setText(value1);
        //column2.setText(value2);
        //column3.setText(value3);
       // column4.setText(value4);
        return convertView;
    }

    View dgAllignment(View convertView,String type) {

        TextView column1 = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView column2 = ((TextView) convertView.findViewById(R.id.txtFirstName));
        TextView column3 = ((TextView) convertView.findViewById(R.id.txtLastName));
        TextView column4 = ((TextView) convertView.findViewById(R.id.txtPhoneNo));

        switch (type) {
            case "A":
                column1.setVisibility(View.VISIBLE);
                column2.setVisibility(View.VISIBLE);
                column3.setVisibility(View.VISIBLE);
                column4.setVisibility(View.VISIBLE);
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
