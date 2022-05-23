package com.villagebanking.ui.Transaction;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOAutoComplete;
import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOPerson;
import com.villagebanking.BOObjects.BOPersonTrans;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.UIUtility;
import com.villagebanking.databinding.PersonsTransLinkviewBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransPerson extends Fragment {
    private PersonsTransLinkviewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PersonsTransLinkviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;
    }

    BOPersonTrans selectedData = new BOPersonTrans();

    AdapterView.OnItemClickListener personSelected() {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                long person_Key = UIUtility.getAutoBoxKey(parent.getItemAtPosition(position));

                selectedData.getDetail2().setPrimary_key(person_Key);

                fill_Person_trans(person_Key, selectedData.getForien_key());
            }
        };
    }

    AdapterView.OnItemClickListener periodSelected() {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                long period_key = UIUtility.getAutoBoxKey(parent.getItemAtPosition(position));
                selectedData.getDetail2().setPrimary_key(period_key);
                fill_Person_trans(selectedData.getDetail2().getPrimary_key(), selectedData.getForien_key());
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initialize() {
        if (getArguments() != null) {
            long person_key = getArguments().getLong("person_key");
            long group_key = getArguments().getLong("group_key");
            long period_key = getArguments().getLong("period_key");

            if (person_key > 0) {
                ArrayList<BOPerson> persons = DBUtility.DTOGetData(DB1Tables.PERSONS, person_key);
                if (persons.size() > 0) {
                    selectedData.setDetail2(new BOAutoComplete(persons.get(0).getPrimary_key(),persons.get(0).getFullName()));
                }
            }
            fillPerson(person_key);

            if (group_key > 0) {
                //ArrayList<BOGroup> groups = DBUtility.DTOGetData(DB1Tables.GROUPS, group_key);
                selectedData.setForien_key(group_key);
            }

            if (period_key > 0) {
                ArrayList<BOPeriod> periods = DBUtility.DTOGetData(DB1Tables.PERIODS, period_key);
                if (periods.size() > 0) {
                    selectedData.setPeriod_detail(periods.get(0));
                }
            }
            fillPeriod(period_key);
            fill_Person_trans(person_key, period_key);
        }
        binding.editPerson.setOnItemClickListener(personSelected());
        binding.editPeriod.setOnItemClickListener(periodSelected());
        binding.btnSave.setOnClickListener(clickSave());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fillPerson(long person_key) {
        ArrayList<BOPerson> list = DBUtility.DTOGetAlls(DB1Tables.PERSONS);
        ArrayList<BOAutoComplete> autoCompleteList = new ArrayList<>();
        list.forEach(
                x -> autoCompleteList.add(new BOAutoComplete(x.getPrimary_key(), x.getStrFName() + "-" + x.getStrLName()))
        );
        UIUtility.SetAutoCompleteBox(this.getContext(), autoCompleteList, binding.editPerson, person_key);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fillPeriod(long period_key) {
        ArrayList<BOPeriod> boPeriods = DBUtility.DTOGetAlls(DB1Tables.PERIODS);
        ArrayList<BOAutoComplete> autoCompleteList1 = new ArrayList<>();
        for (BOPeriod item : boPeriods) {
            autoCompleteList1.add(new BOAutoComplete(item.getPrimary_key(), item.getActualDate()));
        }
        UIUtility.SetAutoCompleteBox(this.getContext(), autoCompleteList1, binding.editPeriod, period_key);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_Person_trans(long person_key, long group_key) {
        ArrayList<BOPersonTrans> trans = DBUtility.DTOGetData(DB1Tables.PERSON_TRANSACTION, person_key);
       /*
        ArrayList<BOPersonTrans> copyTrans = DBUtility.DTOGetData(DB1Tables.PERSON_TRANSACTION, person_key);

        if (group_key > 0) {
            trans.removeIf(x -> x.getForien_key() != group_key);
            copyTrans.removeIf(x -> x.getForien_key() != group_key);
        }
        */
        calculateAmount(trans);

        Collections.sort(trans,(x,y)->String.valueOf(x.getTable_link_key()).compareToIgnoreCase(
                String.valueOf(y.getTable_link_key())));

        TransEditGrid adapter = new TransEditGrid(this.getContext(), R.layout.groups_gridview, trans);
        binding.gvDataView.setAdapter(adapter);
        binding.gvDataView.addOnLayoutChangeListener(layoutChanged(trans));

    }

    Double totalAmount = 0.0;
    Double totalPaid = 0.0;
    Double totalBal = 0.0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    void calculateAmount(ArrayList<BOPersonTrans> fullList) {
        totalAmount = totalPaid = totalBal = 0.0;

        Map<Long, ArrayList<BOPersonTrans>> uniqueList = UIUtility.GetPersonAmount(fullList);
        uniqueList.forEach((x, y) -> applyVal(x, y, fullList));

        totalBal = totalAmount - totalPaid;
        binding.txtTotal.setText(totalAmount.toString());
        binding.txtPaid.setText(totalPaid.toString());
        binding.txtBalance.setText(totalBal.toString());
    }

    Double balEach = 0.0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    void applyVal(Long key, ArrayList<BOPersonTrans> value, ArrayList<BOPersonTrans> fullList) {
        balEach = 0.0;

        Stream<BOPersonTrans> groupListAmount =
                fullList.stream().filter(x -> x.getTable_link_key() == key);

        List<BOPersonTrans> okGk = groupListAmount.collect(Collectors.toList());

        BOPersonTrans transData = null;
        Integer fSize = okGk.size();

        if (fSize > 0) {
            transData = okGk.get(fSize - 1);
            balEach =  okGk.get(0).getActualAmount();
            okGk.forEach(x -> fillBalAmount(x));

            if (balEach != 0 && transData.getPrimary_key() > 0) {
                BOPersonTrans newData = new BOPersonTrans();
                newData.setParentKey(transData.getPrimary_key());
                newData.setTableName(transData.getTableName());
                newData.setTableName(transData.getTableName());
                newData.setTable_link_key(transData.getTable_link_key());
                newData.setForien_key(transData.getForien_key());
                newData.setPeriod_detail(transData.getPeriod_detail());
                newData.setRemarks(transData.getRemarks());
                newData.setDetail2(transData.getDetail2());
                newData.setActualAmount(balEach);
                newData.setNewAmount(0.00);
                fullList.add(newData);
            }
        }
    }


    void fillBalAmount(BOPersonTrans x) {
        totalPaid = totalPaid + x.getNewAmount();
        if (x.getParentKey() == 0) {
            totalAmount = totalAmount + x.getActualAmount();
        } else {
            x.setActualAmount(balEach);
        }
        balEach = balEach - x.getNewAmount();
    }

    View.OnClickListener clickSave() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                for (int i = 0; i < binding.gvDataView.getCount(); i++) {
                    BOPersonTrans transaction = (BOPersonTrans) binding.gvDataView.getItemAtPosition(i);
                    if (transaction.getNewAmount() != 0 || transaction.getPrimary_key() > 0)
                        DBUtility.DTOSaveUpdate(transaction, DB1Tables.PERSON_TRANSACTION);
                }
                fill_Person_trans(selectedData.getDetail2().getPrimary_key(), selectedData.getForien_key());
            }
        };
    }

    AdapterView.OnLayoutChangeListener layoutChanged(ArrayList<BOPersonTrans> newList) {
        return new AdapterView.OnLayoutChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                calculateAmount(newList);
            }
        };
    }
}