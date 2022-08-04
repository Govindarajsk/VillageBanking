package com.villagebanking.ui.Loan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOLoanDetail;
import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.Controls.DataGridBase;
import com.villagebanking.DBTables.tblLoanDetail;
import com.villagebanking.DBTables.tblLoanHeader;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.ui.UIUtility;

import java.util.ArrayList;

public class LoanDetailGrid <T> extends DataGridBase {

    public LoanDetailGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View customeView(int row, Object data) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.loan_detail_gridview, null);

        BOLoanDetail bindData = (BOLoanDetail) data;
        String value1 = UIUtility.ToString(bindData.getEmiNo());
        String value2 = bindData.getPeriodInfo().getDisplayValue();
        String value3 =  UIUtility.ToString(bindData.getEmiAmount());

        TextView txtInstallment = ((TextView) convertView.findViewById(R.id.txtInstallment));
        TextView txtPeriodName = ((TextView) convertView.findViewById(R.id.txtPeriodName));
        TextView txtInstallAmount = ((TextView) convertView.findViewById(R.id.txtInstallAmount));

        txtInstallment.setText(value1);
        txtPeriodName.setText(value2);
        txtInstallAmount.setText(value3);

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData.getPrimary_key()));
        return convertView;
    }
    View.OnClickListener deleteMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, tblLoanDetail.Name);
            }
        };
    }
}
