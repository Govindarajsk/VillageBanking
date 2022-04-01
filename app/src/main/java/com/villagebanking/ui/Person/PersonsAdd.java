package com.villagebanking.ui.Person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.databinding.PersonsFragmentBinding;

public class PersonsAdd extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PersonsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnSave.setOnClickListener(clickMethod());
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private PersonsFragmentBinding binding;
    //region Click Listener
    View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOSaveUpdate(getPersonDataFromView());
            }
        };
    }
    int key = 0;
    BOPerson getPersonDataFromView() {
        BOPerson newData = new BOPerson();
        newData.setKeyID(key);
        newData.setStrFName(binding.editFName.getText().toString());
        newData.setStrLName(binding.editLName.getText().toString());
        newData.setNumMobile(Long.valueOf(binding.editMobile.getText().toString()));
        return newData;
    }
}
