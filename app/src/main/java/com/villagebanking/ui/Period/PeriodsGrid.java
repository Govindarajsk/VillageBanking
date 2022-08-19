package com.villagebanking.ui.Period;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblUtility;
import com.villagebanking.ui.Base.DataGridBase;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblLoanHeader;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.ui.UIUtility;

import java.util.ArrayList;
import java.util.stream.LongStream;

public class PeriodsGrid<T> extends DataGridBase<BOPeriod> {

    //region Initialize
    public PeriodsGrid(Context context, int textViewResourceId, ArrayList<BOPeriod> list) {
        super(context, textViewResourceId, list);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View customeView(int row, BOPeriod bindData, LayoutInflater layout) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.periods_gridview, null);

        String value0 = Long.toString(row);
        TextView txtSNo = convertView.findViewById(R.id.txtSNo);
        txtSNo.setText(value0);

        String value2 = bindData.getPeriodName() + "-" + bindData.getPeriodStatus() + "-" + bindData.getPrimary_key();

        ArrayList<BOTransHeader> transHeaders = tblTransHeader.GetViewList(tblTransHeader.Name,
                UIUtility.ToString(bindData.getPrimary_key()), "");
        String value3 = bindData.getPeriodType() + "-" + bindData.getActualDate() + "-" + transHeaders.size();

        TextView txtPeriodType = convertView.findViewById(R.id.txtPeriodType);
        TextView txtActualDate = convertView.findViewById(R.id.txtActualDate);

        txtPeriodType.setText(value2);
        txtActualDate.setText(value3);

        long size = transHeaders.size();

        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(delete(bindData.getPrimary_key(), size));

        ImageButton btnEdit = convertView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(edit(bindData));

        ImageButton btnDetail = convertView.findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(callTransHeader(bindData, size));

        ImageButton btnOpen = convertView.findViewById(R.id.btnClose);
        btnOpen.setOnClickListener(openPeriod(bindData));

        return convertView;
    }

    //endregion

    //region Events
    View.OnClickListener delete(long primaryKey, long transHeaderCount) {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                // if (transHeaderCount == 0) {
                //DBUtility.DTOdelete(primaryKey, tblPeriod.Name);
                tblPeriod.UpdateStatus(primaryKey, "C");
                Navigation.findNavController(view).navigate(R.id.nav_period_grid_view);
                //  }
            }
        };
    }

    View.OnClickListener edit(BOPeriod bindData) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putLong("primary_key", bindData.getPrimary_key());
                Navigation.findNavController(view).navigate(R.id.nav_period_edit_view, args);
            }
        };
    }

    View.OnClickListener callTransHeader(BOPeriod bindData, long transHeaderCount) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if (transHeaderCount > 0) {
                    Bundle args = new Bundle();
                    args.putString("PAGE", tblPeriod.Name);
                    args.putLong("ID", bindData.getPrimary_key());
                    Navigation.findNavController(view).navigate(R.id.nav_linkview_trans_header, args);
             //   }
            }
        };
    }

    View.OnClickListener openPeriod(BOPeriod bindData) {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                genTransHeader(bindData, view);
                tblPeriod.UpdateStatus(bindData.getPrimary_key(), "A");
            }
        };
    }
    //endregion

    String keys = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    void genTransHeader(BOPeriod period, View view) {
        ArrayList<BOPeriod> boPeriods = tblPeriod.getNextNPeriods(period.getPrimary_key(), 0);

        LongStream keyStream = boPeriods.stream().mapToLong(x -> x.getPrimary_key());
        keyStream.forEach(x -> keys += (keys.length() > 0 ? "," : "") + UIUtility.ToString(x));

        ArrayList<BOTransHeader> transPerson = tblTransHeader.GetViewList(tblGroupPersonLink.Name, keys, "");
        ArrayList<BOTransHeader> transLoan = tblTransHeader.GetViewList(tblLoanHeader.Name, keys, "");

        transPerson.addAll(transLoan);

        transPerson.forEach(x -> generateTransaction(x, period));

        Navigation.findNavController(view).navigate(R.id.nav_period_grid_view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void generateTransaction(BOTransHeader x, BOPeriod period) {
        x.setTransDate(period.getActualDate());
        long periodKey = period.getPrimary_key();

        Long primaryKey = getPrimaryKey(periodKey, tblGroupPersonLink.Name, x.getTableLinkKey());
        x.setPrimary_key(primaryKey);
        //x.setPeriodKey(periodKey);
        BOTransHeader transHeader = tblUtility.GetTData(tblTransHeader.GetList(primaryKey));

        if (transHeader == null) {
            tblTransHeader.Save("I", x);
        } else {
            tblTransHeader.Save("U", x);
        }
    }

    long getPrimaryKey(long periodKey, String tblName, long linkKey) {
        long key_1 = periodKey;
        long key_2 = tblName.equals(tblGroupPersonLink.Name) ? 0 : 1;
        long key_3 = linkKey;
        Long key = Long.valueOf(key_1 + "" + key_2 + "" + key_3);
        return key;
    }
}