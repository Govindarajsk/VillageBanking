package com.villagebanking.ui.Period;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBSQLQuery;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class PeriodsGrid<T> extends ArrayAdapter {
    public PeriodsGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
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
        View convertView = inflater.inflate(R.layout.periods_gridview, null);

        BOPeriod bindData = (BOPeriod) data;
        String value1 = Long.toString(row);
        String value2 = bindData.getPeriodType() + "-" + bindData.getPeriodName() + "-" + bindData.getPrimary_key();
        String value3 = bindData.getActualDate() + "-" + bindData.getPeriodStatus();

        TextView column1 = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView column2 = ((TextView) convertView.findViewById(R.id.txtPeriodType));
        TextView column3 = ((TextView) convertView.findViewById(R.id.txtActualDate));

        column1.setText(value1);
        column2.setText(value2);
        column3.setText(value3);

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData.getPrimary_key()));

        ImageButton btnEdit = ((ImageButton) convertView.findViewById(R.id.btnEdit));
        btnEdit.setOnClickListener(editMethod(bindData));

        ImageButton btnDetail = ((ImageButton) convertView.findViewById(R.id.btnDetail));
        btnDetail.setOnClickListener(transMethod(bindData));

        ImageButton btnClose = ((ImageButton) convertView.findViewById(R.id.btnClose));
        btnClose.setOnClickListener(closeMethod(bindData));

        return convertView;
    }

    View.OnClickListener deleteMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, DB1Tables.PERIODS);
                Navigation.findNavController(view).navigate(R.id.nav_period_grid_view);
            }
        };
    }

    View.OnClickListener editMethod(BOPeriod bindData) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putLong("primary_key", bindData.getPrimary_key());
                Navigation.findNavController(view).navigate(R.id.nav_period_edit_view, args);
            }
        };
    }

    View.OnClickListener transMethod(BOPeriod bindData) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("PAGE", DB1Tables.PERIODS);
                args.putLong("ID", bindData.getPeriodType());
                Navigation.findNavController(view).navigate(R.id.nav_linkview_period_trans, args);
            }
        };
    }

    View.OnClickListener closeMethod(BOPeriod bindData) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                periodOpen(bindData);
            }
        };
    }

    void periodOpen(BOPeriod period) {
        DBUtility.updateField(DB1Tables.PERIODS, "STATUS", "O", period.getPrimary_key());
    }
}