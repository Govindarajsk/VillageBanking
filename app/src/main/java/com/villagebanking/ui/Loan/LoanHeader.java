package com.villagebanking.ui.Loan;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOLoanDetail;
import com.villagebanking.BOObjects.BOLoanHeader;
import com.villagebanking.BOObjects.BOPerson;

import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.DBTables.tblLoanDetail;
import com.villagebanking.DBTables.tblLoanHeader;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.ui.UIUtility;
import com.villagebanking.databinding.LoanHeaderViewBinding;

import java.util.ArrayList;

public class LoanHeader extends Fragment {
    private LoanHeaderViewBinding binding;

    //region Initialize
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LoanHeaderViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initialize() {
        String fromPage = "";
        if (getArguments() != null) {
            fromPage = getArguments().getString("PAGE");
        }
        binding.glEditView.setVisibility(View.GONE);
        binding.glLoanDetail.setVisibility(View.GONE);
        binding.glGridView.setVisibility(View.GONE);
        loadHeader(fromPage);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void loadHeader(String fromPage) {
        ArrayList<BOLoanHeader> boLoanHeaders = new ArrayList<>();
        switch (fromPage) {
            case tblPerson.Name:
                long personKey = getArguments().getLong("ID");
                boLoanHeaders = tblLoanHeader.GetList(0, personKey);
                if (boLoanHeaders.size() == 0) {
                    fill_person(personKey);
                    fill_group(0);
                    fill_Type(1);
                    binding.glEditView.setVisibility(View.VISIBLE);
                    binding.glLoanDetail.setVisibility(View.VISIBLE);

                    binding.editLoanAmount.addTextChangedListener(textChangedEvent());
                    binding.btnSave.setOnClickListener(clickSave());
                } else {
                    String personName = boLoanHeaders.get(0).getPersonKey().getDisplayValue();
                    binding.lblName.setText(personName);
                    binding.glGridView.setVisibility(View.VISIBLE);
                    loadGridView(boLoanHeaders);
                }
                break;
            case tblLoanHeader.Name:
                long loanKey = getArguments().getLong("ID");
                ArrayList<BOLoanDetail> loanDetails = tblLoanDetail.GetList(loanKey);
                binding.glLoanDetail.setVisibility(View.VISIBLE);
                loadDetailGrid(loanDetails);
                break;
            default:
                loanKey = 0;
                loanDetails = tblLoanDetail.GetList(loanKey);
                binding.glLoanDetail.setVisibility(View.VISIBLE);
                loadDetailGrid(loanDetails);
                break;
        }
    }
    //endregion

    //region Load/Fill Fields
    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_person(long personKey) {
        ArrayList<BOPerson> people = DBUtility.DTOGetData(tblPerson.Name, 0);
        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        people.stream().forEach(x -> keyValues.add(new BOKeyValue(x.getPrimary_key(), x.getFullName())));

        UIUtility.getAutoBox(this.getContext(), keyValues, binding.editPerson, null, personKey);
        UIUtility.getAutoBox(this.getContext(), keyValues, binding.editReference1, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_group(long groupKey) {
        ArrayList<BOGroup> groups = DBUtility.DTOGetData(tblGroup.Name, groupKey);

        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        groups.stream().forEach(x -> keyValues.add(new BOKeyValue(x.getPrimary_key(), x.getName(), x)));
        UIUtility.getAutoBox(this.getContext(), keyValues, binding.editGroup, null);

        binding.editGroup.setOnItemClickListener(selectedEvent(binding.editGroup));
        selectedEvent(binding.editGroup);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_Type(long type) {
        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        keyValues.add(new BOKeyValue(1, "Commission"));
        keyValues.add(new BOKeyValue(2, "EMI"));
        UIUtility.getAutoBox(this.getContext(), keyValues, binding.editLoanType, null, type);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void loadGridView(ArrayList<BOLoanHeader> boLoanHeaders) {
        boLoanHeaders.sort((t1, t2) -> Long.toString(t1.getGroupKey().getPrimary_key()).compareTo(Long.toString(t2.getGroupKey().getPrimary_key())));
        LoanHeaderGrid adapter = new LoanHeaderGrid(this.getContext(), R.layout.loan_header_gridview, boLoanHeaders);
        binding.gvDataView.setAdapter(adapter);
    }

    // endregion

    //region Loan Detail
    @RequiresApi(api = Build.VERSION_CODES.N)
    void calculation(double value) {
        Double actualValue = value - UIUtility.ToDouble(binding.lblBondCharge.getText().toString());
        UIUtility.applyValue(binding.lblPayable, actualValue);
        ArrayList<BOLoanDetail> boLoanDetails = getLoanDetail(value);
        loadDetailGrid(boLoanDetails);
    }

    ArrayList<BOLoanDetail> getLoanDetail(double value) {
        ArrayList<BOLoanDetail> boLoanDetails = new ArrayList<>();
        double withInterest = value + (value / 5);
        double eachPeriod = withInterest / 6;
        for (int i = 1; i <= 6; i++) {
            BOLoanDetail boLoanDetail = new BOLoanDetail();
            boLoanDetail.setEmiNo(i);
            boLoanDetail.setLoanHeaderKey(i);
            boLoanDetail.setEmiAmount(eachPeriod);
            boLoanDetail.setPeriodInfo(new BOKeyValue(1, "12/05/1990"));
            boLoanDetails.add(boLoanDetail);
        }
        return boLoanDetails;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void detailSave(long key) {
        for (int i = 0; i < binding.gvLoanDetail.getCount(); i++) {
            BOLoanDetail loanDetail = (BOLoanDetail) binding.gvLoanDetail.getItemAtPosition(i);
            loanDetail.setLoanHeaderKey(key);
            tblLoanDetail.Save("I", loanDetail);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void loadDetailGrid(ArrayList<BOLoanDetail> loanDetails) {
        loanDetails.sort((t1, t2) -> Long.toString(t1.getEmiNo()).compareTo(Long.toString(t2.getEmiNo())));
        LoanDetailGrid adapter = new LoanDetailGrid(this.getContext(), R.layout.loan_detail_gridview, loanDetails);
        binding.gvLoanDetail.setAdapter(adapter);
    }
    //endregion

    //region Events
    TextWatcher textChangedEvent() {
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable s) {
                Double value = s != null && s.length() > 0 ? Double.parseDouble(s.toString()) : 0.00;
                calculation(value);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ShowMessage(context, "beforeTextChanged");
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double value = s != null && s.length() > 0 ? Double.parseDouble(s.toString()) : 0.00;
                calculation(value);
            }
        };
        return fieldValidatorTextWatcher;
    }

    AdapterView.OnItemClickListener selectedEvent(AutoCompleteTextView control) {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                BOGroup selectedValue = (BOGroup) UIUtility.GetAutoBoxSelected(control).getActualObject();
                binding.lblBondCharge.setText(String.valueOf(selectedValue.getBondCharge()));
            }
        };
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
                detailSave(1);
            }
        };
    }
    //endregion
}
