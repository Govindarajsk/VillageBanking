package com.villagebanking.Controls;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.villagebanking.BOObjects.BOAutoComplete;
import com.villagebanking.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CCAutoComplete extends ArrayAdapter<BOAutoComplete> {
    private List<BOAutoComplete> fullList;
    private List<BOAutoComplete> filteredList;

    public CCAutoComplete(@NonNull Context context, @NonNull List<BOAutoComplete> inputList) {
        super(context, 0, inputList);

        fullList = new ArrayList<>(inputList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filterList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.app_dropview, parent, false
            );
        }

        TextView txtDropdownValue = convertView.findViewById(R.id.txtDropdownValue);

        BOAutoComplete place = getItem(position);
        if (place != null) {
            txtDropdownValue.setText(place.getDisplayValue());
        }

        return convertView;
    }

    private Filter filterList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (BOAutoComplete item: fullList) {
                    if (item.getDisplayValue().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((BOAutoComplete) resultValue).getDisplayValue();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SetDefault(TextView txtView, long... keys) {
        long key = keys.length > 0 ? keys[0] : 0;
       /*
        if(fullList!=null) {
            Stream<BOAutoComplete> fItem = fullList.stream().filter(x -> key == 0 || x.getPrimary_key() == key);

            if (fItem != null && fItem.findFirst().isPresent()) {
                Optional<BOAutoComplete> item = fItem.findFirst();
                txtView.setText(item.get() != null && item.isPresent() ? item.get().getDisplayValue() : "");
            }
            fItem.close();
        }
        */
    }
}
