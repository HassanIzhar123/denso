package com.tech.denso.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tech.denso.R;

public class RemainingFeatureFragment extends Fragment {
    private String title;
    private int page;

    public static RemainingFeatureFragment newInstance(int page, String title) {
        RemainingFeatureFragment fragmentFirst = new RemainingFeatureFragment();
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
        View view = inflater.inflate(R.layout.remaining_feature_fragment, container, false);

        return view;
    }
}
