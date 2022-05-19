package com.villagebanking.Controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.villagebanking.R;

import java.util.ArrayList;

public class CCDataGrid<T> extends ArrayAdapter {
    public CCDataGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
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
        View convertView = inflater.inflate(R.layout.app_dropview,null);

        String bindData = data!=null? data.toString():"";
        TextView column1 = ((TextView) convertView.findViewById(R.id.txtDropdownValue));

        column1.setText(bindData);
        return convertView;
    }
}
