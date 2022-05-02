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
import com.villagebanking.BOObjects.BOPersonTransaction;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.StaticUtility;
import com.villagebanking.databinding.LinkviewPersonsTransBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

public class PersonTrans extends Fragment {
    private LinkviewPersonsTransBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LinkviewPersonsTransBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        long key = getArguments().getLong("primary_key");
        initilize(key);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void initilize(long key) {
        person_key = key;
        fill_Person(person_key);
        fill_periods();
        binding.editPeriod.setOnItemClickListener(periodItemSelected());
        binding.editPerson.setOnItemClickListener(personItemSelected());
        binding.btnSave.setOnClickListener(clickMethod());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_Person(long key) {
        ArrayList<BOPerson> list = DBUtility.DTOGetAlls(DB1Tables.PERSONS);
        ArrayList<BOAutoComplete> autoCompleteList = new ArrayList<>();
        list.forEach(x -> autoCompleteList.add
                (
                        new BOAutoComplete(x.getPrimary_key(),
                                x.getStrFName() + "-" + x.getStrLName()))
        );
        StaticUtility.SetAutoCompleteBox(this.getContext(), autoCompleteList, binding.editPerson);

        Optional<BOAutoComplete> item = autoCompleteList.stream().filter(x -> x.getPrimary_key() == key).findFirst();
        if (item.get() != null)
            binding.editPerson.setText(item.get().getDisplayValue());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_periods() {
        ArrayList<BOPeriod> boPeriods = DBUtility.DTOGetAlls(DB1Tables.PERIODS);
        ArrayList<BOAutoComplete> autoCompleteList = new ArrayList<>();
        for (BOPeriod item : boPeriods) {
            autoCompleteList.add(new BOAutoComplete(item.getPrimary_key(), item.getActualDate()));
        }
        StaticUtility.SetAutoCompleteBox(this.getContext(), autoCompleteList, binding.editPeriod);


        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String date = df.format(Calendar.getInstance().getTime());

        Optional<BOPeriod> fItem = boPeriods.stream().findFirst();//.stream().filter(x -> x.getPeriodValue() >= Long.valueOf(date)).findFirst();
        if (fItem.isPresent()) {
            long key = fItem.get().getPrimary_key();
            Optional<BOAutoComplete> item = autoCompleteList.stream().filter(x -> x.getPrimary_key() == key).findFirst();
            if (item.get() != null) {
                binding.editPeriod.setText(item.get().getDisplayValue());
                period_key = item.get().getPrimary_key();
                fill_Person_trans(person_key, period_key);
            }
        }
    }

    long person_key = 0;

    AdapterView.OnItemClickListener personItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof BOAutoComplete) {
                    BOAutoComplete itemSelected = (BOAutoComplete) item;
                    person_key = itemSelected.getPrimary_key();
                    fill_Person_trans(person_key, period_key);
                }
            }
        };
    }

    long period_key = 0;

    AdapterView.OnItemClickListener periodItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof BOAutoComplete) {
                    BOAutoComplete itemSelected = (BOAutoComplete) item;
                    person_key = itemSelected.getPrimary_key();
                    fill_Person_trans(person_key, period_key);
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_Person_trans(long person_key, long period_key) {

        ArrayList<BOPersonTransaction> newlist = getNewlist(person_key);

        PersonTransGrid adapter = new PersonTransGrid(this.getContext(), R.layout.listview_groups, newlist);
        binding.gvGridView.setAdapter(adapter);

        totalAmount = 00.00;
        newlist.forEach(x -> totalAmount = totalAmount + (x.getActualAmount() == null ? 0 : x.getActualAmount())
                - (x.getNewAmount() == null ? 0 : x.getNewAmount()));
        binding.txtTotal.setText(String.valueOf(totalAmount));
       }

    Double totalAmount = 0.00;

    ArrayList<BOPersonTransaction> getNewlist(long person_key) {
        ArrayList<BOPersonTransaction> transactionsList=DBUtility.DTOGetAlls(DB1Tables.PERSON_TRANSACTION);
        ArrayList<BOPersonTransaction> personTrans = DBUtility.DTOGetData(DB1Tables.PERSON_TRANSACTION, person_key);
        return personTrans;
    }

    /*
        @RequiresApi(api = Build.VERSION_CODES.N)
        ArrayList<BOPersonTransaction> getNewlist() {
            ArrayList<BOGroupPersonLink> grpPersonLink = DBUtility.DTOGetAlls(DB1Tables.GROUP_PERSON_LINK);
            grpPersonLink.removeIf(x -> x.getPerson_Detail().getPrimary_key() != person_key);

            newlist = new ArrayList<>();
            ArrayList<BOPersonTransaction> exList = DBUtility.DTOGetAlls(DB1Tables.PERSON_TRANSACTION);

            for (int i = 0; i < grpPersonLink.size(); i++) {
                BOGroupPersonLink grpPerson = grpPersonLink.get(i);

                BOPersonTransaction trans = new BOPersonTransaction();
                trans.setTableName(DB1Tables.GROUP_PERSON_LINK);
                trans.setForien_key(grpPerson.getPrimary_key());
                trans.setPerson_detail(grpPerson.getPerson_Detail());
                trans.getPeriod_detail().setPrimary_key(period_key);
                trans.setRemarks(grpPerson.getGroup_Detail().getName());
                trans.setActualAmount(grpPerson.getGroup_Detail().getAmount());
                trans.setNewAmount(getTransData(trans, exList));

                newlist.add(trans);

                if (trans.getForien_key() != 0 && trans.getActualAmount() != trans.getNewAmount()) {
                    BOPersonTransaction trans1=trans;
                    trans1.setNewAmount(0.00);
                    newlist.add(trans1);
                }

            }
            return newlist;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        Double getTransData(BOPersonTransaction trans, ArrayList<BOPersonTransaction> exList) {

            pendingAmnt = 0.00;
            for (int i = 0; i < exList.size(); i++) {
                BOPersonTransaction gpLink = exList.get(i);
                if (trans.getTableName().equals(gpLink.getTableName()) && trans.getForien_key() == gpLink.getForien_key()) {
                    pendingAmnt = pendingAmnt + gpLink.getNewAmount();
                }
            }
            return pendingAmnt;
        }


        Double pendingAmnt = 0.00;
        ArrayList<BOPersonTransaction> newlist = new ArrayList<>();
    */
    View.OnClickListener clickMethod() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                for (int i = 0; i < binding.gvGridView.getCount(); i++) {
                    BOPersonTransaction transaction = (BOPersonTransaction) binding.gvGridView.getItemAtPosition(i);
                    if (transaction.getNewAmount() > 0)
                        DBUtility.DTOSaveUpdate(transaction, DB1Tables.PERSON_TRANSACTION);
                }
            }
        };
    }
}