package com.villagebanking.ui.Master;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.Utility.CustomAdapter;
import com.villagebanking.R;
import com.villagebanking.Utility.StaticUtility;
import com.villagebanking.databinding.FragmentGroupBinding;

import java.util.Calendar;

public class GroupFragment extends Fragment {
    private FragmentGroupBinding binding;
    int key = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initialize();
        return root;
    }


    void initialize()   {
        binding.btnSave.setOnClickListener(clickMethod());
        binding.btnDelete.setOnClickListener(clickMethod());
        binding.btnNew.setOnClickListener(clickMethod());
        binding.gvGroup.setOnItemClickListener(itemSelect());
        fillGridView();
        binding.editStartDate.setOnClickListener(StaticUtility.DisplayDate(getContext(),binding.editStartDate));

    }

    private void visibleFields(int visiblity)
    {
        binding.row1.setVisibility(visiblity);
        binding.row2.setVisibility(visiblity);
        binding.row3.setVisibility(visiblity);
        binding.row4.setVisibility(visiblity);
        binding.row5.setVisibility(visiblity);
        binding.row6.setVisibility(visiblity);
        binding.row7.setVisibility(visiblity);
        binding.row8.setVisibility(visiblity);
    }
    private void btnVisiblity(String type) {

        binding.btnSave.setVisibility(View.GONE);
        binding.btnNew.setVisibility(View.GONE);
        binding.btnDelete.setVisibility(View.GONE);
        visibleFields(View.GONE);

        switch (type) {
            case "Load":
                binding.btnNew.setVisibility(View.VISIBLE);
                visibleFields(View.VISIBLE);
            case "Save":
                binding.btnNew.setVisibility(View.VISIBLE);
                break;
            case "Delete":
                binding.btnNew.setVisibility(View.VISIBLE);
                break;
            case "New":
                binding.btnSave.setVisibility(View.VISIBLE);
                break;
            case "Query":
                binding.btnNew.setVisibility(View.VISIBLE);
                binding.btnDelete.setVisibility(View.VISIBLE);
                visibleFields(View.VISIBLE);
        }
    }


    public View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b = ((MaterialButton) view).getText().toString();
                switch (b) {
                    case "Save":
                        clickSave();
                        break;
                    case "Delete":
                        clickDelete();
                        break;
                    case "New":
                        clickNew();
                        break;
                }
            }
        };
    }

    void clickSave() {
        BOGroup newIns = getDataFromView();
        DBUtility.DTOSaveUpdate(newIns);
        fillGridView();
        btnVisiblity("Save");
    }

    void clickDelete() {
        DBUtility.DTOdelete(key, StaticUtility.GROUPS);
        key = 0;
        fillGridView();
        btnVisiblity("Delete");
    }

    void clickNew() {

        key = 0;
        DataToView(new BOGroup());
        btnVisiblity("New");
    }


    BOGroup getDataFromView() {
        BOGroup newData = new BOGroup();
        newData.setKeyID(key);
        newData.setName(binding.editName.getText().toString());
        newData.setNoOfPerson(Integer.valueOf(binding.editNoOfPerson.getText().toString()));
        newData.setAmount(Double.parseDouble(binding.editAmount.getText().toString()));
        return newData;
    }

    String DataToView(BOGroup g) {
        key = g.getKeyID();
        binding.editName.setText(g.getName());
        binding.editNoOfPerson.setText(g.getNoOfPerson() > 0 ? Integer.toString(g.getNoOfPerson()) : "");
        binding.editAmount.setText(g.getAmount() > 0 ? Double.toString(g.getAmount()) : "");
        return "Ok";
    }

    void fillGridView() {
        CustomAdapter adapter = new CustomAdapter(this.getContext(), R.layout.activity_gridview,
                DBUtility.DTOGetAlls(StaticUtility.GROUPS), "A");
        binding.gvGroup.setAdapter(adapter);
    }
    AdapterView.OnItemClickListener itemSelect() {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected item
                BOGroup seletedData = (BOGroup) adapterView.getItemAtPosition(i);
                DataToView(seletedData);
                btnVisiblity("Query");
            }
        };
    }
}
