package com.villagebanking.ui.Transaction;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOPeriod;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.DBTables.tblUtility;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.AppGridviewBinding;
import com.villagebanking.databinding.TransHeaderViewBinding;
import com.villagebanking.ui.UIUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.stream.LongStream;

public class TransHeader extends Fragment {

    //region Initialize
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
            long period_Key = 0;
            switch (fromPage) {
                case tblPeriod.Name:
                    period_Key = getArguments().getLong("ID");
                    fill_trans_Header(period_Key, 0);
                    break;
                case tblPerson.Name:
                    long person_Key = getArguments().getLong("ID");
                    period_Key = 0;
                    fill_trans_Header(period_Key, person_Key);
                    break;
                default:
                    break;
            }

            binding.btnSave.setOnClickListener(clickDetailSave(period_Key));
        }
    }

    //endregion

    //region Load / Display
    String keys = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_trans_Header(long periodkey, long personKey) {

        ArrayList<BOPeriod> boPeriods = tblPeriod.getNextNPeriods(periodkey, 0);

        LongStream keyStream = boPeriods.stream().mapToLong(x -> x.getPrimary_key());
        keyStream.forEach(x -> keys += (keys.length() > 0 ? "," : "") + UIUtility.ToString(x));

        ArrayList<BOTransDetail> transDetails1 = tblTransDetail.GetViewList("");
        ArrayList<BOTransDetail> transDetails = tblTransDetail.GetViewList(keys);
        ArrayList<BOTransDetail> filterLst = new ArrayList<>();
        transDetails.forEach(x ->
        {
            if (x.getPeriodKey() == periodkey || x.getBalanceAmount() > 0 || (x.getBalanceAmount() == 0 && x.gettD_Period_Key() == periodkey)) {
                filterLst.add(x);
            }
            if (x.getPeriodKey() != periodkey && x.gettD_Period_Key() != x.getPeriodKey()) {
                BOTransDetail newIns = getClone(x);
                filterLst.add(newIns);
            }
        });
        TransHeaderGrid adapter = new TransHeaderGrid(this.getContext(), R.layout.trans_header_gridview,
                filterLst);
        binding.gvDataView.setAdapter(adapter);
        accountSummary(filterLst);
    }

    BOTransDetail getClone(BOTransDetail data) {
        BOTransDetail transHeader = new BOTransDetail();
        transHeader.setHeaderKey(data.getHeaderKey());
        transHeader.setParentKey(data.getPrimary_key());
        transHeader.setPrimary_key(data.getPrimary_key());
        transHeader.setTotalAmount(data.getTotalAmount() / 10);
        transHeader.setBalanceAmount(data.getTotalAmount() / 10);
        transHeader.setPeriodKey(data.getPeriodKey());
        transHeader.setTransDate(data.getTransDate());
        transHeader.setLinkDetail1(data.getLinkDetail1());
        transHeader.setLinkDetail2(data.getLinkDetail2());
        return transHeader;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void accountSummary(ArrayList<BOTransDetail> transHeaders) {
        Double totalAmount = transHeaders.stream().mapToDouble(x -> x.getTotalAmount()).sum();
        Double totalPaid = transHeaders.stream().mapToDouble(x -> x.getPaidAmount()).sum();
        Double totalBal = transHeaders.stream().mapToDouble(x -> x.getBalanceAmount()).sum();

        binding.txtTotal.setText(totalAmount.toString());
        binding.txtPaid.setText(totalPaid.toString());
        binding.txtBalance.setText(totalBal.toString());
    }
    //endregion

    //region Events
    View.OnClickListener clickDetailSave(long period_Key) {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                BOPeriod period = tblUtility.GetTData(tblPeriod.GetList(period_Key));
                if (period == null) return;
                for (int i = 0; i < binding.gvDataView.getCount(); i++) {
                    BOTransDetail x = (BOTransDetail) binding.gvDataView.getItemAtPosition(i);
                    BOTransDetail transDetail = new BOTransDetail();
                    transDetail.setHeaderKey(x.getPrimary_key());
                    transDetail.setParentKey(x.getParentKey());
                    transDetail.setAmount(x.getPaidAmount());
                    transDetail.setPeriodKey(period.getPrimary_key());
                    transDetail.setTransDate(period.getActualDate());
                    transDetail.setRemarks("Auto");
                    if (x.IsNew) {
                        tblTransDetail.Save("I", transDetail);
                    }
                }
                getActivity().onBackPressed();
            }
        };
    }
    //endregion
}
