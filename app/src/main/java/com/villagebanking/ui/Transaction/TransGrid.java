package com.villagebanking.ui.Transaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTransaction;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class TransGrid <T> extends ArrayAdapter {
    public TransGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
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
        View convertView = inflater.inflate(R.layout.listview_trans, null);

        BOPersonTransaction bindData = (BOPersonTransaction) data;
        String value1 = Integer.toString(row);
        String value2 = bindData.getRemarks() + "-" + bindData.getPrimary_key();
        String value3 = bindData.getTableName()+bindData.getForien_key();
        String value4 = Double.toString(bindData.getNewAmount());

        TextView txtSNo = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView txtRemarks = ((TextView) convertView.findViewById(R.id.txtRemarks));
        TextView txtTableName = ((TextView) convertView.findViewById(R.id.txtTableName));
        TextView txtAmount = ((TextView) convertView.findViewById(R.id.txtAmount));

        txtSNo.setText(value1);
        txtRemarks.setText(value2);
        txtTableName.setText(value3);
        txtAmount.setText(value4);

        Button btnDelete = ((Button) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData.getPrimary_key()));
        Button btnEdit = ((Button) convertView.findViewById(R.id.btnEdit));
        return convertView;
    }
    View.OnClickListener deleteMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, DB1Tables.PERSON_TRANSACTION);
            }
        };
    }
    View.OnClickListener editMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args=new Bundle();
                args.putLong("primary_key",primaryKey);
                Navigation.findNavController(view).navigate(R.id.nav_trans_edit_view,args);
            }
        };
    }
}