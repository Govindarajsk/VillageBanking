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
        fill_GridView(group_Key);
        fill_person(0);
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
        BOGroupPersonLink newData = new BOGroupPersonLink();
        newData.setGroup_Key(group_Key);
        BOKeyValue person = UIUtility.GetAutoBoxSelected(binding.editPerson);
        newData.setPerson_Key(person.getPrimary_key());
        newData.setOrderBy(1);
        newData.setPerson_role("");
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
    void fill_person(long personKey) {
        ArrayList<BOPerson> people = DBUtility.DTOGetData(tblPerson.Name, 0);
        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        people.stream().forEach(x -> keyValues.add(new BOKeyValue(x.getPrimary_key(), x.getFullName())));

        UIUtility.getAutoBox(this.getContext(), keyValues, binding.editPerson, personKey);
    }
}
