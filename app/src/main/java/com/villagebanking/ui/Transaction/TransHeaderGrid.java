package com.villagebanking.ui.Transaction;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class TransHeaderGrid<T> extends ArrayAdapter {
    public TransHeaderGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
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
        View convertView = inflater.inflate(R.layout.trans_header_gridview, null);

        BOTransHeader bindData = (BOTransHeader) data;
        String value1 = bindData.getRemarks() + "-" + bindData.getPrimary_key();
        String value2 = String.valueOf(bindData.getPaidAmount());
        String value3 = String.valueOf(bindData.getBalanceAmount());
        String value4 = null;

        TextView txtDetails = ((TextView) convertView.findViewById(R.id.txtDetails));
        TextView txtAmount = ((TextView) convertView.findViewById(R.id.txtAmount));
        TextView txtNewAmount = ((TextView) convertView.findViewById(R.id.txtNewAmount));

        GridView grdView = ((GridView) convertView.findViewById(R.id.gvTransDetail));
        TransGrid adapter = new TransGrid(this.getContext(), R.layout.trans_gridview,
                bindData.getTransDetails());
        grdView.setAdapter(adapter);

        grdView.setOnFocusChangeListener(onFocus(bindData, txtAmount, txtNewAmount));
        grdView.addOnLayoutChangeListener(layoutChanged(bindData, txtAmount, txtNewAmount));

        txtDetails.setText(value1);
        txtAmount.setText(value2);
        txtNewAmount.setText(value3);

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData));

        ImageButton btnDetail = ((ImageButton) convertView.findViewById(R.id.btnDetail));
        btnDetail.setOnClickListener(detailMethod(bindData));

        return convertView;
    }

    View.OnClickListener deleteMethod(BOTransHeader transHeader) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transHeader.getTransDetails().size() == 0)
                    DBUtility.DTOdelete(transHeader.getPrimary_key(), tblTransHeader.Name);
            }
        };
    }

    View.OnClickListener detailMethod(BOTransHeader transHeader) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("PAGE", tblTransHeader.Name);
                args.putLong("ID", transHeader.getPrimary_key());
                Navigation.findNavController(view).navigate(R.id.nav_trans_detail_grid);
            }
        };
    }

    AdapterView.OnFocusChangeListener onFocus(BOTransHeader transHeader, TextView txtView1, TextView txtView2) {
        return new AdapterView.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    txtView1.setText(String.valueOf(transHeader.getTransDetails().stream().mapToDouble(x -> x.getAmount()).sum()));
                    txtView1.setText(String.valueOf(transHeader.getTransDetails().stream().mapToDouble(x -> x.getAmount()).sum()));
                } else {

                }
            }
        };
    }

    AdapterView.OnLayoutChangeListener layoutChanged(BOTransHeader transHeader, TextView txtView1, TextView txtView2) {
        return new AdapterView.OnLayoutChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                txtView1.setText(String.valueOf(transHeader.getTransDetails().stream().mapToDouble(x -> x.getAmount()).sum()));
                txtView1.setText(String.valueOf(transHeader.getTransDetails().stream().mapToDouble(x -> x.getAmount()).sum()));
            }
        };
    }

}