package com.villagebanking.ui.GroupPerson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class GroupPersonsGrid<T> extends ArrayAdapter{
    public GroupPersonsGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
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
        View convertView = inflater.inflate(R.layout.listview_persons, null);

        BOGroupPersonLink bindData = (BOGroupPersonLink) data;
        String value1 = Integer.toString(row);
        String value2=null;
        String value3=null;
        String value4=null;

        BOPerson person=bindData.getPerson_Detail();
        if(person!=null) {
            value2 = person.getStrFName() + "-" + bindData.getPrimary_key();
            value3 = person.getStrLName();
            value4 = Long.toString(person.getNumMobile());
        }

        TextView txtSNo = ((TextView) convertView.findViewById(R.id.txtSNo));
        TextView txtFirstName = ((TextView) convertView.findViewById(R.id.txtFirstName));
        TextView txtLastName = ((TextView) convertView.findViewById(R.id.txtLastName));
        TextView txtPhoneNo = ((TextView) convertView.findViewById(R.id.txtPhoneNo));

        txtSNo.setText(value1);
        txtFirstName.setText(value2);
        txtLastName.setText(value3);
        txtPhoneNo.setText(value4);
        Button btnDelete = ((Button) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(clickMethod(bindData.getPrimary_key()));
        return convertView;
    }

    View.OnClickListener clickMethod(long primaryKey) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOdelete(primaryKey, DB1Tables.GROUP_PERSON_LINK);
            }
        };
    }
}