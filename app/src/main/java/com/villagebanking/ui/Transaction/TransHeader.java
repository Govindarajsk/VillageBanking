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
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.UIUtility;
import com.villagebanking.databinding.PersonsTransLinkviewBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransHeader extends Fragment {
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
                    BOTransDetail transHeader = (BOTransDetail) binding.gvDataView.getItemAtPosition(i);
                    //DBUtility.DTOSaveUpdate(transHeader, DB1Tables.TRANSACTION_HEADER);

                    if (transHeader.getPaidAmount() != 0 || transHeader.getParentKey() > 0) {
                        BOTransDetail transDetail = new BOTransDetail();
                        transDetail.setPrimary_key(transHeader.getPrimary_key());
                        transDetail.setParentKey(transHeader.getParentKey());
                        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        transDetail.setTransDate(date);
                        transDetail.setPaidAmount(transHeader.getPaidAmount());
                        transDetail.setRemarks("Ok Google");
                        DBUtility.DTOSaveUpdate(transDetail, tblTransDetail.Name);
                    }
                }
                fill_Person_trans(person_key, period_key, groupLink_Key);
            }
        };
    }

    String keys = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_Person_trans(long personkey, long periodkey, long groupLinkKey) {

        ArrayList<BOTransDetail> trans = DBUtility.DTOGetTransData("", String.valueOf(periodkey), "", "");

        calculateAmount(trans);

        Collections.sort(trans, (x, y) -> String.valueOf(x.getTableLinkKey()).compareToIgnoreCase(
                String.valueOf(y.getTableLinkKey())));

        TransHeaderGrid adapter = new TransHeaderGrid(this.getContext(), R.layout.groups_gridview, trans);
        binding.gvDataView.setAdapter(adapter);
        binding.gvDataView.addOnLayoutChangeListener(layoutChanged(trans));
    }

    //region Calculation part
    Double totalAmount = 0.0;
    Double totalPaid = 0.0;
    Double totalBal = 0.0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    void calculateAmount(ArrayList<BOTransDetail> fullList) {
        totalAmount = totalPaid = totalBal = 0.0;
        Map<Long, ArrayList<BOTransDetail>> uniqueList = UIUtility.GetTransAmount(fullList);
        uniqueList.forEach((x, y) -> applyVal(x, y, fullList));

        totalBal = totalAmount - totalPaid;
        binding.txtTotal.setText(totalAmount.toString());
        binding.txtPaid.setText(totalPaid.toString());
        binding.txtBalance.setText(totalBal.toString());
    }

    Double balEach = 0.0;
    long tableLinkKey = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    void applyVal(Long key, ArrayList<BOTransDetail> value, ArrayList<BOTransDetail> fullList) {
        balEach = 0.0;tableLinkKey = 0;

        Stream<BOTransDetail> groupListAmount =
                fullList.stream().filter(x -> x.getTableLinkKey() == key);

        List<BOTransDetail> okGk = groupListAmount.collect(Collectors.toList());

        BOTransDetail transData = null;
        Integer fSize = okGk.size();

        if (fSize > 0) {
            transData = okGk.get(fSize - 1);
            balEach = okGk.get(0).getBalanceAmount();
            okGk.forEach(x -> fillBalAmount(x));

            if (balEach != 0 && transData.getPrimary_key() > 0) {
                BOTransDetail newData = new BOTransDetail();
                newData.setParentKey(transData.getPrimary_key());
                newData.setTableName(transData.getTableName());
                newData.setTableLinkKey(transData.getTableLinkKey());
                newData.setChildKey(transData.getChildKey());
                //newData.setPeriod_detail(transData.getPeriod_detail());
                newData.setRemarks(transData.getRemarks());
                newData.setBalanceAmount(balEach);
                newData.setPaidAmount(0.00);
                fullList.add(newData);
            }
        }
    }

    void fillBalAmount(BOTransDetail x) {
        if (tableLinkKey != x.getTableLinkKey()) {
            tableLinkKey = x.getTableLinkKey();
            totalAmount = totalAmount + x.getTotalAmount();
        } else {
            x.setTableLinkKey(tableLinkKey);
        }

        totalPaid = totalPaid + x.getPaidAmount();
        x.setBalanceAmount(balEach);
        balEach = balEach - x.getPaidAmount();
    }

    AdapterView.OnLayoutChangeListener layoutChanged(ArrayList<BOTransDetail> newList) {
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
