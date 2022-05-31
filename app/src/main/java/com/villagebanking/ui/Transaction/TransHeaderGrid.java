package com.villagebanking.ui.Transaction;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPersonTrans;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;

import java.util.ArrayList;

public class TransHeaderGrid<T> extends ArrayAdapter {
    public TransHeaderGrid(Context context, int textViewResourceId, ArrayList<T> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T data = (T) super.getItem(position);
        convertView = customeView(position + 1, data);
        return convertView;
    }

    private View customeView(int row, T data) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.persons_trans_gridview, null);

        BOTransDetail bindData = (BOTransDetail) data;
        String value1 = bindData.getRemarks() + bindData.getParentKey();
        String value2 = String.valueOf(bindData.getBalanceAmount());
        String value3 = String.valueOf(bindData.getPaidAmount());
        String value4 = null;

        TextView txtDetails = ((TextView) convertView.findViewById(R.id.txtDetails));
        TextView txtAmount = ((TextView) convertView.findViewById(R.id.txtAmount));
        TextView txtNewAmount = ((TextView) convertView.findViewById(R.id.txtNewAmount));

        txtNewAmount.addTextChangedListener(textChangedEvnt(bindData));

        txtDetails.setText(value1);
        txtAmount.setText(value2);
        txtNewAmount.setText(value3);

        CheckBox chkIsFull = ((CheckBox) convertView.findViewById(R.id.chkIsFull));

        boolean isAmountExisit = bindData.getPaidAmount() != 0 && bindData.getPrimary_key() > 0;
        chkIsFull.setChecked(isAmountExisit);
        chkIsFull.setEnabled(!isAmountExisit);
        txtNewAmount.setEnabled(!isAmountExisit);
        chkIsFull.setOnClickListener(clickCheckBox(bindData, txtNewAmount));

        if (bindData.getTableLinkKey() > 0) {
            txtDetails.setVisibility(View.INVISIBLE);
            ((TextView) convertView.findViewById(R.id.lineTop)).setVisibility(View.INVISIBLE);
        }

        ImageButton btnDelete = ((ImageButton) convertView.findViewById(R.id.btnDelete));
        btnDelete.setOnClickListener(clickDelete(bindData));

        return convertView;
    }

    View.OnClickListener clickDelete(BOTransDetail bindData) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bindData != null)
                    if (bindData.getPrimary_key() > 0) {
                        DBUtility.DTOdelete(bindData.getPrimary_key(), DB1Tables.PERSON_TRANSACTION);
                        Bundle args = new Bundle();
                        ArrayList<BOGroupPersonLink> tableLinkDetail =
                                DBUtility.DTOGetData(DB1Tables.GROUP_PERSON_LINK, bindData.getTableLinkKey());
                        if (tableLinkDetail.size() > 0) {
                            BOGroupPersonLink linkData = tableLinkDetail.get(0);
                            args.putLong("person_key", linkData.getPerson_Detail().getPrimary_key());
                            args.putLong("group_key", linkData.getGroup_Detail().getPrimary_key());
                            args.putLong("period_key", linkData.getGroup_Detail().getPeriodDetail().getPrimary_key());
                        }
                        Navigation.findNavController(view).navigate(R.id.nav_linkview_person_trans, args);
                    } else {
                        bindData.setPaidAmount(0.00);
                    }

            }
        };
    }

    View.OnClickListener clickCheckBox(BOTransDetail bindData, TextView txtView) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox chkBox = ((CheckBox) view);
                boolean checked = chkBox.isChecked();

                double newAmount = 0.0;

                if (checked && bindData.getPrimary_key() == 0) {
                    newAmount = bindData.getBalanceAmount();
                } else if (bindData.getPrimary_key() > 0) {
                    newAmount = bindData.getPaidAmount();
                }

                txtView.setText(String.valueOf(newAmount));
            }
        };
    }

    TextWatcher textChangedEvnt(BOTransDetail bindData) {
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                bindData.setPaidAmount(s != null ? Double.valueOf(s.toString()) : 0.00);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ShowMessage(context, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bindData.setPaidAmount(s != null ? Double.valueOf(s.toString()) : 0.00);

            }
        };

        return fieldValidatorTextWatcher;
    }
}