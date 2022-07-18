package com.villagebanking.ui.Group;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.Utility.UIUtility;
import com.villagebanking.databinding.GroupsEditviewBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GroupsEdit extends Fragment {
    private GroupsEditviewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = GroupsEditviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initialize() {
        fill_periodType();
        if (getArguments() != null) {
            long group_Key = getArguments().getLong("group_key");
            writeView(group_Key);
        }
        binding.btnSave.setOnClickListener(clickSave());
        binding.editPeridType.setOnItemClickListener(periodItemSelected());
        binding.editStartDate.setOnItemClickListener(periodTypeItemSelected());

        UIUtility.ApplyTextWatcher(this.getContext(), binding.editAmount, binding.editNoOfPerson, binding.lblTotalAmount);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View.OnClickListener clickSave() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkFields()) {
                    DBUtility.DTOSaveUpdate(readView(), DB1Tables.GROUPS);
                    getActivity().onBackPressed();
                }
            }
        };
    }

    boolean checkFields() {
        boolean ok1 = UIUtility.IsFieldEmpty(UIUtility.V_STRING, 0, 10, binding.editName);
        boolean ok2 = UIUtility.IsFieldEmpty(UIUtility.V_NUMBER, 0, 100, binding.editNoOfPerson);
        boolean ok3 = UIUtility.IsFieldEmpty(UIUtility.V_NUMBER, 0, 50000, binding.editAmount);
        boolean ok4 = UIUtility.IsFieldEmpty(UIUtility.V_STRING, 0, 1000, binding.editBondCharge);
        boolean ok5 = UIUtility.IsFieldEmpty(UIUtility.V_STRING, 0, 15, binding.editPeridType);
        boolean ok6 = UIUtility.IsFieldEmpty(UIUtility.V_STRING, 0, 15, binding.editStartDate);

        return ok1 || ok2 || ok3 || ok4 || ok5 || ok6;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_periodType() {
        ArrayList<BOPeriod> actualList = DBUtility.DTOGetAlls(DB1Tables.PERIODS);
        Map<String, List<BOPeriod>> okperiod = UIUtility.GroupByPeriod(actualList);
        Set<String> list = okperiod.keySet();

        ArrayList<BOKeyValue> autoCompleteList = new ArrayList<>();
        for (String item : list) {
            String[] strings = item.split(":");
            if (strings.length > 1)
                autoCompleteList.add(new BOKeyValue(Integer.valueOf(strings[0]), item));
        }
        UIUtility.SetAutoCompleteBox(this.getContext(), autoCompleteList, binding.editPeridType);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_periodStart(long periodType) {
        ArrayList<BOPeriod> boPeriods = DBUtility.DTOGetAlls(DB1Tables.PERIODS);
        boPeriods.removeIf(x -> x.getPeriodType() != periodType);
        boPeriods.sort((t1, t2) -> Long.toString(t1.getPeriodValue()).compareTo(Long.toString(t2.getPeriodValue())));
        ArrayList<BOKeyValue> autoCompleteList = new ArrayList<>();
        for (BOPeriod item : boPeriods) {
            autoCompleteList.add(new BOKeyValue(item.getPrimary_key(), item.getActualDate()));
        }
        UIUtility.SetAutoCompleteBox(this.getContext(), autoCompleteList, binding.editStartDate);
    }

    AdapterView.OnItemClickListener periodItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                long key = UIUtility.getAutoBoxKey(parent.getItemAtPosition(position));
                selectedData.getPeriodDetail().setPeriodType(key);
                fill_periodStart(key);
            }
        };
    }

    AdapterView.OnItemClickListener periodTypeItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                long key = UIUtility.getAutoBoxKey(parent.getItemAtPosition(position));
                selectedData.getPeriodDetail().setPrimary_key(key);
            }
        };
    }

    BOGroup selectedData = new BOGroup();

    BOGroup readView() {
        if (selectedData == null)
            selectedData = new BOGroup();
        selectedData.setName(binding.editName.getText().toString());
        selectedData.setNoOfPerson(Integer.valueOf(binding.editNoOfPerson.getText().toString()));
        selectedData.setAmount(Double.parseDouble(binding.editAmount.getText().toString()));
        selectedData.setBondCharge(Double.parseDouble(binding.editBondCharge.getText().toString()));
        return selectedData;
    }

    void writeView(long primary_key) {
        ArrayList<BOGroup> fList = DBUtility.DTOGetData(DB1Tables.GROUPS, primary_key);
        if (fList.size() > 0) {
            selectedData = fList.get(0);
            UIUtility.applyValue(binding.editName, selectedData.getName());
            UIUtility.applyValue(binding.editNoOfPerson, selectedData.getNoOfPerson());
            UIUtility.applyValue(binding.editAmount, selectedData.getAmount());
            UIUtility.applyValue(binding.editStartDate, selectedData.getPeriodDetail().getActualDate());
            UIUtility.applyValue(binding.editPeridType, selectedData.getPeriodDetail().getPeriodName());
            UIUtility.applyValue(binding.editBondCharge, selectedData.getBondCharge());
            UIUtility.applyValue(binding.lblTotalAmount, selectedData.getAmount() * selectedData.getNoOfPerson());
            //diableField();
        }
    }

    void diableField() {
        UIUtility.disableField(binding.editNoOfPerson);
        UIUtility.disableField(binding.editAmount);
        UIUtility.disableField(binding.editStartDate);
        UIUtility.disableField(binding.editPeridType);
        UIUtility.disableField(binding.editBondCharge);
    }
}
