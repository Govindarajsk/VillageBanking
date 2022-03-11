package com.villagebanking.ui.Master;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.CustomAdapter;
import com.villagebanking.Utility.StaticUtility;
import com.villagebanking.databinding.PersonViewBinding;


public class PersonView extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PersonViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;

    }
    int key = 0;
    private @NonNull PersonViewBinding binding;

    void initialize()   {
        assignToGridView();
    }

    void assignToGridView() {
        CustomAdapter adapter = new CustomAdapter(this.getContext(), R.layout.activity_gridview,
                DBUtility.DTOGetAlls(StaticUtility.PERSONS), "A");
        binding.gvDataView.setAdapter(adapter);
    }

    //endregion
}