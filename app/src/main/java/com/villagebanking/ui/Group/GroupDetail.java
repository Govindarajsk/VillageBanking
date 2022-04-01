package com.villagebanking.ui.Group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.StaticUtility;
import com.villagebanking.databinding.GroupViewBinding;

public class GroupDetail extends Fragment {
    private GroupViewBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = GroupViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        assignToGridView();
        return root;
    }
    void assignToGridView() {

        GroupAdapter adapter = new GroupAdapter(this.getContext(), R.layout.persons_gridview,
                DBUtility.DTOGetAlls(StaticUtility.GROUPS));
        binding.gvDataView.setAdapter(adapter);

    }
}
