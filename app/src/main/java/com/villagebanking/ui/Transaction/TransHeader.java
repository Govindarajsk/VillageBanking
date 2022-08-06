package com.villagebanking.ui.Transaction;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.DBTables.tblPeriod;
import com.villagebanking.DBTables.tblPerson;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.AppGridviewBinding;
import com.villagebanking.databinding.TransHeaderViewBinding;
import com.villagebanking.ui.UIUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

            switch (fromPage) {
                case tblPeriod.Name:
                    long period_Key = getArguments().getLong("ID");
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
        }
        binding.btnSave.setOnClickListener(clickDetailSave());
    }

    //endregion

    //region Load / Display
    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_trans_Header(long periodkey, long personKey) {
        ArrayList<BOTransHeader> transHeaders =
                tblTransHeader.GetViewList(tblTransHeader.Name, UIUtility.ToString(periodkey), UIUtility.ToString(personKey));

        TransHeaderGrid adapter = new TransHeaderGrid(this.getContext(), R.layout.trans_header_gridview,
                transHeaders);
        binding.gvDataView.setAdapter(adapter);
        accountSummary(transHeaders);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void accountSummary(ArrayList<BOTransHeader> transHeaders) {
        Double totalAmount = transHeaders.stream().mapToDouble(x -> x.getTotalAmount()).sum();
        Double totalPaid = transHeaders.stream().mapToDouble(x -> x.getPaidAmount()).sum();
        Double totalBal = transHeaders.stream().mapToDouble(x -> x.getBalanceAmount()).sum();

        binding.txtTotal.setText(totalAmount.toString());
        binding.txtPaid.setText(totalPaid.toString());
        binding.txtBalance.setText(totalBal.toString());
    }
    //endregion

    //region Events
    View.OnClickListener clickDetailSave() {
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
                    transDetail.setTransDate(UIUtility.getCurrentDate());
                    transDetail.setRemarks("SKG");
                    if (transHeader.IsNew) {
                        tblTransDetail.Save("I", transDetail);
                    }
                   //tblTransHeader.UpdateAmount(transHeader);
                }
            }
        };
    }
    //endregion
}
