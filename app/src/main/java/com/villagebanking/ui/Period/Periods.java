package com.villagebanking.ui.Period;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.AppGridviewBinding;
import com.villagebanking.ui.Transaction.TransHeaderGrid;

import java.util.ArrayList;

public class Periods extends Fragment {
    private AppGridviewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AppGridviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initilize();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initilize() {
        assignToGridView();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    void assignToGridView() {
        ArrayList<BOPeriod> periods = tblPeriod.GetList(0);
        periods.sort((t1, t2) -> Long.toString(t1.getPeriodType()).
                compareTo(Long.toString(t2.getPeriodType())));
        PeriodsGrid adapter = new PeriodsGrid(this.getContext(), R.layout.app_gridview, periods);
        binding.gvDataView.setAdapter(adapter);
    }
}