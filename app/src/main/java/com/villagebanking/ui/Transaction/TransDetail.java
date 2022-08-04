package com.villagebanking.ui.Transaction;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.villagebanking.BOObjects.BOGroup;
import com.villagebanking.BOObjects.BOKeyValue;
import com.villagebanking.BOObjects.BOTransDetail;
import com.villagebanking.BOObjects.BOTransHeader;
import com.villagebanking.Controls.AutoBox;
import com.villagebanking.DBTables.tblGroup;
import com.villagebanking.DBTables.tblTransDetail;
import com.villagebanking.DBTables.tblTransHeader;
import com.villagebanking.Database.DBUtility;
import com.villagebanking.R;
import com.villagebanking.databinding.AppGridviewBinding;
import com.villagebanking.databinding.TransDetailsGridviewBinding;
import com.villagebanking.databinding.TransDetailsViewBinding;
import com.villagebanking.ui.UIUtility;

import java.util.ArrayList;

public class TransDetail extends Fragment {
    private TransDetailsViewBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TransDetailsViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fill_group(0);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void trans_Detail(long groupKey) {
        ArrayList<BOTransDetail> transDetails = tblTransDetail.GetDetailViewList(0, groupKey);

        TransDetailGrid adapter = new TransDetailGrid(this.getContext(), R.layout.trans_details_gridview, transDetails);
        binding.gvDataView.setAdapter(adapter);

        double total = transDetails.stream().mapToDouble(x -> x.getAmount()).sum();
        binding.lblSummary.setText(String.valueOf(total));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void fill_group(long groupKey) {
        ArrayList<BOGroup> groups = DBUtility.DTOGetData(tblGroup.Name, groupKey);

        ArrayList<BOKeyValue> keyValues = new ArrayList<>();
        groups.stream().forEach(x -> keyValues.add(new BOKeyValue(x.getPrimary_key(), x.getName(), x)));
        AutoBox autoBox = UIUtility.getAutoBox(this.getContext(), keyValues, binding.selectGroup,
                itemSelected(null));

        //binding.selectGroup.setOnItemClickListener(selectedEvent(binding.selectGroup));
        long grpKey = autoBox.getSelectedItem().getPrimary_key();
        trans_Detail(grpKey);

    }

    AdapterView.OnItemClickListener itemSelected(AdapterView.OnItemClickListener listener) {
        return new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                BOKeyValue group = ((AutoBox) binding.selectGroup.getAdapter()).getSelectedItem();
                trans_Detail(group.getPrimary_key());
            }
        };
    }
}
