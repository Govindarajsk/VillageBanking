package com.villagebanking.ui.Base;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.villagebanking.R;

import java.util.ArrayList;

public abstract class DataGridBase<T> extends ArrayAdapter {
    public DataGridBase(Context context, int textViewResourceId, ArrayList<T> list) {
        super(context, textViewResourceId, list);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        T data = (T) super.getItem(position);
        convertView = customeView(position + 1, data, inflater);
        return convertView;
    }

    public abstract View customeView(int row, T data, LayoutInflater layout);
}
