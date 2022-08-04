package com.villagebanking.ui.Transaction;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.Controls.DataGridBase;
import com.villagebanking.DBTables.tblLoanHeader;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class TransHeaderGrid<T> extends DataGridBase {
    public TransHeaderGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
        super(context, textViewResourceId, objects);

    }
    @Override
    public View customeView(int row, Object data) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.trans_header_gridview, null);

        BOTransHeader bindData = (BOTransHeader) data;
        String value1 = bindData.getLinkDetail1().getDisplayValue();
        String value2 = String.valueOf(bindData.getPaidAmount());
        String value3 = String.valueOf(bindData.getBalanceAmount());
        String value4 = bindData.getLinkDetail2().getDisplayValue();

        TextView txtDetails = ((TextView) convertView.findViewById(R.id.txtDetails));
        TextView txtAmount = ((TextView) convertView.findViewById(R.id.txtAmount));
        TextView txtNewAmount = ((TextView) convertView.findViewById(R.id.txtNewAmount));

        TextView txtRemarks = ((TextView) convertView.findViewById(R.id.txtRemarks));

        txtDetails.setText(value1);
        txtAmount.setText(value2);
        txtNewAmount.setText(value3);
        txtRemarks.setText(value4);

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData));

        ImageButton btnDetail = ((ImageButton) convertView.findViewById(R.id.btnDetail));
        btnDetail.setOnClickListener(detailMethod(bindData));

        CheckBox chkIsFull = ((CheckBox) convertView.findViewById(R.id.chkIsFull));
        chkIsFull.setOnClickListener(clickCheckBox(bindData, txtAmount));

        boolean isFullyPaid = bindData.getBalanceAmount() == 0;
        chkIsFull.setChecked(isFullyPaid);
        chkIsFull.setEnabled(!isFullyPaid);
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

    View.OnClickListener clickCheckBox(BOTransHeader bindData, TextView txtView) {
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