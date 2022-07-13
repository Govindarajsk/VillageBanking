package com.villagebanking.ui.Transaction;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.AppGridviewBinding;

import java.util.ArrayList;

public class TransDetail extends Fragment {
    private AppGridviewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AppGridviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        trans_Detail();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void trans_Detail() {
        ArrayList<BOTransDetail> transDetails = DBUtility.DTOGetAlls(tblTransDetail.Name);
        TransDetailGrid adapter = new TransDetailGrid(this.getContext(), R.layout.groups_gridview, transDetails);
        binding.gvDataView.setAdapter(adapter);
    }
}
