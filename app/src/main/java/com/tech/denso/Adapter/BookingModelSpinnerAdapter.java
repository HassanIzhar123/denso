package com.tech.denso.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tech.denso.Models.Locations.Datum;
import com.tech.denso.R;

import java.util.List;

public class BookingModelSpinnerAdapter extends BaseAdapter {
    Context context;
    List<com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum> datums;
    LayoutInflater inflter;

    public BookingModelSpinnerAdapter(Context context, List<com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum> datums) {
        this.context = context;
        this.datums = datums;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return datums.size();
    }

    @Override
    public com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum getItem(int i) {
        return datums.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(datums.get(i).getTransmissionName());
        return view;
    }
}