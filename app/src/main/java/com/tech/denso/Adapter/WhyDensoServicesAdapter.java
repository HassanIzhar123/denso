package com.tech.denso.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.denso.Models.PackagesModel.Datum;
import com.tech.denso.R;

import java.util.ArrayList;
import java.util.List;

public class WhyDensoServicesAdapter extends RecyclerView.Adapter<WhyDensoServicesAdapter.ViewHolder> {

    private ArrayList<String> servicename,serviceheader,servicedescription;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public WhyDensoServicesAdapter(Context context, ArrayList<String> servicename, ArrayList<String> serviceheader, ArrayList<String> servicedescription) {
        this.mInflater = LayoutInflater.from(context);
        this.servicename = servicename;
        this.serviceheader = serviceheader;
        this.servicedescription = servicedescription;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.why_services_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.headertext.setText(servicename.get(position));
        if (position == 0) {
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.headertext.setTextColor(Color.parseColor("#0F0A39"));
            holder.descriptiontext.setTextColor(Color.parseColor("#0F0A39"));
            holder.descriptionheadertext.setTextColor(Color.parseColor("#262A33"));
            holder.serviceimg.setImageResource(R.drawable.group_9614);
            holder.whitebtn.setVisibility(View.GONE);
            holder.redbtn.setVisibility(View.VISIBLE);
        } else if (position == 1) {
            holder.card.setCardBackgroundColor(Color.parseColor("#ED1A3B"));
            holder.headertext.setTextColor(Color.parseColor("#FFFFFF"));
            holder.descriptiontext.setTextColor(Color.parseColor("#FFFFFF"));
            holder.descriptionheadertext.setTextColor(Color.parseColor("#FFFFFF"));
            holder.serviceimg.setImageResource(R.drawable.service_icon);
            holder.whitebtn.setVisibility(View.VISIBLE);
            holder.redbtn.setVisibility(View.GONE);
        } else if (position == 2) {
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.headertext.setTextColor(Color.parseColor("#0F0A39"));
            holder.descriptiontext.setTextColor(Color.parseColor("#0F0A39"));
            holder.descriptionheadertext.setTextColor(Color.parseColor("#262A33"));
            holder.serviceimg.setImageResource(R.drawable.group_9608);
            holder.whitebtn.setVisibility(View.GONE);
            holder.redbtn.setVisibility(View.VISIBLE);
        } else if (position == 3) {
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.headertext.setTextColor(Color.parseColor("#0F0A39"));
            holder.descriptiontext.setTextColor(Color.parseColor("#0F0A39"));
            holder.descriptionheadertext.setTextColor(Color.parseColor("#262A33"));
            holder.serviceimg.setImageResource(R.drawable.electrical0services_icon);
            holder.whitebtn.setVisibility(View.GONE);
            holder.redbtn.setVisibility(View.VISIBLE);
        } else {
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.headertext.setTextColor(Color.parseColor("#0F0A39"));
            holder.descriptiontext.setTextColor(Color.parseColor("#0F0A39"));
            holder.descriptionheadertext.setTextColor(Color.parseColor("#262A33"));
            holder.whitebtn.setVisibility(View.GONE);
            holder.redbtn.setVisibility(View.VISIBLE);
        }
        holder.descriptionheadertext.setText(serviceheader.get(position));
        holder.descriptiontext.setText(servicedescription.get(position));
    }

    @Override
    public int getItemCount() {
        return servicename.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView headertext, descriptiontext, descriptionheadertext;
        CardView card;
        ImageView serviceimg;
        ImageButton whitebtn, redbtn;

        ViewHolder(View itemView) {
            super(itemView);
            headertext = itemView.findViewById(R.id.headertext);
            serviceimg = itemView.findViewById(R.id.serviceimg);
            descriptiontext = itemView.findViewById(R.id.descriptiontext);
            descriptionheadertext = itemView.findViewById(R.id.descriptionheadertext);
            whitebtn = itemView.findViewById(R.id.whitebtn);
            redbtn = itemView.findViewById(R.id.redbtn);
            card = itemView.findViewById(R.id.card);
            whitebtn.setOnClickListener(this);
            redbtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id) {
        return servicename.get(id);
    }

   public  void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
