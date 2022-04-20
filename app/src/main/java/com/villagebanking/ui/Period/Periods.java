package com.villagebanking.ui.Period;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.StaticUtility;
import com.villagebanking.databinding.AppGridviewBinding;

public class Periods extends Fragment {
    private AppGridviewBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AppGridviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        assignToGridView();
        initilize();
        return root;
    }
    void initilize() {
        binding.btnAdd.setOnClickListener(clickMethod());
    }

    public View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_period_edit_view);
            }
        };
    }

    void assignToGridView() {
        PeriodsGrid adapter = new PeriodsGrid(this.getContext(), R.layout.app_gridview,
                DBUtility.DTOGetAlls(DB1Tables.PERIODS), StaticUtility.DATAGRID);
        binding.gvDataView.setAdapter(adapter);
    }
}