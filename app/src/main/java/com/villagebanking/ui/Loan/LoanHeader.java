package com.villagebanking.ui.Loan;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.Database.DBUtility;
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

        binding.editLoanAmount.addTextChangedListener(textChangedEvnt());

        binding.editPerson.setOnItemClickListener(periodItemSelected());
        //binding.editGroup.setOnItemClickListener(periodTypeItemSelected());

        if (getArguments() != null) {
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
        }
        binding.btnSave.setOnClickListener(clickSave());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_person(long personKey) {
        ArrayList<BOPerson> people = DBUtility.DTOGetData(tblPerson.Name, personKey);
        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        people.stream().forEach(x -> keyValues.add(new BOKeyValue(x.getPrimary_key(), x.getFullName())));

        UIUtility.LoadAutoBox(this.getContext(), keyValues, binding.editPerson, personKey);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_group(long groupKey) {
        ArrayList<BOGroup> groups = DBUtility.DTOGetData(tblGroup.Name, groupKey);

        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        groups.stream().forEach(x -> keyValues.add(new BOKeyValue(x.getPrimary_key(), x.getName(), x)));
        BOKeyValue selected = UIUtility.LoadAutoBox(this.getContext(), keyValues, binding.editGroup);

        BOGroup selectedValue = (BOGroup) selected.getActualObject();
        binding.lblBondCharge.setText(String.valueOf(selectedValue.getBondCharge()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_Type(long type) {
        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        keyValues.add(new BOKeyValue(1, "Commision"));
        keyValues.add(new BOKeyValue(2, "EMI"));
        UIUtility.LoadAutoBox(this.getContext(), keyValues, binding.editLoanType, type);
    }

    View.OnClickListener clickSave() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

            }
        };
    }

    TextWatcher textChangedEvnt() {
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Double value = s != null ? Double.valueOf(s.toString()) : 0.00;
                calculation(value);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ShowMessage(context, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double value = s != null ? Double.valueOf(s.toString()) : 0.00;
                calculation(value);
            }
        };
        return fieldValidatorTextWatcher;
    }
    void calculation(double value){
        Double actualValue = value - UIUtility.ToDouble(binding.lblBondCharge.getText().toString());
        UIUtility.applyValue(binding.lblPayable, actualValue);
    }

    AdapterView.OnItemClickListener periodItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                long key = UIUtility.getAutoBoxKey(parent.getItemAtPosition(position));
            }
        };
    }
}
