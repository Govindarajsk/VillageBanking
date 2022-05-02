package com.villagebanking.ui.Period;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Controls.DatePickerFragment;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.Utility.StaticUtility;
import com.villagebanking.databinding.EditviewPeriodsBinding;

import java.util.ArrayList;

public class PeriodsEdit extends Fragment {
    private EditviewPeriodsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = EditviewPeriodsBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            long key = getArguments().getLong("primary_key");
            applyValue(key);
        }
        initilize();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void initilize() {
        binding.btnSave.setOnClickListener(clickMethod());
        DatePicker simpleDatePicker = (DatePicker) binding.editDate; // initiate a date picker
        simpleDatePicker.setSpinnersShown(true);
    }

    public View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOSaveUpdate(getPersonDataFromView(), DB1Tables.PERIODS);
                //getActivity().onBackPressed();
            }
        };
    }

    BOPeriod getPersonDataFromView() {
        BOPeriod newData = new BOPeriod();
        newData.setPrimary_key((primary_key));
        String str = binding.editType.getText().toString();
        newData.setPeriodType(Integer.valueOf(str));
        newData.setPeriodName(binding.editName.getText().toString());
        newData.setActualDate(StaticUtility.getDateString(binding.editDate));
        newData.setPeriodValue(StaticUtility.getDateInt(binding.editDate));
        newData.setPeriodRemarks(binding.editDetail.getText().toString());
        return newData;
    }

    long primary_key = 0;

    void applyValue(long key) {
        ArrayList<BOPeriod> datas = DBUtility.DTOGetData(DB1Tables.PERIODS, key);
        if (datas.size() > 0) {
            BOPeriod editData = datas.get(0);
            primary_key = editData.getPrimary_key();
            binding.editType.setText(String.valueOf(editData.getPeriodType()));
            binding.editName.setText(editData.getPeriodName());
            StaticUtility.getDateFromString(editData.getActualDate(), binding.editDate);
            binding.editDetail.setText(editData.getPeriodRemarks());
        }
    }
}
