package com.tech.denso.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.tech.denso.Models.Services.Datum;
import com.tech.denso.R;

import java.util.Arrays;
import java.util.List;

public class ServicingViewAdapter extends RecyclerView.Adapter<ServicingViewAdapter.ViewHolder> {

    private List<Datum> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public ServicingViewAdapter(Context context, List<Datum> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.services_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mData.get(position).isAnotherRel()) {
            holder.bannerrel.setVisibility(View.VISIBLE);
            holder.mainrel.setVisibility(View.GONE);
        } else {
            holder.bannerrel.setVisibility(View.GONE);
            holder.mainrel.setVisibility(View.VISIBLE);
            String servicename = mData.get(position).getServiceName();
            holder.headertext.setText(servicename);
            if (position == 0) {
                holder.btnrel.setBackgroundResource(R.drawable.round_button_background);
                holder.bookbtn.setTextColor(Color.parseColor("#ED1A3B"));
                holder.serviceimg.setImageResource(R.drawable.group_9614);
            } else if (position == 1) {
                holder.btnrel.setBackgroundResource(R.drawable.round_button_background);
                holder.bookbtn.setTextColor(Color.parseColor("#ED1A3B"));
                holder.serviceimg.setImageResource(R.drawable.group_9605);
            } else if (position == 2) {
                holder.btnrel.setBackgroundResource(R.drawable.red_round_button_background);
                holder.bookbtn.setTextColor(Color.parseColor("#FFFFFF"));
                holder.serviceimg.setImageResource(R.drawable.group_9608);
            } else if (position == 3) {
                holder.btnrel.setBackgroundResource(R.drawable.round_button_background);
                holder.bookbtn.setTextColor(Color.parseColor("#ED1A3B"));
                holder.serviceimg.setImageResource(R.drawable.electrical0services_icon);
            } else {
                holder.btnrel.setBackgroundResource(R.drawable.round_button_background);
                holder.bookbtn.setTextColor(Color.parseColor("#ED1A3B"));
            }
//        String htmlText = mData.get(position).getServiceCategory()
//                .replace("//","<font color='#c5c5c5'>"+"//" +"</font>");
//        holder.descriptiontext.setText(Html.fromHtml(htmlText));
            holder.descriptiontext.setText(mData.get(position).getServiceCategory());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView headertext, descriptiontext;
        Button bookbtn;
        MaterialButton booknowbtn;
        ImageView serviceimg;
        RelativeLayout btnrel, mainrel, bannerrel;

        ViewHolder(View itemView) {
            super(itemView);
            headertext = itemView.findViewById(R.id.headertext);
            serviceimg = itemView.findViewById(R.id.serviceimg);
            descriptiontext = itemView.findViewById(R.id.descriptiontext);
            bookbtn = itemView.findViewById(R.id.bookbtn);
            booknowbtn = itemView.findViewById(R.id.booknowbtn);
            btnrel = itemView.findViewById(R.id.btnrel);
            mainrel = itemView.findViewById(R.id.mainrel);
            bannerrel = itemView.findViewById(R.id.bannerrel);
            bookbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null)
                        mClickListener.onItemClick(view, getAdapterPosition());
                }
            });
            booknowbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null)
                        mClickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    Datum getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
