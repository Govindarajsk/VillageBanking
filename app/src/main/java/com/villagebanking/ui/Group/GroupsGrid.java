package com.villagebanking.ui.Group;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class GroupsGrid<T> extends ArrayAdapter {
    public GroupsGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
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
        View convertView = inflater.inflate(R.layout.listview_groups, null);

        BOGroup bindData = (BOGroup) data;
        String sNo = Integer.toString(row);
        String groupName = bindData.getName();
        String amount = Double.toString(bindData.getAmount());
        String noPerson = Integer.toString(bindData.getNoOfPerson());
        String actDate = bindData.getPeriodDetail().getActualDate();
        String total = Double.toString(bindData.getNoOfPerson() * bindData.getAmount());


        TextView txtTotal = ((TextView) convertView.findViewById(R.id.txtTotal));
        TextView txtGrpName = ((TextView) convertView.findViewById(R.id.txtGrpName));
        TextView txtAmount = ((TextView) convertView.findViewById(R.id.txtAmount));
        TextView txtActualDate = ((TextView) convertView.findViewById(R.id.txtActualDate));
        TextView txtNoOfPerson = ((TextView) convertView.findViewById(R.id.txtNoPerson));

        txtTotal.setText(total);
        txtGrpName.setText(groupName);
        txtAmount.setText(amount);
        txtNoOfPerson.setText(noPerson);
        txtActualDate.setText(actDate);

        Button btnDelete = ((Button) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(clickMethod(bindData.getPrimary_key()));

        Button btnPersonDetail = ((Button) convertView.findViewById(R.id.btnPersonDetail));
        btnPersonDetail.setOnClickListener(showPersonDetail(bindData.getPrimary_key()));

        return convertView;
    }
    View.OnClickListener clickMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, DB1Tables.GROUPS);
                Navigation.findNavController(view).navigate(R.id.nav_group_grid_view);
            }
        };
    }
    View.OnClickListener showPersonDetail(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args=new Bundle();
                args.putLong("group_key",primaryKey);
                Navigation.findNavController(view).navigate(R.id.nav_linkview_group_person,args);
            }
        };
    }

}
