package com.villagebanking.ui.Master;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Utility.CustomAdapter;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.StaticUtility;
import com.villagebanking.databinding.FragmentPersonsBinding;

public class PersonsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPersonsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        
    }

    int key = 0;
    private FragmentPersonsBinding binding;
    TextView txtFName, txtLName, txtMobile;
    Button btnNew, btnSave, btnDelete;

    void initialize()   {
        txtFName = binding.editFName;
        txtMobile = binding.editMobile;
        txtLName = binding.editLName;

        btnDelete = binding.btnDelete;
        btnNew = binding.btnNew;
        btnSave = binding.btnSave;

        btnSave.setVisibility(View.VISIBLE);
        btnNew.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.GONE);

        btnSave.setOnClickListener(clickMethod());
        btnDelete.setOnClickListener(clickMethod());
        btnNew.setOnClickListener(clickMethod());
        binding.gvNames.setOnItemClickListener(itemSelect());
        assignToGridView();
    }

    private void btnVisiblity(String type) {
        switch (type) {
            case "Load":
                binding.btnSave.setVisibility(View.VISIBLE);
                binding.btnNew.setVisibility(View.VISIBLE);
                binding.btnDelete.setVisibility(View.GONE);
            case "Save":
                binding.btnNew.setVisibility(View.VISIBLE);
                binding.btnDelete.setVisibility(View.GONE);
                break;
            case "Delete":
                binding.btnSave.setVisibility(View.VISIBLE);
                binding.btnNew.setVisibility(View.VISIBLE);
                binding.btnDelete.setVisibility(View.GONE);
                break;
            case "New":
                binding.btnSave.setVisibility(View.VISIBLE);
                binding.btnNew.setVisibility(View.VISIBLE);
                binding.btnDelete.setVisibility(View.GONE);
                break;
            case "Query":
                binding.btnNew.setVisibility(View.VISIBLE);
                binding.btnDelete.setVisibility(View.VISIBLE);
        }
    }

    //region Click Listener
    View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b = ((MaterialButton) view).getText().toString();
                switch (b) {
                    case "Save":
                        clickSave(view);
                        break;
                    case "Delete":
                        clickDelete(view);
                        break;
                    case "New":
                        clickNew(view);
                        break;
                }
            }
        };
    }

    AdapterView.OnItemClickListener itemSelect() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected item
                BOPerson person = (BOPerson) adapterView.getItemAtPosition(i);
                assignPersonDataToView(person);
                btnVisiblity("Query");
            }
        };
    }
    //endregion

    //region Click Methods
    public void clickSave(View view) {
        DBUtility.DTOSaveUpdate(getPersonDataFromView());
        assignToGridView();
        btnVisiblity("Save");
    }

    public void clickDelete(View view) {
        DBUtility.DTOdelete(key, StaticUtility.PERSONS);
        key = 0;
        assignToGridView();
        btnVisiblity("Delete");
    }

    public void clickNew(View view) {
        assignPersonDataToView(new BOPerson());
        btnVisiblity("New");
    }

    //endregion

    //region Support Methods
    BOPerson getPersonDataFromView() {
        BOPerson newData = new BOPerson();
        newData.setKeyID(key);
        newData.setStrFName(txtFName.getText().toString());
        newData.setStrLName(txtLName.getText().toString());
        newData.setNumMobile(Long.valueOf(txtMobile.getText().toString()));
        return newData;
    }

    void assignPersonDataToView(BOPerson person) {
        key = person.getKeyID();
        txtFName.setText(person.getStrFName());
        txtLName.setText(person.getStrLName());
        txtMobile.setText(Long.toString(person.getNumMobile()));
    }

    void assignToGridView() {
        CustomAdapter adapter = new CustomAdapter(this.getContext(), R.layout.activity_gridview,
                DBUtility.DTOGetAlls(StaticUtility.PERSONS), "A");
        binding.gvNames.setAdapter(adapter);
    }

    //endregion
}
