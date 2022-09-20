package com.tech.denso.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.denso.Models.InitialWarrantyFragment.Item;
import com.tech.denso.Models.BookingsModel.BookingServicesSpinner.Datum;
import com.tech.denso.R;

import java.util.ArrayList;
import java.util.List;

public class SelectableAdapter extends RecyclerView.Adapter<SelectableAdapter.ViewHolder> implements DialogInterface.OnDismissListener {

    private List<Datum> mValues;
    private List<Datum> filteredvalues;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    ArrayList<Datum> temparr = new ArrayList<>();
    Context context;

    public SelectableAdapter(Context context, List<Datum> items,
                             Dialog dialog, ItemClickListener listener) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        mValues = new ArrayList<>();
        filteredvalues = new ArrayList<>();
        this.mValues = items;
        temparr.addAll(mValues);
        mClickListener = listener;
        dialog.setOnDismissListener(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.checked_item, parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = mValues.get(position).getServiceCategory();
        holder.textView.setText(name);
        setChecked(holder.rel, holder.checkbox, holder.textView, mValues.get(position), mValues.get(position).isSelected());
    }

    public List<Datum> getSelectedItems() {
        List<Datum> selectedItems = new ArrayList<>();
        for (Datum item : temparr) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    public void checkLogs(String logvalue, Item item) {
        Log.e(logvalue, "" + item.getName() + " " + item.isSelected());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setBackToOriginalArray(List<Datum> templist) {
        mValues.clear();
        mValues.addAll(templist);
//        List<Item> selectedarr = getSelectedItems();
//        for (int i = 0; i < selectedarr.size(); i++) {
//            Log.e("firstonvaluecheck", "" + selectedarr.get(i).getName() + " " + selectedarr.get(i).isSelected());
//        }
//        for (int i = 0; i < selectedarr.size(); i++) {
//            selectedarr.remove(i);
//            selectedarr.add(i, new Item(list.get(i).getName(), false));
//        }
        notifyDataSetChanged();
    }

    public void setSelected(int position) {

    }

    public void filter(String text) {
        filteredvalues.clear();
        for (int i = 0; i < mValues.size(); i++) {
            if (mValues.get(i).getServiceCategory().toLowerCase().contains(text.toLowerCase())
                    && mValues.get(i).getServiceCategory().trim().toLowerCase().startsWith(text.toLowerCase().trim())
            ) {
                filteredvalues.add(mValues.get(i));
            }
        }
        mValues.clear();
        mValues.addAll(filteredvalues);
        notifyDataSetChanged();
    }

    public ArrayList<Datum> getTemparr() {
        return temparr;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mClickListener.onDismissDialog(getSelectedItems());
    }

    public List<Datum> getmValues() {
        return mValues;
    }

    public void setAllChecked(boolean value) {
        for (Datum item : mValues) {
            item.setSelected(value);
        }
        notifyDataSetChanged();
    }

    public void setChecked(RelativeLayout rel, CheckBox checkbox, TextView textView, Datum mItem, boolean value) {
        if (value) {
            rel.setBackgroundColor(Color.parseColor("#E91A3A"));
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            setBackgroundDrawable(rel, R.drawable.selected_services_row);
        } else {
            rel.setBackground(null);
            textView.setTextColor(ContextCompat.getColor(context, R.color.services_row_text_color));
            setBackgroundDrawable(rel, -1);
        }
        mItem.setSelected(value);
        checkbox.setChecked(value);
    }

    private void setBackgroundDrawable(RelativeLayout layout, int drawable) {
        if (drawable != -1) {
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(ContextCompat.getDrawable(context, drawable));
            } else {
                layout.setBackground(ContextCompat.getDrawable(context, drawable));
            }
        } else {
            layout.setBackground(null);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public static final int MULTI_SELECTION = 2;
        public static final int SINGLE_SELECTION = 1;
        TextView textView;
        Item mItem;
        ItemClickListener itemSelectedListener;
        CheckBox checkbox;
        RelativeLayout rel;

        ViewHolder(View view, ItemClickListener listener) {
            super(view);
            itemSelectedListener = listener;
            textView = (TextView) view.findViewById(R.id.checked_text_item);
            checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            rel = (RelativeLayout) view.findViewById(R.id.rel);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkbox.isChecked()) {
                        setChecked(rel, checkbox, textView, mValues.get(getAdapterPosition()), true);
                    } else {
                        setChecked(rel, checkbox, textView, mValues.get(getAdapterPosition()), false);
                    }
                }
            });
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        setChecked(rel, checkbox, textView, mValues.get(getAdapterPosition()), false);
                    } else {
                        setChecked(rel, checkbox, textView, mValues.get(getAdapterPosition()), true);
                    }
                    itemSelectedListener.onItemSelected(mValues.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemSelected(Datum item, int position);

        void onDismissDialog(List<Datum> items);
    }
}