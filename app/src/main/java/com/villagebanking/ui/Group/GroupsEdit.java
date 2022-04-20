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

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOPeriod;
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
        binding.editPeridType.setOnItemSelectedListener(periodTypeSelected());
        binding.editStartDate.setOnItemSelectedListener(periodSelected());
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOSaveUpdate(getDataFromView(), DB1Tables.GROUPS);
                getActivity().onBackPressed();
            }
        };
    }

    BOGroup getDataFromView() {
        BOGroup newData = new BOGroup();
        newData.setPrimary_key(0);
        newData.setName(binding.editName.getText().toString());
        newData.setNoOfPerson(Integer.valueOf(binding.editNoOfPerson.getText().toString()));
        newData.setAmount(Double.parseDouble(binding.editAmount.getText().toString()));
        newData.setPeriodKey(Integer.valueOf(periodTypeSelected != null ? periodTypeSelected : "0"));
        newData.setStartPeriodKey(periodSelected.getPrimary_key());
        newData.setBondCharge(Double.parseDouble(binding.editBondCharge.getText().toString()));
        return newData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_periodType() {
        ArrayList<BOPeriod> actualList = DBUtility.DTOGetAlls(DB1Tables.PERIODS);
        Map<Integer, List<BOPeriod>> okperiod = StaticUtility.GroupByPeriod(actualList);
        Set<Integer> list = okperiod.keySet();

        ArrayList<String> periodTypes = new ArrayList<>();
        for (Integer i : list) {
            periodTypes.add(i.toString());
        }

        DataGrid dataGrid = new DataGrid(this.getContext(), R.layout.listview_dropdown, periodTypes);
        dataGrid.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.editPeridType.setAdapter(dataGrid);
        binding.editPeridType.setSelection(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_periodStart(Integer periodType) {
        ArrayList<BOPeriod> boPeriods = DBUtility.DTOGetAlls(DB1Tables.PERIODS);

        boPeriods.removeIf(x -> x.getPeriodType() != periodType);

        PeriodsGrid adapter = new PeriodsGrid(this.getContext(), R.layout.app_gridview,
                boPeriods, StaticUtility.LISTBOX);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.editStartDate.setAdapter(adapter);
        binding.editStartDate.setSelection(0);
    }

    String periodTypeSelected = null;
    AdapterView.OnItemSelectedListener periodTypeSelected() {
        return new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected item
                periodTypeSelected = (String) adapterView.getItemAtPosition(i);
                fill_periodStart(Integer.valueOf(periodTypeSelected));
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                periodTypeSelected = null;
                fill_periodStart(0);
            }
        };
    }


    BOPeriod periodSelected = null;
    AdapterView.OnItemSelectedListener periodSelected() {
        return new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected item
                periodSelected = (BOPeriod) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                periodSelected = null;
            }
        };
    }
}
