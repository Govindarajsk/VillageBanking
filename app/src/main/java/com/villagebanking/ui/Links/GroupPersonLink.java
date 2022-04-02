package com.villagebanking.ui.Links;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.Database.DB0Tables;
import com.villagebanking.Utility.CustomAdapter;
import com.villagebanking.Database.DBHelper;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.GroupPersonLinkBinding;
import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOPerson;

import java.util.ArrayList;

public class GroupPersonLink extends Fragment {
    private GroupPersonLinkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = GroupPersonLinkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dbHelper = DBUtility.getDB();
        binding.btnSave.setOnClickListener(clickMethod());
        binding.btnDelete.setOnClickListener(clickMethod());
        binding.btnNew.setOnClickListener(clickMethod());
        fillGroup();
        fillPerson();
        assignToGridView();
        return root;
    }

    DBHelper dbHelper;

    View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b = ((MaterialButton) view).getText().toString();
                switch (b) {
                    case "Save":
                        clickSave();
                        break;
                    case "Delete":
                        clickDelete();
                        break;
                }
            }
        };
    }

    void clickDelete() {
        DBUtility.DTOdelete(key, DB0Tables.GROUP_PERSON_LINK);
        key = 0;
        assignToGridView();
        binding.btnSave.setVisibility(View.VISIBLE);
        binding.btnNew.setVisibility(View.VISIBLE);
        binding.btnDelete.setVisibility(View.GONE);
    }

    void clickSave() {
        BOGroupPersonLink newIns = getDataFromView();
        DBUtility.DTOSaveUpdate(newIns, DB0Tables.GROUP_PERSON_LINK);
        assignToGridView();
        key = 0;
    }

    int key = 0;

    BOGroupPersonLink getDataFromView() {
        BOGroupPersonLink newData = new BOGroupPersonLink();
        newData.setKeyID(key);

        BOGroup g = (BOGroup) binding.editGroup.getSelectedItem();
        newData.setGroup_key(g.getKeyID());
        BOPerson p = (BOPerson) binding.editPerson.getSelectedItem();
        newData.setPerson_key(p.getKeyID());
        newData.setOrderBy(objList.size() + 1);
        newData.setPerson_role("A");
        return newData;
    }

    void fillGroup() {
        CustomAdapter adapter = new CustomAdapter(this.getContext(), R.layout.activity_gridview,
                DBUtility.DTOGetAlls(DB0Tables.GROUPS), "B");

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.editGroup.setAdapter(adapter);
        binding.editGroup.setSelection(0);
    }

    void fillPerson() {
        CustomAdapter adapter = new CustomAdapter(this.getContext(), R.layout.activity_gridview,
                DBUtility.DTOGetAlls(DB0Tables.PERSONS), "B");

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.editPerson.setAdapter(adapter);
        binding.editPerson.setSelection(0);
    }

    ArrayList<BOGroupPersonLink> objList = new ArrayList<>();

    void assignToGridView() {
        CustomAdapter adapter = new CustomAdapter(this.getContext(), R.layout.activity_gridview,
                DBUtility.DTOGetAlls(DB0Tables.GROUP_PERSON_LINK), "A");
        binding.gvPersons.setAdapter(adapter);

        binding.gvPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected item
                BOGroupPersonLink seletedItem = (BOGroupPersonLink) adapterView.getItemAtPosition(i);
                key = seletedItem.getKeyID();
                // assignPersonDataToView(person);
                binding.btnNew.setVisibility(View.VISIBLE);
                binding.btnDelete.setVisibility(View.VISIBLE);
            }
        });
    }
}
