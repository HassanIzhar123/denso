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

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.tech.denso.Activities.DashboardActivity;
import com.tech.denso.Models.InitialWarrantyFragment.InitialWarrantyModel;
import com.tech.denso.R;
import com.tech.denso.ViewModels.InitialViewModel;
import com.tech.denso.ViewModels.NextViewModel;


public class NextWarrantyFragment extends Fragment {

    private int page;
    private String title;
    View view;
    EditText salesorderedittext, manufactureredittext, unitmodeledittext, unitserialedittext, message_edittext;
    MaterialButton nextbtn;
    View unitserialview, unitmodelview, manufacturerview, salesorderview;
    InitialWarrantyModel item;
    TextView originalunitselecttextview, failedpartselecttextview,failedparterror,originalunitdateerror;

    public NextWarrantyFragment() {
        // Required empty public constructor
    }

    public static NextWarrantyFragment newInstance(int page, String title) {
        NextWarrantyFragment fragmentFirst = new NextWarrantyFragment();
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
        view = inflater.inflate(R.layout.fragment_next_warranty, container, false);
        message_edittext = view.findViewById(R.id.message_edittext);
        originalunitselecttextview = view.findViewById(R.id.originalunitselecttextview);
        failedpartselecttextview = view.findViewById(R.id.failedpartselecttextview);
        salesorderedittext = view.findViewById(R.id.salesorderedittext);
        manufactureredittext = view.findViewById(R.id.manufactureredittext);
        unitmodeledittext = view.findViewById(R.id.unitmodeledittext);
        unitserialedittext = view.findViewById(R.id.unitserialedittext);

        unitserialview = view.findViewById(R.id.unitserialview);
        unitmodelview = view.findViewById(R.id.unitmodelview);
        manufacturerview = view.findViewById(R.id.manufacturerview);
        salesorderview = view.findViewById(R.id.salesorderview);
        nextbtn = view.findViewById(R.id.nextbtn);
        failedparterror = view.findViewById(R.id.failedparterror);
        originalunitdateerror = view.findViewById(R.id.originalunitdateerror);
        setFocusChangeListener(unitserialedittext, unitserialview);
        setFocusChangeListener(unitmodeledittext, unitmodelview);
        setFocusChangeListener(manufactureredittext, manufacturerview);
        setFocusChangeListener(salesorderedittext, salesorderview);
        InitialViewModel model = new ViewModelProvider(requireActivity()).get(InitialViewModel.class);
        model.getSelected().observe(getViewLifecycleOwner(), item -> {
            Log.e("minitialvaluecheck", "" + item.getCity());
            this.item = item;
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckCondition()) {
                    String salesorder = salesorderedittext.getText().toString();
                    String manufacturer = manufactureredittext.getText().toString();
                    String unitmodel = unitmodeledittext.getText().toString();
                    String unitserial = unitserialedittext.getText().toString();
                    String message = message_edittext.getText().toString();
                    String originalunitdate = originalunitselecttextview.getText().toString();
                    String failedpartdate = failedpartselecttextview.getText().toString();
                    item.setSaleOrder(salesorder);
                    item.setManufacturer(manufacturer);
                    item.setUnitModelNumber(unitmodel);
                    item.setUnitSerialNumber(unitserial);
                    item.setMessage(message);
                    item.setOriginalUnitDate(originalunitdate);
                    item.setFailedUnitDate(failedpartdate);
                    NextViewModel model = new ViewModelProvider(requireActivity()).get(NextViewModel.class);
                    model.select(item);
                    DashboardActivity.backbtn.setVisibility(View.VISIBLE);
                    WarrantyFragment.pager.setCurrentItem(2);
                }
            }
        });
        return view;
    }

    private boolean CheckCondition() {
        String salesorder = salesorderedittext.getText().toString();
        String manufacturer = manufactureredittext.getText().toString();
        String unitmode = unitmodeledittext.getText().toString();
        String unitserial = unitserialedittext.getText().toString();
        String message = message_edittext.getText().toString();
        String originalunit = originalunitselecttextview.getText().toString();
        String failedpart = failedpartselecttextview.getText().toString();
        if (checkIfStringisEmpty(salesorder)) {
            salesorderedittext.setError(getString(R.string.salesorder_cant_be_empty));
        }
        if (checkIfStringisEmpty(manufacturer)) {
            manufactureredittext.setError(getString(R.string.manufacturer_cant_be_empty));
        }
        if (checkIfStringisEmpty(unitmode)) {
            unitmodeledittext.setError(getString(R.string.unit_model_cant_be_empty));
        }
        if (checkIfStringisEmpty(unitserial)) {
            unitserialedittext.setError(getString(R.string.unit_serial_cant_be_empty));
        }
        if (checkIfStringisEmpty(message)) {
            message_edittext.setError(getString(R.string.message_cant_be_empty));
        }
        if (originalunit.equals(getString(R.string.select_date))) {
            originalunitdateerror.setVisibility(View.VISIBLE);
        }
        if (failedpart.equals(getString(R.string.select_date))) {
            failedparterror.setVisibility(View.VISIBLE);
        }
        if (!checkIfStringisEmpty(salesorder) && !checkIfStringisEmpty(manufacturer) && !checkIfStringisEmpty(unitmode)
                && !checkIfStringisEmpty(unitserial) && !checkIfStringisEmpty(message)
                && !checkIfStringisEmpty(originalunit) && !checkIfStringisEmpty(failedpart)
        &&!(failedpart.equals(getString(R.string.select_date)))&&!(originalunit.equals(getString(R.string.select_date)))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIfStringisEmpty(String value) {
        if (TextUtils.isEmpty(value)) {
            return true;
        } else {
            return false;
        }
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