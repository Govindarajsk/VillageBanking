package com.villagebanking.ui.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPerson;
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
        String value2 = bindData.getFName();
        String value3 = bindData.getLName();
        String value4 = Long.toString(bindData.getMobile());

        TextView txtSNo = convertView.findViewById(R.id.txtSNo);
        TextView txtFirstName = convertView.findViewById(R.id.txtFirstName);
        TextView txtLastName = convertView.findViewById(R.id.txtLastName);
        TextView txtPhoneNo = convertView.findViewById(R.id.txtPhoneNo);

        txtSNo.setText(value1);
        txtFirstName.setText(value2);
        txtLastName.setText(value3);
        txtPhoneNo.setText(value4);

        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);
        ImageButton btnEdit = convertView.findViewById(R.id.btnEdit);
        ImageButton btnLoan = convertView.findViewById(R.id.btnLoan);

        btnDelete.setOnClickListener(delete(bindData.getPrimary_key()));
        btnEdit.setOnClickListener(edit(bindData));
        btnLoan.setOnClickListener(loanMethod(bindData));

        return convertView;
    }

    View.OnClickListener delete(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, tblPerson.Name);
                Navigation.findNavController(view).navigate(R.id.nav_person_grid_view);
            }
        };
    }

    View.OnClickListener edit(BOPerson bindData) {
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

    View.OnClickListener loanMethod(BOPerson bindData) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("PAGE", tblPerson.Name);
                args.putLong("ID", bindData.getPrimary_key());
                Navigation.findNavController(view).navigate(R.id.nav_loan_header_view, args);
            }
        };
    }
}
