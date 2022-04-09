package com.villagebanking.ui.Group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.ActivityGridviewBinding;

public class GroupDetail extends Fragment {
    private ActivityGridviewBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityGridviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        assignToGridView();
        return root;
    }
    void assignToGridView() {

        GroupGrid adapter = new GroupGrid(this.getContext(), R.layout.group_gridview,
                DBUtility.DTOGetAlls(DB1Tables.GROUPS));
        binding.gvDataView.setAdapter(adapter);
    }
}
