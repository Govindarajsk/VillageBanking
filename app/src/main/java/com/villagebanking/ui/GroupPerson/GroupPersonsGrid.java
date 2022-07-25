package com.villagebanking.ui.GroupPerson;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class GroupPersonsGrid<T> extends ArrayAdapter {
    public GroupPersonsGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
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

        BOGroupPersonLink bindData = (BOGroupPersonLink) data;
        String value1 = Integer.toString(row);
        String value2 = "";
        String value3 = "";
        String value4 = "";
        long person_key = 0;
        long group_key = 0;

        BOKeyValue person = bindData.PersonDetail;
        if (person != null) {
            value2 = person.getDisplayValue();
        }
        BOKeyValue group = bindData.GroupDetail;
        if (group != null) {
            value3 = group.getDisplayValue();
        }

        TextView txtSNo = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView txtFirstName = ((TextView) convertView.findViewById(R.id.txtFirstName));
        TextView txtLastName = ((TextView) convertView.findViewById(R.id.txtLastName));
        TextView txtPhoneNo = ((TextView) convertView.findViewById(R.id.txtPhoneNo));

        txtSNo.setText(value1);
        txtFirstName.setText(value2);
        txtLastName.setText(value3);
        txtPhoneNo.setText(value4);
        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData));

        if (person_key > 0 && group_key > 0) {
            ImageButton btnEdit = ((ImageButton) convertView.findViewById(R.id.btnEdit));
            btnEdit.setOnClickListener(editMethod(group_key, person_key));
        }
        return convertView;
    }

    View.OnClickListener deleteMethod(BOGroupPersonLink boGroupPerson) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(boGroupPerson.getPrimary_key(), tblGroupPersonLink.Name);
                Bundle args = new Bundle();
                args.putLong("group_key", boGroupPerson.getGroup_Key());
                Navigation.findNavController(view).navigate(R.id.nav_linkview_group_person, args);
            }
        };
    }

    View.OnClickListener editMethod(long group_key, long person_key) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putLong("group_key", group_key);
                args.putLong("person_key", person_key);
                Navigation.findNavController(view).navigate(R.id.nav_linkview_person_trans, args);
            }
        };
    }
}