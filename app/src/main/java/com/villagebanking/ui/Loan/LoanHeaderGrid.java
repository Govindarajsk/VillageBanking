package com.villagebanking.ui.Loan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.Controls.DataGridBase;
import com.villagebanking.DBTables.tblLoanHeader;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class LoanHeaderGrid<T> extends DataGridBase {

    public LoanHeaderGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View customeView(int row, Object data) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.loan_header_gridview, null);

        BOLoanHeader bindData = (BOLoanHeader) data;
        String value1 = bindData.getTransDate();
        String value2 = bindData.getPersonKey().getDisplayValue();
        String value3 = Double.toString(bindData.getLoanAmount());//bindData.getGroupKey().getDisplayValue();
        String value4 = Double.toString(bindData.getRepayAmount());

        TextView txtSNo = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView txtPersonName = ((TextView) convertView.findViewById(R.id.txtPersonName));
        TextView txtGroupName = ((TextView) convertView.findViewById(R.id.txtGroupName));
        TextView txtLoanAmount = ((TextView) convertView.findViewById(R.id.txtLoanAmount));

        txtSNo.setText(value1);
        txtPersonName.setText(value2);
        txtGroupName.setText(value3);
        txtLoanAmount.setText(value4);

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData.getPrimary_key()));
        return convertView;
    }

    View.OnClickListener deleteMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, tblLoanHeader.Name);
                Navigation.findNavController(view).navigate(R.id.nav_loan_grid_view);
            }
        };
    }
}