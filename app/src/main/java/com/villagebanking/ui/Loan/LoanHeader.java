package com.villagebanking.ui.Loan;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOLoanDetail;
import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Controls.AutoBox;
import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.DBTables.tblLoanHeader;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.ui.Transaction.TransHeaderGrid;
import com.villagebanking.ui.UIUtility;
import com.villagebanking.databinding.LoanHeaderViewBinding;

import java.util.ArrayList;

public class LoanHeader extends Fragment {
    private LoanHeaderViewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LoanHeaderViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initialize() {
        binding.glEditView.setVisibility(View.GONE);
        if (getArguments() != null) {
            binding.glEditView.setVisibility(View.VISIBLE);
            String fromPage = getArguments().getString("PAGE");

            switch (fromPage) {
                case tblPerson.Name:
                    long person_Key = getArguments().getLong("ID");
                    fill_person(person_Key);
                    fill_group(0);
                    fill_Type(1);
                    break;
                default:
                    break;
            }
            binding.editLoanAmount.addTextChangedListener(textChangedEvnt());
            binding.btnSave.setOnClickListener(clickSave());
        } else {
            binding.glGridView.setVisibility(View.VISIBLE);
            loadGridView();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_person(long personKey) {
        ArrayList<BOPerson> people = DBUtility.DTOGetData(tblPerson.Name, 0);
        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        people.stream().forEach(x -> keyValues.add(new BOKeyValue(x.getPrimary_key(), x.getFullName())));

        UIUtility.getAutoBox(this.getContext(), keyValues, binding.editPerson, personKey);
        UIUtility.getAutoBox(this.getContext(), keyValues, binding.editReference1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_group(long groupKey) {
        ArrayList<BOGroup> groups = DBUtility.DTOGetData(tblGroup.Name, groupKey);

        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        groups.stream().forEach(x -> keyValues.add(new BOKeyValue(x.getPrimary_key(), x.getName(), x)));

        AutoBox autoBox = UIUtility.getAutoBox(this.getContext(), keyValues, binding.editGroup);

        BOGroup selectedValue = (BOGroup) autoBox.getSelectedItem().getActualObject();
        binding.lblBondCharge.setText(String.valueOf(selectedValue.getBondCharge()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_Type(long type) {
        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        keyValues.add(new BOKeyValue(1, "Commision"));
        keyValues.add(new BOKeyValue(2, "EMI"));
        UIUtility.getAutoBox(this.getContext(), keyValues, binding.editLoanType, type);
    }

    View.OnClickListener clickSave() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                BOKeyValue person = UIUtility.GetAutoBoxSelected(binding.editPerson);
                BOKeyValue group = UIUtility.GetAutoBoxSelected(binding.editGroup);
                BOKeyValue lonType = UIUtility.GetAutoBoxSelected(binding.editLoanType);
                BOKeyValue ref1 = UIUtility.GetAutoBoxSelected(binding.editReference1);

                BOLoanHeader loanHeader = new BOLoanHeader();
                loanHeader.getPersonKey().setPrimary_key(person.getPrimary_key());
                loanHeader.getGroupKey().setPrimary_key(group.getPrimary_key());
                loanHeader.getLoanType().setPrimary_key(lonType.getPrimary_key());
                loanHeader.setLoanAmount(UIUtility.ToDouble(binding.editLoanAmount));
                loanHeader.setOtherAmount(UIUtility.ToDouble(binding.lblOtherAmount));
                loanHeader.setTransDate(UIUtility.getCurrentDate());
                loanHeader.setRemarks("ok");
                loanHeader.getReference1().setPrimary_key(ref1.getPrimary_key());
                double withInterest = loanHeader.getLoanAmount() + (loanHeader.getLoanAmount() / 5);
                loanHeader.setRepayAmount(withInterest);
                tblLoanHeader.Save("I", loanHeader);

                ArrayList<LoanHeader> lList = DBUtility.DTOGetAlls(tblLoanHeader.Name);
            }
        };
    }

    TextWatcher textChangedEvnt() {
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Double value = s != null && s.length() > 0 ? Double.parseDouble(s.toString()) : 0.00;
                calculation(value);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ShowMessage(context, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double value = s != null && s.length() > 0 ? Double.parseDouble(s.toString()) : 0.00;
                calculation(value);
            }
        };
        return fieldValidatorTextWatcher;
    }

    void calculation(double value) {
        Double actualValue = value - UIUtility.ToDouble(binding.lblBondCharge.getText().toString());
        UIUtility.applyValue(binding.lblPayable, actualValue);
        ArrayList<BOLoanDetail> boLoanDetails = getLoanDetail(value);
        LoanDetailGrid adapter = new LoanDetailGrid(this.getContext(), R.layout.loan_detail_gridview, boLoanDetails);
        binding.gvLoanDetail.setAdapter(adapter);
    }

    ArrayList<BOLoanDetail> getLoanDetail(double value) {
        ArrayList<BOLoanDetail> boLoanDetails = new ArrayList<>();
        double withInterest = value + (value / 5);
        double eachperiod = withInterest / 6;
        for (int i = 1; i <= 6; i++) {
            BOLoanDetail boLoanDetail = new BOLoanDetail();
            boLoanDetail.setInstallment(i);
            boLoanDetail.setAmount(eachperiod);
            boLoanDetail.setPeriodInfo(new BOKeyValue(1, "12/05/1990"));
            boLoanDetails.add(boLoanDetail);
        }
        return boLoanDetails;
    }

    void loadGridView() {
        ArrayList<BOLoanHeader> boLoanHeaders = tblLoanHeader.GetList(0);
        LoanHeaderGrid adapter = new LoanHeaderGrid(this.getContext(), R.layout.loan_header_gridview, boLoanHeaders);
        binding.gvDataView.setAdapter(adapter);
    }
}
