package com.villagebanking.ui.Person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.databinding.PersonsEditviewBinding;

public class PersonsEdit extends Fragment {

    private PersonsEditviewBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PersonsEditviewBinding.inflate(inflater, container, false);
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
                DBUtility.DTOSaveUpdate(getPersonDataFromView(), tblPerson.Name);
                getActivity().onBackPressed();
            }
        };
    }
    BOPerson getPersonDataFromView() {
        BOPerson newData = new BOPerson();
        newData.setPrimary_key(0);
        newData.setStrFName(binding.editFName.getText().toString());
        newData.setStrLName(binding.editLName.getText().toString());
        newData.setNumMobile(Long.valueOf(binding.editMobile.getText().toString()));
        return newData;
    }
}
