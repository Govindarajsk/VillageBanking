package com.villagebanking.ui.Transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.Controls.DataGridBase;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class TransDetailGrid<T> extends DataGridBase {

    public TransDetailGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View customeView(int row, Object data) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.trans_details_gridview, null);

        BOTransDetail bindData = (BOTransDetail) data;
        String value1 = bindData.getTransDate();
        String value2 = bindData.getRemarks();
        String value3 = Double.toString(bindData.getAmount());

        TextView txtTransDate = ((TextView) convertView.findViewById(R.id.txtTransDate));
        TextView txtDetails = ((TextView) convertView.findViewById(R.id.txtDetails));
        TextView txtAmount = ((TextView) convertView.findViewById(R.id.txtAmount));

        txtTransDate.setText(value1);
        txtDetails.setText(value2);
        txtAmount.setText(value3);

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(deleteMethod(bindData.getPrimary_key()));
        return convertView;
    }

    View.OnClickListener deleteMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, tblTransDetail.Name);
                Navigation.findNavController(view).navigate(R.id.nav_trans_grid_view);
            }
        };
    }
}