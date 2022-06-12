package com.tech.denso.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tech.denso.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NavigationRecyclerAdapter extends RecyclerView.Adapter<NavigationRecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> array;
    ArrayList<Integer> icons;
    private OnItemClickListener mListener;

    public NavigationRecyclerAdapter(ArrayList<String> array, ArrayList<Integer> icons) {
        this.array = array;
        this.icons = icons;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerrow, parent, false);
        return new MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.rowtext.setText(array.get(position));
        holder.usericon.setImageResource(icons.get(position));
    }

    @Override
    public long getItemId(int position) {
        setHasStableIds(true);
        return array.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public interface OnItemClickListener {
        void onClick(View v, int position, String value);
    }

    public void setOnItemCLickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout row;
        ImageView usericon;
        TextView rowtext;

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            row = itemView.findViewById(R.id.row);
            usericon = itemView.findViewById(R.id.usericon);
            rowtext = itemView.findViewById(R.id.rowtext);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(itemView, getAdapterPosition(), array.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
