package com.villagebanking.ui.Period;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.StaticUtility;

import java.util.ArrayList;

public class PeriodsGrid<T> extends ArrayAdapter {
    String requestType="";
    public PeriodsGrid(Context context, int textViewResourceId, ArrayList<T> objects,String requestType) {
        super(context, textViewResourceId, objects);
        this.requestType=requestType;
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
        View convertView = inflater.inflate(R.layout.listview_periods, null);

        BOPeriod bindData = (BOPeriod) data;
        String value1 = Long.toString(row);
        String value2 = bindData.getPeriodType() + "-" + bindData.getPeriodName();
        String value3 = bindData.getActualDate();//String.valueOf(bindData.getActualDate()!=null?bindData.getActualDate():"");

        TextView column1 = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView column2 = ((TextView) convertView.findViewById(R.id.txtPeriodType));
        TextView column3 = ((TextView) convertView.findViewById(R.id.txtActualDate));

        column1.setText(value1);
        column2.setText(value2);
        column3.setText(value3);

        Button btnDelete = ((Button) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(clickMethod(bindData.getPrimary_key()));

        if(requestType==StaticUtility.LISTBOX) {
            column1.setVisibility(View.GONE);
            column3.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        return convertView;
    }

    View.OnClickListener clickMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey,DB1Tables.PERIODS);
                Navigation.findNavController(view).navigate(R.id.nav_period_grid_view);
            }
        };
    }
}