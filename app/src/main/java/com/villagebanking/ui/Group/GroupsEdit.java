package com.villagebanking.ui.Group;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOAutoComplete;
import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Controls.AutoCompleteBox;
import com.villagebanking.Controls.DataGrid;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.StaticUtility;
import com.villagebanking.databinding.EditviewGroupsBinding;
import com.villagebanking.ui.Period.PeriodsGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GroupsEdit extends Fragment {
    private EditviewGroupsBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = EditviewGroupsBinding.inflate(inflater, container, false);
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
        binding.btnSave.setOnClickListener(clickMethod());
        binding.editPeridType.setOnItemClickListener(periodItemSelected());
        binding.editStartDate.setOnItemClickListener(periodTypeItemSelected());

        StaticUtility.ApplyTextWatcher(this.getContext(),binding.editAmount,binding.editNoOfPerson,binding.lblTotalAmount);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkFields()) {
                    DBUtility.DTOSaveUpdate(getDataFromView(), DB1Tables.GROUPS);
                    getActivity().onBackPressed();
                }
            }
        };
    }

    boolean checkFields() {
        //binding.editName.setError("Ok google");
        boolean ok1 = StaticUtility.IsFieldEmpty(StaticUtility.V_STRING, binding.editName);
        boolean ok2 = StaticUtility.IsFieldEmpty(StaticUtility.V_NUMBER,binding.editNoOfPerson);
        boolean ok3 = StaticUtility.IsFieldEmpty(StaticUtility.V_NUMBER,binding.editAmount);
        boolean ok4 = StaticUtility.IsFieldEmpty(StaticUtility.V_STRING,binding.editBondCharge);
        boolean ok5 = StaticUtility.IsFieldEmpty(StaticUtility.V_STRING,binding.editPeridType);
        boolean ok6 = StaticUtility.IsFieldEmpty(StaticUtility.V_STRING,binding.editStartDate);

        return ok1 || ok2 || ok3 || ok4 || ok5 || ok6;
    }

    BOGroup getDataFromView() {
        BOGroup newData = new BOGroup();
        newData.setName(binding.editName.getText().toString());
        newData.setNoOfPerson(Integer.valueOf(binding.editNoOfPerson.getText().toString()));
        newData.setAmount(Double.parseDouble(binding.editAmount.getText().toString()));
        newData.setPeriodKey(periodKey1);
        newData.setStartPeriodKey(periodKey2);
        newData.setBondCharge(Double.parseDouble(binding.editBondCharge.getText().toString()));
        return newData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_periodType() {
        ArrayList<BOPeriod> actualList = DBUtility.DTOGetAlls(DB1Tables.PERIODS);
        Map<String, List<BOPeriod>> okperiod = StaticUtility.GroupByPeriod(actualList);
        Set<String> list = okperiod.keySet();

        ArrayList<BOAutoComplete> autoCompleteList = new ArrayList<>();
        for (String item : list) {
            String[] strings=item.split(":");
            if(strings.length>1)
                autoCompleteList.add(new BOAutoComplete(Integer.valueOf(strings[0]), item));
        }
        StaticUtility.SetAutoCompleteBox(this.getContext(),autoCompleteList, binding.editPeridType);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_periodStart(long periodType) {
        ArrayList<BOPeriod> boPeriods = DBUtility.DTOGetAlls(DB1Tables.PERIODS);
        boPeriods.removeIf(x -> x.getPeriodType() != periodType);

        ArrayList<BOAutoComplete> autoCompleteList = new ArrayList<>();
        for (BOPeriod item : boPeriods) {
            autoCompleteList.add(new BOAutoComplete(item.getPrimary_key(),item.getActualDate()));
        }
        StaticUtility.SetAutoCompleteBox(this.getContext(),autoCompleteList, binding.editStartDate);
    }

    long periodKey1 = 0;

    AdapterView.OnItemClickListener periodItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof BOAutoComplete) {
                    BOAutoComplete itemSelected = (BOAutoComplete) item;
                    periodKey1 = itemSelected.getPrimary_key();
                    fill_periodStart(periodKey1);
                }
            }
        };
    }

    long periodKey2 = 0;

    AdapterView.OnItemClickListener periodTypeItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof BOAutoComplete) {
                    BOAutoComplete itemSelected = (BOAutoComplete) item;
                    periodKey2 = itemSelected.getPrimary_key();
                }
            }
        };
    }
}
