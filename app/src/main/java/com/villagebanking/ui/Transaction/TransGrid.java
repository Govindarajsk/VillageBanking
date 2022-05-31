package com.villagebanking.ui.Transaction;

import android.content.Context;
import android.os.Bundle;
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
        View convertView = inflater.inflate(R.layout.trans_gridview, null);

        //BOTransHeader bindData = (BOTransHeader) data;
        BOTransDetail bindData = (BOTransDetail) data;
        String value1 = Integer.toString(row);
        String value2 = bindData.getRemarks() + "-" + bindData.getPrimary_key()+ "-" + bindData.getHeaderKey();
        String value3 = bindData.getTableName();
        String value4 = Double.toString(bindData.getTotalAmount());

        TextView txtSNo = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView txtRemarks = ((TextView) convertView.findViewById(R.id.txtRemarks));
        TextView txtTableName = ((TextView) convertView.findViewById(R.id.txtTableName));
        TextView txtAmount = ((TextView) convertView.findViewById(R.id.txtAmount));

        txtSNo.setText(value1);
        txtRemarks.setText(value2);
        txtTableName.setText(value3);
        txtAmount.setText(value4);

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData.getPrimary_key()));
        return convertView;
    }
    View.OnClickListener deleteMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DBUtility.DTOdelete(primaryKey, tblTransHeader.Name);
                DBUtility.DTOdelete(primaryKey, tblTransDetail.Name);
                Navigation.findNavController(view).navigate(R.id.nav_trans_grid_view);
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