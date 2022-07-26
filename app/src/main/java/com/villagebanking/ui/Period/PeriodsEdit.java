package com.villagebanking.ui.Period;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Controls.AutoBox;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.databinding.PeriodsEditviewBinding;
import com.villagebanking.ui.UIUtility;

import java.util.ArrayList;

public class PeriodsEdit extends Fragment {
    private PeriodsEditviewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = PeriodsEditviewBinding.inflate(inflater, container, false);
        initilize();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initilize() {
        binding.btnSave.setOnClickListener(clickMethod());
        DatePicker simpleDatePicker = (DatePicker) binding.editDate; // initiate a date picker
        simpleDatePicker.setSpinnersShown(true);
        fill_Type(1);
        if (getArguments() != null) {
            long key = getArguments().getLong("primary_key");
            applyValue(key);
        }
    }

    public View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //tblPeriod.Save("I",getViewData());
                DBUtility.DTOSaveUpdate(getViewData(), tblPeriod.Name);
            }
        };
    }

    BOPeriod getViewData() {
        BOPeriod newData = new BOPeriod();
        newData.setPrimary_key((primary_key));
        BOKeyValue selected = UIUtility.GetAutoBoxSelected(binding.editType);
        newData.setPeriodType(selected.getPrimary_key());
        newData.setPeriodName(selected.getDisplayValue());
        newData.setActualDate(UIUtility.getDateString(binding.editDate));
        newData.setPeriodValue(UIUtility.getDateInt(binding.editDate));
        newData.setPeriodRemarks(binding.editDetail.getText().toString());
        return newData;
    }

    long primary_key = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    void applyValue(long key) {
        ArrayList<BOPeriod> datas = DBUtility.DTOGetData(tblPeriod.Name, key);
        if (datas.size() > 0) {
            BOPeriod editData = datas.get(0);
            primary_key = editData.getPrimary_key();
            ((AutoBox)binding.editType.getAdapter()).SetSelected(binding.editType,editData.getPeriodType());

            BOKeyValue selected = UIUtility.GetAutoBoxSelected(binding.editType);
            editData.setPeriodType(selected.getPrimary_key());
            editData.setPeriodName(selected.getDisplayValue());
            binding.editType.setText(String.valueOf(editData.getPeriodType()));
            UIUtility.getDateFromString(editData.getActualDate(), binding.editDate);
            binding.editDetail.setText(editData.getPeriodRemarks());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_Type(long type) {
        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        keyValues.add(new BOKeyValue(1, "1.Amavasai"));
        keyValues.add(new BOKeyValue(2, "2.Every 5th"));
        UIUtility.getAutoBox(this.getContext(), keyValues, binding.editType, type);
        binding.editType.setOnItemClickListener(selectedEvent(binding.editType));
    }

    AdapterView.OnItemClickListener selectedEvent(AutoCompleteTextView control) {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                BOKeyValue value = UIUtility.GetAutoBoxSelected(control);
            }
        };
    }
}

