package com.tech.denso.Fragments;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tech.denso.Models.InitialWarrantyFragment.InitialWarrantyModel;
import com.tech.denso.R;
import com.tech.denso.ViewModels.InitialViewModel;
import com.tech.denso.ViewModels.NextViewModel;

public class FinalWarrantyFragment extends Fragment {

    private int page;
    private String title;
    View view;
    EditText newpartedittext, newserialnumberedittext, newpartnameedittext, newpartinvoinceedittext;
    View newpartview, newserialnumberview, newpartnameview, newpartinvoiceview;
    InitialWarrantyModel item;

    public FinalWarrantyFragment() {
        // Required empty public constructor
    }

    public static FinalWarrantyFragment newInstance(int page, String title) {
        FinalWarrantyFragment fragmentFirst = new FinalWarrantyFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_final_warranty, container, false);
        newpartedittext = view.findViewById(R.id.newpartedittext);
        newserialnumberedittext = view.findViewById(R.id.newserialnumberedittext);
        newpartnameedittext = view.findViewById(R.id.newpartnameedittext);
        newpartinvoinceedittext = view.findViewById(R.id.newpartinvoinceedittext);

        newpartview = view.findViewById(R.id.newpartview);
        newserialnumberview = view.findViewById(R.id.newserialnumberview);
        newpartnameview = view.findViewById(R.id.newpartnameview);
        newpartinvoiceview = view.findViewById(R.id.newpartinvoiceview);

        setFocusChangeListener(newpartedittext, newpartview);
        setFocusChangeListener(newserialnumberedittext, newserialnumberview);
        setFocusChangeListener(newpartnameedittext, newpartnameview);
        setFocusChangeListener(newpartinvoinceedittext, newpartinvoiceview);
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    View v = getActivity().getCurrentFocus();
//                    if (v instanceof EditText) {
//                        Rect outRect = new Rect();
//                        v.getGlobalVisibleRect(outRect);
//                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
//                            v.clearFocus();
//                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                        }
//                    }
//                }
//                return true;
//            }
//        });
        NextViewModel model = new ViewModelProvider(requireActivity()).get(NextViewModel.class);
        model.getSelected().observe(getViewLifecycleOwner(), item -> {
            this.item = item;
        });
        return view;
    }

    public void setFocusChangeListener(EditText edittext, View view) {
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int colorFrom = Color.parseColor("#FAFAFA");
                    int colorTo = getResources().getColor(android.R.color.holo_red_light);
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(250);
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            view.getBackground().setColorFilter((int) animator.getAnimatedValue(),
                                    PorterDuff.Mode.SRC_ATOP);
                        }

                    });
                    colorAnimation.start();
                } else {
                    int colorTo = Color.parseColor("#FAFAFA");
                    int colorFrom = getResources().getColor(android.R.color.holo_red_light);
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(250);
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
//                            textView.setBackgroundColor((int) animator.getAnimatedValue());
                            view.getBackground().setColorFilter((int) animator.getAnimatedValue(),
                                    PorterDuff.Mode.SRC_ATOP);
                        }

                    });
                    colorAnimation.start();
                }
            }
        });
    }
}