package com.villagebanking.ui.Person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.AppGridviewBinding;


public class Persons extends Fragment {
    private AppGridviewBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AppGridviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initilize();
        return root;
    }

    void initilize() {

        assignToGridView();
        binding.btnAdd.setOnClickListener(clickMethod());
    }

    public View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_person_edit_view);
            }
        };
    }

    void assignToGridView() {
        PersonsGrid adapter = new PersonsGrid(this.getContext(), R.layout.persons_gridview,
                tblPerson.GetList(0));
        binding.gvDataView.setAdapter(adapter);
    }
}