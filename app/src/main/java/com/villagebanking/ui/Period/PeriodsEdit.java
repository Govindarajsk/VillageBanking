package com.villagebanking.ui.Period;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.databinding.PeriodsViewBinding;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodsEdit extends Fragment {

    private PeriodsViewBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PeriodsViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initilize();
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void initilize() {
        binding.btnSave.setOnClickListener(clickMethod());
    }

    public View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOSaveUpdate(getPersonDataFromView(), DB1Tables.PERIODS);
            }
        };
    }

    BOPeriod getPersonDataFromView() {
        BOPeriod newData = new BOPeriod();
        newData.setPrimary_key(0);
        String str=binding.editType.getText().toString();
        newData.setPeriodType(Integer.valueOf(str));
        newData.setPeriodName(binding.editName.getText().toString());
        ParsePosition pos = new ParsePosition(0);
        Date dt=new SimpleDateFormat("dd/MM/yyyy").parse(binding.editDate.getText().toString(),pos);
        newData.setActualDate(dt);
        newData.setPeriodRemarks(binding.editDetail.getText().toString());
        return newData;
    }
}

