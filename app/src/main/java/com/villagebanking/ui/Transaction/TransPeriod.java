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

import com.villagebanking.BOObjects.BOPeriod;
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

public class TransPeriod extends Fragment {
    private PersonsTransLinkviewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PersonsTransLinkviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;
    }

    BOPersonTrans selectedData = new BOPersonTrans();
    long person_key = 0;
    long period_key = 0;
    long groupLink_Key = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initialize() {
        if (getArguments() != null) {
            String fromPage = getArguments().getString("PAGE");

            switch (fromPage) {
                case DB1Tables.PERIODS:
                    period_key = getArguments().getLong("ID");
                    if (period_key > 0) {
                        ArrayList<BOPeriod> periods = DBUtility.DTOGetData(DB1Tables.PERIODS, period_key);
                        if (periods.size() > 0) {
                            selectedData.setPeriod_detail(periods.get(0));
                        }
                    }
                    break;
                case DB1Tables.GROUP_PERSON_LINK:
                    groupLink_Key = getArguments().getLong("ID");
                    break;
                case DB1Tables.PERSONS:
                    person_key = getArguments().getLong("ID");
                    break;
                default:
                    break;
            }
            fill_Person_trans(person_key, period_key, groupLink_Key);
        }
        binding.btnSave.setOnClickListener(clickSave());
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
                fill_Person_trans(person_key, period_key, groupLink_Key);
            }
        };
    }

    String keys = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_Person_trans(long personkey, long periodkey, long groupLinkKey) {

        ArrayList<BOPeriod> boPeriods = DBUtility.DBGetDataFilter(DB1Tables.PERIODS, "PERIOD_TYPE", UIUtility.LongToString(periodkey));
        keys = "";

        boPeriods.stream().forEach(x -> keys = (keys.length() > 0 ? (keys + ",") : "") + x.getPrimary_key());
    /*
        ArrayList<BOPersonTrans> trans = DBUtility.DTOGetTransData(personkey, keys, groupLinkKey);

        calculateAmount(trans);

        Collections.sort(trans, (x, y) -> String.valueOf(x.getTable_link_key()).compareToIgnoreCase(
                String.valueOf(y.getTable_link_key())));


        TransHeaderGrid adapter = new TransHeaderGrid(this.getContext(), R.layout.groups_gridview, trans);
        binding.gvDataView.setAdapter(adapter);
        binding.gvDataView.addOnLayoutChangeListener(layoutChanged(trans));

 */

    }

    //region Calculation part
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
            balEach = okGk.get(0).getActualAmount();
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

    AdapterView.OnLayoutChangeListener layoutChanged(ArrayList<BOPersonTrans> newList) {
        return new AdapterView.OnLayoutChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                calculateAmount(newList);
            }
        };
    }
    //endregion
}