package com.villagebanking.ui.Master;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.databinding.PeriodsViewBinding;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodsAdd extends Fragment {

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
                //DBUtility.DTOSaveUpdate(getPersonDataFromView(),D);
            }
        };
    }

    BOPeriod getPersonDataFromView() {
        BOPeriod newData = new BOPeriod();
        newData.setPrimary_key(0);
        newData.setPeriodName(binding.editName.getText().toString());

        ParsePosition pos = new ParsePosition(0);
        newData.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse
                (binding.editStartDate.getText().toString(),pos)
        );
        newData.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse
                (binding.editStartDate.getText().toString(),pos)
        );
        return newData;
    }
}

