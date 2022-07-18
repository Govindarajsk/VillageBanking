package com.villagebanking.ui.Loan;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.databinding.LoanHeaderViewBinding;

public class LoanHeader extends Fragment {
    private LoanHeaderViewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LoanHeaderViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}
