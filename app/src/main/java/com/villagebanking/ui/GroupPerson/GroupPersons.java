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

import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.DBTables.tblGroupPersonLink;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.ui.UIUtility;
import com.villagebanking.databinding.GroupsPersonLinkviewBinding;

import java.util.ArrayList;

public class GroupPersons extends Fragment {
    private GroupsPersonLinkviewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = GroupsPersonLinkviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initilize();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initilize() {
        long group_Key = getArguments().getLong("group_key");
        binding.btnSave.setOnClickListener(clickMethod(group_Key));
        binding.editPerson.setOnItemClickListener(personSelected());
        fill_GridView(group_Key);
        fill_person();
    }

    public View.OnClickListener clickMethod(long groupKey) {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                BOGroupPersonLink saveData = getDataFromView(groupKey);
                if (saveData != null)
                    DBUtility.DTOSaveUpdate(saveData, tblGroupPersonLink.Name);
                fill_GridView(groupKey);
            }
        };
    }

    BOGroupPersonLink getDataFromView(long group_Key) {
        BOGroupPersonLink newData = null;
        if (group_Key > 0 && personKey > 0) {
            newData = new BOGroupPersonLink();
            newData.setGroup_Key(group_Key);
            newData.setPerson_Key(personKey);
            newData.setOrderBy(1);
            newData.setPerson_role("");
        }
        return newData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_GridView(long groupKey) {
        ArrayList<BOGroupPersonLink> groupPersons = DBUtility.DTOGetAlls(tblGroupPersonLink.Name);
        groupPersons.removeIf(x -> x.getGroup_Key() != groupKey);
        GroupPersonsGrid adapter = new GroupPersonsGrid(this.getContext(), R.layout.app_gridview, groupPersons);
        binding.gvPersons.setAdapter(adapter);

        ArrayList<BOGroup> groups = DBUtility.DTOGetData(tblGroup.Name, groupKey);
        if (groups.size() > 0) {
            BOGroup group = groups.get(0);
            binding.editGroup.setText(group.getName());
            if (groupPersons.size() == group.getNoOfPerson()) {
                binding.editPart.setVisibility(View.GONE);
            } else {
                binding.editPart.setVisibility(View.VISIBLE);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_person() {
        ArrayList<BOPerson> actualList = DBUtility.DTOGetAlls(tblPerson.Name);

        ArrayList<BOKeyValue> autoCompleteList = new ArrayList<>();
        actualList.forEach(x -> autoCompleteList.add
                (
                        new BOKeyValue(x.getPrimary_key(),
                                x.getStrFName() + "-" + x.getStrLName()))
        );
        UIUtility.LoadAutoBox(this.getContext(), autoCompleteList, binding.editPerson);
    }

    long personKey = 0;

    AdapterView.OnItemClickListener personSelected() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof BOKeyValue) {
                    BOKeyValue itemSelected = (BOKeyValue) item;
                    personKey = itemSelected.getPrimary_key();
                }
            }
        };
    }
}
