package com.villagebanking.ui.Transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPersonTransaction;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class PersonTransGrid<T> extends ArrayAdapter {
    public PersonTransGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T data = (T) super.getItem(position);
        convertView = customeView(position + 1, data);
        return convertView;
    }

    private View customeView(int row, T data) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.listview_persons_trans, null);

        BOPersonTransaction bindData = (BOPersonTransaction) data;
        String value1 = bindData.getRemarks()+"-"+bindData.getPrimary_key();
        String value2 = String.valueOf(bindData.getActualAmount());
        String value3 = String.valueOf(bindData.getNewAmount());
        String value4 = null;

        TextView txtDetails = ((TextView) convertView.findViewById(R.id.txtDetails));
        TextView txtAmount = ((TextView) convertView.findViewById(R.id.txtAmount));
        TextView txtNewAmount = ((TextView) convertView.findViewById(R.id.txtNewAmount));

        txtDetails.setText(value1);
        txtAmount.setText(value2);
        txtNewAmount.setText(value3);


        CheckBox chkIsFull = ((CheckBox) convertView.findViewById(R.id.chkIsFull));
        //chkIsFull.setChecked(true);

        chkIsFull.setOnClickListener(clickCheckBox(bindData, txtNewAmount));

        Button btnDelete = ((Button) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(clickMethod(bindData.getPrimary_key()));
        if (bindData.getNewAmount() > 0) {
            btnDelete.setVisibility(View.VISIBLE);
            chkIsFull.setVisibility(View.GONE);

        } else {
            btnDelete.setVisibility(View.GONE);
            chkIsFull.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    View.OnClickListener clickMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (primaryKey > 0)
                    DBUtility.DTOdelete(primaryKey, DB1Tables.PERSON_TRANSACTION);
                //Navigation.findNavController(view).navigate(R.id.nav_linkview_person_trans);
            }
        };
    }

    View.OnClickListener clickCheckBox(BOPersonTransaction bindData, TextView txtView) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    txtView.setText(bindData.getActualAmount().toString());
                    bindData.setNewAmount(bindData.getActualAmount());
                } else {
                    // txtView.setText("0.00");
                    //bindData.setNewAmount(0.00);
                }

            }
        };
    }
}