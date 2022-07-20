package com.villagebanking.ui.Group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.AppGridviewBinding;

public class Groups extends Fragment {
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
                Navigation.findNavController(view).navigate(R.id.nav_group_edit_view);
            }
        };
    }

    void assignToGridView() {
        GroupsGrid adapter = new GroupsGrid(this.getContext(), R.layout.groups_gridview,
                DBUtility.DTOGetAlls(tblGroup.Name));
        binding.gvDataView.setAdapter(adapter);
    }
}
