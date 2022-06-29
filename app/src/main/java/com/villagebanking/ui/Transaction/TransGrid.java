package com.villagebanking.ui.Transaction;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPersonTrans;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.R;

import java.util.ArrayList;

public class TransGrid<T> extends ArrayAdapter {
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
        View convertView = inflater.inflate(R.layout.trans_gridview, null);

        BOTransDetail bindData = (BOTransDetail) data;
        String value1 = Integer.toString(row);
        String value2 = bindData.getRemarks() + "-" + bindData.getPrimary_key() + "-" + bindData.getHeaderKey();
        String value3 = bindData.getTableName();
        String value4 = Double.toString(bindData.getAmount());
        String value5 = Double.toString(bindData.getBalanceAmount());

        //TextView txtSNo = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView txtRemarks = ((TextView) convertView.findViewById(R.id.txtRemarks));
        TextView txtTableName = ((TextView) convertView.findViewById(R.id.txtTableName));
        TextView txtAmount = ((TextView) convertView.findViewById(R.id.txtAmount));
        TextView txtBalAmount = ((TextView) convertView.findViewById(R.id.txtBalAmount));

        //txtSNo.setText(value1);
        txtRemarks.setText(value1);
        txtTableName.setText(value3);
        txtAmount.setText(value4);
        txtBalAmount.setText(value5);

        txtAmount.addTextChangedListener(textChangedEvnt(bindData, txtBalAmount));

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData.getPrimary_key()));
        return convertView;
    }

    View.OnClickListener deleteMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, tblTransHeader.Name);
                Navigation.findNavController(view).navigate(R.id.nav_trans_grid_view);
            }
        };
    }

    TextWatcher textChangedEvnt(BOTransDetail bindData, TextView txtBalAmount) {
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                bindData.setAmount(s != null ? Double.valueOf(s.toString()) : 0.00);
                Double balAmount = bindData.getBalanceAmount() - bindData.getAmount();
                txtBalAmount.setText(String.valueOf(balAmount));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ShowMessage(context, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bindData.setAmount(s != null ? Double.valueOf(s.toString()) : 0.00);
            }
        };
        return fieldValidatorTextWatcher;
    }
}