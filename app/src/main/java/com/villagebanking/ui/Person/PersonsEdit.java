package com.villagebanking.ui.Person;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.databinding.PersonsEditviewBinding;
import com.villagebanking.ui.UIUtility;

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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                tblPerson.Save("I",getPersonDataFromView());
                getActivity().onBackPressed();
            }
        };
    }

    BOPerson getPersonDataFromView() {
        BOPerson newData = new BOPerson();
        newData.setPrimary_key(0);
        newData.setFName(UIUtility.ToString(binding.editFName));
        newData.setLName(UIUtility.ToString(binding.editLName));
        newData.setMobile(UIUtility.ToLong(binding.editMobile));
        newData.getReference1().setPrimary_key(0);
        newData.getReference2().setPrimary_key(0);
        newData.setScore(0);
        return newData;
    }
}
