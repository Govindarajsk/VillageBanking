package com.villagebanking.ui.Transaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.ui.Base.DataGridBase;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class TransHeaderGrid<T> extends DataGridBase<BOTransDetail> {
    public TransHeaderGrid(Context context, int textViewResourceId, ArrayList<BOTransDetail> objects) {
        super(context, textViewResourceId, objects);

    }

    @Override
    public View customeView(int row, BOTransDetail bindData, LayoutInflater layout) {
        View convertView = layout.inflate(R.layout.trans_header_gridview, null);

        String value1 = String.valueOf(bindData.getPeriodKey());
        String value2 = String.valueOf(bindData.getTransDate());
        String value3 = String.valueOf(bindData.getBalanceAmount());
        String value4 = String.valueOf(bindData.gettD_Period_Key());

        TextView txtDetails = convertView.findViewById(R.id.txtDetails);
        TextView txtAmount = convertView.findViewById(R.id.txtAmount);
        TextView txtNewAmount = convertView.findViewById(R.id.txtNewAmount);

        TextView txtRemarks = convertView.findViewById(R.id.txtRemarks);

        txtAmount.setText(value2);
        txtNewAmount.setText(value3);
        txtDetails.setText(value1);
        txtRemarks.setText(value4);

        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(deleteMethod(bindData));

        ImageButton btnDetail = convertView.findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(detailMethod(bindData));

        CheckBox chkIsFull = convertView.findViewById(R.id.chkIsFull);
        chkIsFull.setOnClickListener(clickCheckBox(bindData, txtAmount));

        boolean isFullyPaid = bindData.getTotalAmount()-bindData.getPaidAmount() == 0;
        chkIsFull.setChecked(isFullyPaid);
        chkIsFull.setEnabled(!isFullyPaid);

        btnDelete.setEnabled(!bindData.IsLastMonth);

        if(bindData.getParentKey()>0){
            txtDetails.setVisibility(View.GONE);
            txtRemarks.setVisibility(View.GONE);
        }

        return convertView;
    }

    View.OnClickListener deleteMethod(BOTransDetail transHeader) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (transHeader.getTransDetails().size() == 0)
                    DBUtility.DTOdelete(transHeader.getHeaderKey(), tblTransHeader.Name);

            }
        };
    }

    View.OnClickListener detailMethod(BOTransDetail transHeader) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("PAGE", tblTransHeader.Name);
                args.putLong("ID", transHeader.getHeaderKey());
                Navigation.findNavController(view).navigate(R.id.nav_trans_detail_grid);
            }
        };
    }

    View.OnClickListener clickCheckBox(BOTransDetail bindData, TextView txtView) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox chkBox = ((CheckBox) view);
                boolean checked = chkBox.isChecked();
                bindData.IsNew = checked;
                double newAmount = 0.0;
                if (checked) {

                    if (checked && bindData.getPrimary_key() == 0) {
                        newAmount = bindData.getBalanceAmount();
                    } else if (bindData.getPrimary_key() > 0) {
                        newAmount = bindData.getBalanceAmount();
                    }
                    txtView.setText(String.valueOf(newAmount));
                } else {
                    txtView.setText("0.00");
                }
                bindData.setPaidAmount(newAmount);
                double delTotal = bindData.getTotalAmount() - newAmount;
                bindData.setBalanceAmount(delTotal);
            }
        };
    }
}