package com.villagebanking.ui.Person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.villagebanking.Database.DB0Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.PersonsViewBinding;


public class PersonsDetail extends Fragment {
    private PersonsViewBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PersonsViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        assignToGridView();
        return root;
    }
    void assignToGridView() {
        PersonsAdapter adapter = new PersonsAdapter(this.getContext(), R.layout.persons_gridview,
                DBUtility.DTOGetAlls(DB0Tables.PERSONS));
        binding.gvDataView.setAdapter(adapter);
    }
}