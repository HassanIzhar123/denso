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
        if (mData.get(position) != null) {
            if (mData.get(position).getId() != null && !mData.get(position).getId().equals("null")) {
                holder.ordervaluetext.setText(mData.get(position).getId());
            } else {
                holder.ordervaluetext.setText("-");
            }
            if (mData.get(position).getBookingDate() != null && !mData.get(position).getBookingDate().equals("null")) {
                holder.datevaluetext.setText(mData.get(position).getBookingDate().split("T")[0]);
            } else {
                holder.datevaluetext.setText("-");
            }
            if (mData.get(position).getBookingTime() != null && !mData.get(position).getBookingTime().equals("null")) {
                holder.timevaluetext.setText(mData.get(position).getBookingTime().split("Z")[0]);
            } else {
                holder.timevaluetext.setText("-");
            }
            if (mData.get(position).getServiceDetails().equals("")) {
                holder.bookservicevaluetext.setText("-");
            } else {
                if (mData.get(position).getServiceDetails() != null && !mData.get(position).getServiceDetails().equals("null")) {
                    holder.bookservicevaluetext.setText(mData.get(position).getServiceDetails());
                } else {
                    holder.bookservicevaluetext.setText("-");
                }
            }
            if (mData.get(position).getVehicleType() != null && !mData.get(position).getVehicleType().equals("null")) {
                holder.makevaluetext.setText(mData.get(position).getVehicleType());
            } else {
                holder.makevaluetext.setText("-");
            }
            if (mData.get(position).getModel() != null && !mData.get(position).getModel().equals("null")) {
                holder.yearvaluetext.setText(mData.get(position).getModel());
            } else {
                holder.yearvaluetext.setText("-");
            }
            if (mData.get(position).getTransmission() != null && !mData.get(position).getTransmission().equals("null")) {
                holder.transmissionvaluetext.setText(mData.get(position).getTransmission());
            } else {
                holder.transmissionvaluetext.setText("-");
            }
            if (mData.get(position).getBranchName() != null && !mData.get(position).getBranchName().equals("null")) {
                holder.branchvaluetext.setText(mData.get(position).getBranchName());
            } else {
                holder.branchvaluetext.setText("-");
            }
        }
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
