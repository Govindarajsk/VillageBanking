package com.villagebanking.ui.Period;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblLoanHeader;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.ui.UIUtility;

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
        String value2 = bindData.getPeriodName();

        ArrayList<BOTransHeader> transHeaders =
                DBUtility.FetchPeriodTrans(tblTransHeader.Name, String.valueOf(bindData.getPrimary_key()));

        String value3 = bindData.getActualDate() + "-" + transHeaders.size();

        TextView column1 = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView column2 = ((TextView) convertView.findViewById(R.id.txtPeriodType));
        TextView column3 = ((TextView) convertView.findViewById(R.id.txtActualDate));

        column1.setText(value1);
        column2.setText(value2);
        column3.setText(value3);

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteGroup(bindData.getPrimary_key(), transHeaders.size()));

        ImageButton btnEdit = ((ImageButton) convertView.findViewById(R.id.btnEdit));
        btnEdit.setOnClickListener(editGroup(bindData));

        ImageButton btnDetail = ((ImageButton) convertView.findViewById(R.id.btnDetail));
        btnDetail.setOnClickListener(openTransHeader(bindData, transHeaders.size()));

        return convertView;
    }

    View.OnClickListener deleteGroup(long primaryKey, long transHeaderCount) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transHeaderCount == 0) {
                    DBUtility.DTOdelete(primaryKey, tblPeriod.Name);
                    Navigation.findNavController(view).navigate(R.id.nav_period_grid_view);
                }
            }
        };
    }

    View.OnClickListener editGroup(BOPeriod bindData) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putLong("primary_key", bindData.getPrimary_key());
                Navigation.findNavController(view).navigate(R.id.nav_period_edit_view, args);
            }
        };
    }

    View.OnClickListener openTransHeader(BOPeriod bindData, long transHeaderCount) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transHeaderCount > 0) {
                    Bundle args = new Bundle();
                    args.putString("PAGE", tblPeriod.Name);
                    args.putLong("ID", bindData.getPrimary_key());
                    Navigation.findNavController(view).navigate(R.id.nav_linkview_trans_header, args);
                }
            }
        };
    }

    View.OnClickListener openPeriod(BOPeriod bindData) {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                periodOpen(bindData, view);
            }
        };
    }

    String keys = "0";

    @RequiresApi(api = Build.VERSION_CODES.N)
    void periodOpen(BOPeriod period, View view) {

        ArrayList<BOPeriod> boPeriods = DBUtility.DBGetDataFilter(tblPeriod.Name, "PERIOD_TYPE",
                UIUtility.ToString(period.getPeriodType()));
        keys = "";

        boPeriods.stream().forEach(x -> keys =
                (
                        x.getPeriodValue() <= period.getPeriodValue() ?
                                (keys.length() > 0 ? (keys + ",") : "") + x.getPrimary_key()
                                : keys
                )
        );

        ArrayList<BOTransHeader> transPerson = DBUtility.FetchPeriodTrans(tblGroupPersonLink.Name, keys);
        ArrayList<BOTransHeader> transLoan = DBUtility.FetchPeriodTrans(tblLoanHeader.Name, keys);

        transPerson.addAll(transLoan);

        String countStr = String.valueOf(transPerson.size());
        transPerson.forEach(x -> generateTransaction(x, period.getPrimary_key()));

        DBUtility.updateField(tblPeriod.Name, "PERIOD_STATUS", countStr, period.getPrimary_key());
        Navigation.findNavController(view).navigate(R.id.nav_period_grid_view);
    }

    void generateTransaction(BOTransHeader x, long periodKey) {
        x.setTransDate(UIUtility.getCurrentDate());
        Long primaryKey = Long.valueOf(periodKey + (x.getTableName().equals(tblGroupPersonLink.Name) ? "0" : "1") +
                x.getTableLinkKey());
        x.setPrimary_key(primaryKey);
        x.setPeriodKey(periodKey);
        DBUtility.DTOInsertUpdate("I", x);
    }
}