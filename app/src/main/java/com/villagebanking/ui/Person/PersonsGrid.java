package com.villagebanking.ui.Person;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.DBTables.tblPerson;

import java.util.ArrayList;

public class PersonsGrid<T> extends ArrayAdapter {
    public PersonsGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
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
        String value2 = bindData.getStrFName();// + "-" + bindData.getPrimary_key();
        String value3 = bindData.getStrLName();
        String value4 = Long.toString(bindData.getNumMobile());

        TextView txtSNo = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView txtFirstName = ((TextView) convertView.findViewById(R.id.txtFirstName));
        TextView txtLastName = ((TextView) convertView.findViewById(R.id.txtLastName));
        TextView txtPhoneNo = ((TextView) convertView.findViewById(R.id.txtPhoneNo));

        txtSNo.setText(value1);
        txtFirstName.setText(value2);
        txtLastName.setText(value3);
        txtPhoneNo.setText(value4);
        ImageButton btnDelete = (ImageButton) convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(deleteMethod(bindData.getPrimary_key()));
        ImageButton btnEdit = ((ImageButton) convertView.findViewById(R.id.btnEdit));
        btnEdit.setOnClickListener(editMethod(bindData));
        return convertView;
    }

    View.OnClickListener deleteMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, DB1Tables.PERSONS);
                Navigation.findNavController(view).navigate(R.id.nav_person_grid_view);
            }
        };
    }

    View.OnClickListener editMethod(BOPerson bindData) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("PAGE", tblPerson.Name);
                args.putLong("ID", bindData.getPrimary_key());
                Navigation.findNavController(view).navigate(R.id.nav_linkview_trans_header, args);
            }
        };
    }
}
