package com.villagebanking.ui.Loan;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.ui.Base.DataGridBase;
import com.villagebanking.DBTables.tblLoanHeader;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class LoanHeaderGrid<T> extends DataGridBase<BOLoanHeader> {

    public LoanHeaderGrid(Context context, int textViewResourceId, ArrayList<BOLoanHeader> list) {
        super(context, textViewResourceId, list);
    }

    @Override
    public View customeView(int row, BOLoanHeader bindData,LayoutInflater layoutBase) {
        View convertView = layoutBase.inflate(R.layout.loan_header_gridview, null);

        String value1 = String.valueOf(row);
        String value2 = bindData.getTransDate();
        String value3 = Double.toString(bindData.getRepayAmount());
        String value4 = bindData.getGroup().getDisplayValue();

        TextView txtSNo = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView txtTransDate = ((TextView) convertView.findViewById(R.id.txtTransDate));
        TextView lblRepayAmount = ((TextView) convertView.findViewById(R.id.lblRepayAmount));
        TextView txtGroupName = ((TextView) convertView.findViewById(R.id.txtGroupName));

        txtSNo.setText(value1);
        txtTransDate.setText(value2);
        lblRepayAmount.setText(value3);
        txtGroupName.setText(value4);

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData.getPrimary_key()));

        ImageButton btnEMI = ((ImageButton) convertView.findViewById(R.id.btnEMI));
        btnEMI.setOnClickListener(showLoanEMI(bindData));

        return convertView;
    }

    View.OnClickListener deleteMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, tblLoanHeader.Name);
                Navigation.findNavController(view).navigate(R.id.nav_loan_header_view);
            }
        };
    }

    View.OnClickListener showLoanEMI(BOLoanHeader loanHeader) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loanHeader.getPaySummary().size() > 0) {
                    Bundle args = new Bundle();
                    args.putString("PAGE", tblLoanHeader.Name);
                    args.putLong("ID", loanHeader.getPrimary_key());
                    Navigation.findNavController(view).navigate(R.id.nav_loan_header_view, args);
                }
            }
        };
    }
}