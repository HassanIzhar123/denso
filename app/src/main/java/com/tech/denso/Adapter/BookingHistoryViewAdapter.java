package com.tech.denso.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tech.denso.R;

import java.util.List;

public class BookingHistoryViewAdapter extends RecyclerView.Adapter<BookingHistoryViewAdapter.ViewHolder> {

    private List<com.tech.denso.Models.BookingsModel.Datum> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public BookingHistoryViewAdapter(Context context, List<com.tech.denso.Models.BookingsModel.Datum> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.booking_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ordervaluetext.setText(mData.get(position).getId());
        holder.datevaluetext.setText(mData.get(position).getBookingDate().split("T")[0]);
        holder.timevaluetext.setText(mData.get(position).getBookingTime().split("Z")[0]);
        if (mData.get(position).getServiceDetails().equals("")) {
            holder.bookservicevaluetext.setText("-");
        } else {
            holder.bookservicevaluetext.setText(mData.get(position).getServiceDetails());
        }
        holder.makevaluetext.setText(mData.get(position).getVehicleType());
        holder.yearvaluetext.setText(mData.get(position).getModel());
        holder.transmissionvaluetext.setText(mData.get(position).getTransmission());
        holder.branchvaluetext.setText(mData.get(position).getBranchName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ordervaluetext, datevaluetext, timevaluetext, bookservicevaluetext, makevaluetext, yearvaluetext, transmissionvaluetext, branchvaluetext;

        ViewHolder(View itemView) {
            super(itemView);
            ordervaluetext = itemView.findViewById(R.id.ordervaluetext);
            datevaluetext = itemView.findViewById(R.id.datevaluetext);
            timevaluetext = itemView.findViewById(R.id.timevaluetext);
            bookservicevaluetext = itemView.findViewById(R.id.bookservicevaluetext);
            makevaluetext = itemView.findViewById(R.id.makevaluetext);
            yearvaluetext = itemView.findViewById(R.id.yearvaluetext);
            transmissionvaluetext = itemView.findViewById(R.id.tranmissionvaluetext);
            branchvaluetext = itemView.findViewById(R.id.branchvaluetext);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    com.tech.denso.Models.BookingsModel.Datum getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
