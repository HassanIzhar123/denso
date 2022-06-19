package com.tech.denso.Fragments;

import static com.tech.denso.Helper.Helper.getListener;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.tech.denso.CustomViews.NonSwipeableViewPager;
import com.tech.denso.Interfaces.ListenFromActivity;
import com.tech.denso.Interfaces.Listener;
import com.tech.denso.Item;
import com.tech.denso.MultiSpinner;
import com.tech.denso.R;
import com.tech.denso.SelectableAdapter;

import java.util.ArrayList;
import java.util.List;

public class WarrantyFragment extends Fragment {
    private String title;
    private int page;
    View view;
    public static NonSwipeableViewPager pager;
    public static MyPagerAdapter adapter;

    public static WarrantyFragment newInstance(int page, String title) {
        WarrantyFragment fragmentFirst = new WarrantyFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.warranty_fragment, container, false);
        pager = view.findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(adapter);

        return view;
    }

    public  class MyPagerAdapter extends FragmentPagerAdapter {
        private Fragment mCurrentFragment;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return InitialWarrantyFragment.newInstance(0, "Page # 1");
                case 1:
                    return NextWarrantyFragment.newInstance(1, "Page # 2");
                case 2:
                    return FinalWarrantyFragment.newInstance(2, "Page # 3");
                default:
                    return FinalWarrantyFragment.newInstance(3, "Page # 3");
            }
        }

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}


