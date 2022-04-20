package com.villagebanking.ui.GroupPerson;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.Controls.DataGrid;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Utility.CustomAdapter;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.databinding.LinkviewGroupPersonBinding;

import java.util.ArrayList;

public class GroupPersons extends Fragment {
    private LinkviewGroupPersonBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LinkviewGroupPersonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        long group_key=getArguments().getLong("group_key");
        initilize(group_key);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initilize(long group_Key) {
        binding.btnSave.setOnClickListener(clickMethod(group_Key));
        binding.editPerson.setOnItemSelectedListener(personSelected());
        fill_GridView(group_Key);
        fill_person();
    }

    public View.OnClickListener clickMethod(long groupKey) {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DBUtility.DTOSaveUpdate(getDataFromView(groupKey), DB1Tables.GROUP_PERSON_LINK);
                fill_GridView(groupKey);
            }
        };
    }

    BOGroupPersonLink getDataFromView(long group_Key) {
        BOGroupPersonLink newData = new BOGroupPersonLink();
        newData.setGroup_key(group_Key);
        newData.setPerson_key(personSelected.getPrimary_key());
        return newData;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_GridView(long groupKey) {
        ArrayList<BOGroupPersonLink> groupPersons = DBUtility.DTOGetAlls(DB1Tables.GROUP_PERSON_LINK);
        groupPersons.removeIf(x -> x.getGroup_key() != groupKey);

        GroupPersonsGrid adapter = new GroupPersonsGrid(this.getContext(), R.layout.app_gridview,
                groupPersons);
        binding.gvPersons.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_person() {
        ArrayList<BOPeriod> actualList = DBUtility.DTOGetAlls(DB1Tables.PERSONS);
        DataGrid dataGrid = new DataGrid(this.getContext(), R.layout.listview_dropdown, actualList);
        dataGrid.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.editPerson.setAdapter(dataGrid);
        binding.editPerson.setSelection(0);
    }

    BOPerson personSelected = null;

    AdapterView.OnItemSelectedListener personSelected() {
        return new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected item
                personSelected = (BOPerson) adapterView.getItemAtPosition(i);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                personSelected = null;
            }
        };
    }
}
