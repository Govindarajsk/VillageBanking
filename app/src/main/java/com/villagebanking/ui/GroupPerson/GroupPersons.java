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

import com.villagebanking.BOObjects.BOAutoComplete;
import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.Controls.AutoCompleteBox;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Utility.StaticUtility;
import com.villagebanking.databinding.LinkviewGroupPersonBinding;

import java.util.ArrayList;

public class GroupPersons extends Fragment {
    private LinkviewGroupPersonBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LinkviewGroupPersonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        long group_key = getArguments().getLong("group_key");
        initilize(group_key);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initilize(long group_Key) {
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
                    DBUtility.DTOSaveUpdate(saveData, DB1Tables.GROUP_PERSON_LINK);
                fill_GridView(groupKey);
            }
        };
    }

    BOGroupPersonLink getDataFromView(long group_Key) {
        BOGroupPersonLink newData = null;
        if (group_Key > 0 && personKey > 0) {
            newData = new BOGroupPersonLink();
            newData.getGroup_Detail().setPrimary_key(group_Key);
            newData.getPerson_Detail().setPrimary_key(personKey);
        }
        return newData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_GridView(long groupKey) {
        ArrayList<BOGroupPersonLink> groupPersons = DBUtility.DTOGetAlls(DB1Tables.GROUP_PERSON_LINK);
        groupPersons.removeIf(x -> x.getGroup_Detail().getPrimary_key() != groupKey);
        GroupPersonsGrid adapter = new GroupPersonsGrid(this.getContext(), R.layout.app_gridview, groupPersons);
        binding.gvPersons.setAdapter(adapter);

        ArrayList<BOGroup> groups = DBUtility.DTOGetData(DB1Tables.GROUPS, groupKey);
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
        ArrayList<BOPerson> actualList = DBUtility.DTOGetAlls(DB1Tables.PERSONS);

        ArrayList<BOAutoComplete> autoCompleteList = new ArrayList<>();
        actualList.forEach(x -> autoCompleteList.add
                (
                        new BOAutoComplete(x.getPrimary_key(),
                                x.getStrFName() + "-" + x.getStrLName()))
        );
        StaticUtility.SetAutoCompleteBox(this.getContext(), autoCompleteList, binding.editPerson);
    }

    long personKey = 0;

    AdapterView.OnItemClickListener personSelected() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof BOAutoComplete) {
                    BOAutoComplete itemSelected = (BOAutoComplete) item;
                    personKey = itemSelected.getPrimary_key();
                }
            }
        };
    }
}
