package com.villagebanking.ui.Links;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOGroupPersonLink;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTransaction;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Utility.CustomAdapter;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.PersonTransactionBinding;

import java.util.ArrayList;
import java.util.function.Predicate;

public class PersonTrans extends Fragment {

    private PersonTransactionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PersonTransactionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //region Click Listener
    void initialize() {
        binding.btnSave.setOnClickListener(clickMethod());
        binding.btnDelete.setOnClickListener(clickMethod());
        binding.btnNew.setOnClickListener(clickMethod());
        binding.gvDataView.setOnItemClickListener(itemSelect());
        binding.editPerson.setOnItemSelectedListener(itemSelected());
        //fillDGridView(DBUtility.DTOGetAlls(StaticUtility.PERSON_TRANSACTION));
        fillGroupPerson(binding.editPerson);
    }

    View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
                selectedData = (BOPersonTransaction) adapterView.getItemAtPosition(i);
                DataToView(selectedData);
                btnVisiblity("Query");
            }
        };
    }

    AdapterView.OnItemSelectedListener itemSelected() {
        return new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the selected item
                DataToView(ClearNew());
                BOPerson seletedData = (BOPerson) adapterView.getItemAtPosition(i);
                personSelectedData(seletedData);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }
    //endregion

    //region Click Methods
    BOPersonTransaction selectedData =null;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clickSave(View view) {
        //BOPersonTransaction selectedData = (BOPersonTransaction) binding.gvDataView.getSelectedItem();
        ViewToData(selectedData);
        DBUtility.DTOSaveUpdate(selectedData, DB1Tables.PERSON_TRANSACTION);
        btnVisiblity("Save");
        DataToView(ClearNew());
        personSelectedData((BOPerson) binding.editPerson.getSelectedItem());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clickDelete(View view) {
        DBUtility.DTOdelete(key, DB1Tables.PERSON_TRANSACTION);
        key = 0;
        btnVisiblity("Delete");
        DataToView(ClearNew());
        personSelectedData((BOPerson) binding.editPerson.getSelectedItem());
    }

    public void clickNew(View view) {
        DataToView(ClearNew());
        btnVisiblity("New");
    }
    //endregion

    int key = 0;

    //region Support Methods => Edit Required


    private void btnVisiblity(String type) {
        switch (type) {
            case "Load":
                binding.btnSave.setVisibility(View.VISIBLE);
                binding.btnNew.setVisibility(View.GONE);
                binding.btnDelete.setVisibility(View.GONE);
            case "Save":
                binding.btnNew.setVisibility(View.VISIBLE);
                binding.btnDelete.setVisibility(View.GONE);
                break;
            case "Delete":
                binding.btnSave.setVisibility(View.VISIBLE);
                binding.btnNew.setVisibility(View.GONE);
                binding.btnDelete.setVisibility(View.GONE);
                break;
            case "New":
                binding.btnSave.setVisibility(View.VISIBLE);
                binding.btnNew.setVisibility(View.GONE);
                binding.btnDelete.setVisibility(View.GONE);
                break;
            case "Query":
                binding.btnNew.setVisibility(View.GONE);
                binding.btnDelete.setVisibility(View.VISIBLE);
        }
    }

    //Save
    BOPersonTransaction ViewToData(BOPersonTransaction newData) {
        newData.setAmount(Double.valueOf(binding.editAmount.getText().toString()));
        return newData;
    }

    int selectedRow = 0;

    //Display
    @RequiresApi(api = Build.VERSION_CODES.N)
    void personSelectedData(BOPerson seletedData) {

        ArrayList<BOGroupPersonLink> gpList = DBUtility.DTOGetAlls(DB1Tables.GROUP_PERSON_LINK);
        Predicate<BOGroupPersonLink> condition = x -> x.getPerson_key() != seletedData.getKeyID();
        gpList.removeIf(condition);

        if (gpList.stream().count() > 0) {
            transList = new ArrayList<>();
            gpList.forEach((d) -> transList.add(getBOgroup(d)));

            totalAmount = 0.00;
            transList.forEach(x -> getDouble(x.getCalcAmount()));
            binding.lblTotalAmount.setText(String.format("%.2f", totalAmount));

            transList.forEach(x -> getFinal(x));
            totalAmount = 0.00;
            transList.forEach(x -> getDouble(x.getAmount()));
            binding.lblPaidAmount.setText(String.format("%.2f", totalAmount));

            binding.lblBalmount.setText(String.format("%.2f",
                    (Double.valueOf(binding.lblTotalAmount.getText().toString())-
                           Double.valueOf(binding.lblPaidAmount.getText().toString()))
            ));

            fillDGridView(transList);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    BOPersonTransaction getFinal(BOPersonTransaction linkdata) {
        ArrayList<BOPersonTransaction> pTrans = DBUtility.DTOGetAlls(DB1Tables.PERSON_TRANSACTION);
        pTrans.removeIf(x -> x.getGp_key() != linkdata.getGp_key());
        if (pTrans.stream().count() > 0)
            linkdata.setKeyID(pTrans.stream().findFirst().get().getKeyID());
        totalAmount = 0.00;
        pTrans.forEach(x -> getDouble(x.getAmount()));
        linkdata.setAmount(totalAmount);
        return linkdata;
    }

    Double totalAmount = 0.00;
    ArrayList<BOPersonTransaction> transList = new ArrayList<>();

    Double getDouble(Double amount) {
        totalAmount = totalAmount + amount;
        return totalAmount;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    BOPersonTransaction getBOgroup(BOGroupPersonLink linkdata) {
        ArrayList<BOGroup> groupLst = DBUtility.DTOGetAlls(DB1Tables.GROUPS);
        groupLst.removeIf(x -> x.getKeyID() != linkdata.getGroup_key());
        BOGroup gpData = (BOGroup) groupLst.get(0);

        BOPersonTransaction data = new BOPersonTransaction();
        data.setGp_key(linkdata.getKeyID());
        data.setLinktype('G');
        data.setLinkName(gpData.getName());
        data.setCalcAmount(gpData.getAmount());
        data.setAmount(0.00);
        gpData.setKeyID(linkdata.getKeyID());
        return data;
    }

    //Display
    void DataToView(BOPersonTransaction data) {
        key = data.getKeyID();
        //binding.editTransDate.setText(data.getTransDate().toString());

        binding.lblNarration.setText(data.getLinkName());
        binding.lblpendingAmount.setText(Double.toString(data.getCalcAmount()));
        binding.editAmount.setText(Double.toString(data.getAmount()));
    }

    //New
    BOPersonTransaction ClearNew() {
        BOPersonTransaction newData = new BOPersonTransaction();
        return newData;
    }
    //endregion

    //region Fill
    void fillDGridView(ArrayList<BOPersonTransaction> transactions) {
        if (transactions == null)
            transactions = DBUtility.DTOGetAlls(DB1Tables.PERSON_TRANSACTION);

        CustomAdapter adapter = new CustomAdapter(this.getContext(), R.layout.activity_gridview,
                transactions, "A");
        binding.gvDataView.setAdapter(adapter);
    }

    void fillGroupPerson(Spinner spinner) {
        CustomAdapter adapter = new CustomAdapter(this.getContext(), R.layout.activity_gridview,
                DBUtility.DTOGetAlls(DB1Tables.PERSONS), "B");

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(selectedRow);
    }
    //endregion
}