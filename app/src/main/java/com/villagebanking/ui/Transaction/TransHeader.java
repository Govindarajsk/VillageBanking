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

import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.UIUtility;
import com.villagebanking.databinding.PersonsTransLinkviewBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class TransHeader extends Fragment {
    private PersonsTransLinkviewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PersonsTransLinkviewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_trans_Header(long periodkey) {
        ArrayList<BOTransHeader> transHeaders = DBUtility.FetchPeriodTrans(tblTransHeader.Name, String.valueOf(periodkey));

        transHeaders.forEach(x -> x.setTransDetails(fill_trans_Detail(x.getPrimary_key())));

        TransHeaderGrid adapter = new TransHeaderGrid(this.getContext(), R.layout.groups_gridview, transHeaders);
        binding.gvDataView.setAdapter(adapter);

        amountCalculation(transHeaders);
    }

    ArrayList<BOTransDetail> fill_trans_Detail(long headerKey) {
        ArrayList<BOTransDetail> transDetails = DBUtility.DTOGetTransData(headerKey);
        return transDetails;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initialize() {
        if (getArguments() != null) {
            String fromPage = getArguments().getString("PAGE");

            switch (fromPage) {
                case DB1Tables.PERIODS:
                    long period_key = getArguments().getLong("ID");
                    fill_trans_Header(period_key);
                    break;
                case DB1Tables.GROUP_PERSON_LINK:
                    //groupLink_Key = getArguments().getLong("ID");
                    break;
                case DB1Tables.PERSONS:
                    //person_key = getArguments().getLong("ID");
                    break;
                default:
                    break;
            }
        }
        binding.btnSave.setOnClickListener(clickSave());
    }

    View.OnClickListener clickSave() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                for (int i = 0; i < binding.gvDataView.getCount(); i++) {
                    BOTransHeader transHeader = (BOTransHeader) binding.gvDataView.getItemAtPosition(i);
                    transHeader.getTransDetails().forEach(x -> {
                        x.setHeaderKey(transHeader.getPrimary_key());
                        x.setParentKey(transHeader.getPrimary_key());
                        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        x.setTransDate(date);
                        x.setRemarks("Ok Google");
                        DBUtility.DTOSaveUpdate(x, tblTransDetail.Name);
                    });
                }
            }
        };
    }

    //region Calculation part
    Double totalAmount = 0.0;
    Double totalPaid = 0.0;
    Double totalBal = 0.0;
    Double balEach = 0.0;
    long tableLinkKey = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void amountCalculation(ArrayList<BOTransHeader> transHeaders) {

        transHeaders.forEach(x -> setDetailList(x));

        totalAmount = totalPaid = totalBal = 0.0;
        totalAmount = transHeaders.stream().mapToDouble(x -> x.getTotalAmount()).sum();
        totalPaid = transHeaders.stream().mapToDouble(x -> x.getPaidAmount()).sum();
        totalBal = transHeaders.stream().mapToDouble(x -> x.getBalanceAmount()).sum();

        binding.txtTotal.setText(totalAmount.toString());
        binding.txtPaid.setText(totalPaid.toString());
        binding.txtBalance.setText(totalBal.toString());
    }

    Double lastamount = 0.0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setDetailList(BOTransHeader transHeader) {
        balEach = transHeader.getTotalAmount();
        ArrayList<BOTransDetail> transDetails = transHeader.getTransDetails();
        lastamount = 0.0;
        transDetails.forEach(x -> {
            balEach = balEach - x.getAmount();
            lastamount = x.getAmount();
        });
        transHeader.setBalanceAmount(balEach);
        if (balEach != 0 && (transDetails.size() == 0 || lastamount > 0)) {
            BOTransDetail newData = new BOTransDetail();
            newData.setHeaderKey(transHeader.getPrimary_key());
            newData.setParentKey(0);
            newData.setTransDate(UIUtility.getCurrentDate());
            newData.setAmount(0.00);
            newData.setRemarks(transHeader.getRemarks());
            newData.setBalanceAmount(balEach);
            transDetails.add(newData);
        }
    }
    //endregion
}
