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
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DB1Tables;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.Utility.UIUtility;
import com.villagebanking.databinding.TransHeaderViewBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class TransHeader extends Fragment {
    private TransHeaderViewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TransHeaderViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initialize();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initialize() {
        if (getArguments() != null) {
            String fromPage = getArguments().getString("PAGE");

            switch (fromPage) {
                case DB1Tables.PERIODS:
                    long period_Key = getArguments().getLong("ID");
                    fill_trans_Header(String.valueOf(period_Key), 0);
                    break;
                case tblPerson.Name:
                    long person_Key = getArguments().getLong("ID");
                    String periodKeys = "";
                    tblPeriod.getPeriodKeys(person_Key);
                    fill_trans_Header(periodKeys, person_Key);
                    break;
                default:
                    break;
            }
        }
        binding.btnSave.setOnClickListener(clickSave());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_trans_Header(String periodkeys, long personKey) {
        ArrayList<BOTransHeader> transHeaderFlist = DBUtility.FetchPeriodTrans(tblTransHeader.Name,
                periodkeys);


        ArrayList<BOTransHeader> transHeaders = new ArrayList<>();
        transHeaderFlist.forEach(x -> {
            x.setTransDetails(fill_trans_Detail(x.getPrimary_key()));
            if (personKey > 0) {
                if (x.link2Key == personKey)
                    transHeaders.add(x);
            } else
                transHeaders.add(x);

        });

        TransHeaderGrid adapter = new TransHeaderGrid(this.getContext(), R.layout.groups_gridview, transHeaders);
        binding.gvDataView.setAdapter(adapter);

        amountCalculation(transHeaders);
    }

    ArrayList<BOTransDetail> fill_trans_Detail(long headerKey) {
        ArrayList<BOTransDetail> transDetails = DBUtility.DTOGetTransData(headerKey);
        return transDetails;
    }


    View.OnClickListener clickSave() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                for (int i = 0; i < binding.gvDataView.getCount(); i++) {
                    BOTransHeader transHeader = (BOTransHeader) binding.gvDataView.getItemAtPosition(i);
                    BOTransDetail transDetail = new BOTransDetail();
                    transDetail.setHeaderKey(transHeader.getPrimary_key());
                    transDetail.setParentKey(transHeader.getPrimary_key());
                    transDetail.setAmount(transHeader.getPaidAmount());
                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                    transDetail.setTransDate(date);
                    transDetail.setRemarks("SKG");
                    if (transHeader.IsNew) {
                        DBUtility.DTOSaveUpdate(transDetail, tblTransDetail.Name);
                    }
                }
            }
        };
    }

    //region Calculation part

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void amountCalculation(ArrayList<BOTransHeader> transHeaders) {

        transHeaders.forEach(x -> setDetailList(x));

        Double totalAmount = transHeaders.stream().mapToDouble(x -> x.getTotalAmount()).sum();
        Double totalPaid = transHeaders.stream().mapToDouble(x -> x.getPaidAmount()).sum();
        Double totalBal = transHeaders.stream().mapToDouble(x -> x.getBalanceAmount()).sum();

        binding.txtTotal.setText(totalAmount.toString());
        binding.txtPaid.setText(totalPaid.toString());
        binding.txtBalance.setText(totalBal.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void setDetailList(BOTransHeader transHeader) {
        ArrayList<BOTransDetail> transDetails = transHeader.getTransDetails();

        Double paidAmount = transDetails.stream().mapToDouble(x -> x.getAmount()).sum();
        transHeader.setPaidAmount(paidAmount);
        Double pendingAmount = transHeader.getTotalAmount() - paidAmount;
        transHeader.setBalanceAmount(pendingAmount);
    }
    //endregion
}
