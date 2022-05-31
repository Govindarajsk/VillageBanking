package com.villagebanking.ui.Transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOPersonTrans;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.databinding.TransEditviewBinding;

public class TransEdit extends Fragment {

    private TransEditviewBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TransEditviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot(); if (getArguments() != null) {
            long key = getArguments().getLong("primary_key");
            applyValue(key);
        }
        initilize();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void initilize() {
        binding.btnSave.setOnClickListener(clickMethod());
    }

    public View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBUtility.DTOSaveUpdate(getTransDataFromView(), DB1Tables.PERSON_TRANSACTION);
                getActivity().onBackPressed();
            }
        };
    }
    BOPersonTrans getTransDataFromView() {
        BOPersonTrans newData = new BOPersonTrans();
        //newData.setPrimary_key(0);
        //newData.setStrFName(binding.editFName.getText().toString());
        //newData.setStrLName(binding.editLName.getText().toString());
        //newData.setNumMobile(Long.valueOf(binding.editMobile.getText().toString()));
        return newData;
    }
    long primary_key = 0;

    void applyValue(long key) {
        /*
        ArrayList<BOPersonTransaction> datas = DBUtility.DTOGetData(DB1Tables.PERSON_TRANSACTION, key);
        if (datas.size() > 0) {
            BOPersonTransaction editData = datas.get(0);
            primary_key = editData.getPrimary_key();
            binding.editType.setText(String.valueOf(editData.getPeriodType()));
            binding.editName.setText(editData.getPeriodName());
            StaticUtility.getDateFromString(editData.getActualDate(), binding.editDate);
            binding.editDetail.setText(editData.getPeriodRemarks());
        }
        */
    }
}