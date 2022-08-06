package com.villagebanking.ui.Base;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.villagebanking.databinding.TransHeaderViewBinding;

public abstract class FragmentBase<T> extends Fragment {
    protected abstract void Initialize();

    private TransHeaderViewBinding binding;

    private ViewBinding binding1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TransHeaderViewBinding.inflate(inflater, container, false);

       // binding1=ViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Initialize();
        return root;
    }
}
