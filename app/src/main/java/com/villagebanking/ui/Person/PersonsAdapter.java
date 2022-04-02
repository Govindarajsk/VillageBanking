package com.villagebanking.ui.Person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.R;

import java.util.ArrayList;

public class PersonsAdapter<T> extends ArrayAdapter {
    public PersonsAdapter(Context context, int textViewResourceId, ArrayList<T> objects) {
        super(context, textViewResourceId, objects);
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
        View convertView = inflater.inflate(R.layout.persons_gridview, null);

        BOPerson bindData = (BOPerson) data;
        String value1 = Integer.toString(row);
        String value2 = bindData.getStrFName() + "-" + bindData.getKeyID();
        String value3 = bindData.getStrLName();
        String value4 = Long.toString(bindData.getNumMobile());

        TextView column1 = ((TextView) convertView.findViewById(R.id.txtColumn1));
        TextView column2 = ((TextView) convertView.findViewById(R.id.txtColumn2));
        TextView column3 = ((TextView) convertView.findViewById(R.id.txtColumn3));
        TextView column4 = ((TextView) convertView.findViewById(R.id.txtColumn4));

        column1.setText(value1);
        column2.setText(value2);
        column3.setText(value3);
        column4.setText(value4);
        ApplyColor(convertView, row);
        return convertView;
    }

    void ApplyColor(View convertView, int row) {
        GridLayout grdlyout = ((GridLayout) convertView.findViewById(R.id.maingrid));
        if (row % 2 == 0) {
            grdlyout.setBackgroundColor(0xffffff);
        } else {
            grdlyout.setBackgroundColor(0xebe6e6);
        }
    }
}