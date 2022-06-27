package com.tech.denso.Fragments;

import static com.tech.denso.Helper.Helper.getListener;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.tech.denso.Activities.DashboardActivity;
import com.tech.denso.Helper.Helper;
import com.tech.denso.Interfaces.ListenFromActivity;
import com.tech.denso.Interfaces.Listener;
import com.tech.denso.Item;
import com.tech.denso.Models.InitialWarrantyFragment.InitialWarrantyModel;
import com.tech.denso.R;
import com.tech.denso.SelectableAdapter;
import com.tech.denso.ViewModels.InitialViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InitialWarrantyFragment extends Fragment {

    private String title;
    private int page;
    View view;


    TextView sp_services_text;
    EditText companynameedittext, streetedittext, cityedittext, phoneedittext, homeownernameedittext, homestreetedittext, homeownercityedittext, homeownerphoneedittext;
    MaterialButton nextbtn;
    View companynameview, streetview, cityview, phoneview, homeownernameview, homestreetview, homeownercityview, homeownerphoneview;
    ImageView phoneredcheck, phonegreencheck, homeownerphoneredcheck, homeownerphonegreencheck;

    public InitialWarrantyFragment() {
    }

    public static InitialWarrantyFragment newInstance(int page, String title) {
        InitialWarrantyFragment fragmentFirst = new InitialWarrantyFragment();
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
        view = inflater.inflate(R.layout.fragment_initial_warranty, container, false);
        nextbtn = view.findViewById(R.id.nextbtn);

        companynameedittext = view.findViewById(R.id.companynameedittext);
        streetedittext = view.findViewById(R.id.streetedittext);
        cityedittext = view.findViewById(R.id.cityedittext);
        phoneedittext = view.findViewById(R.id.phoneedittext);
        sp_services_text = view.findViewById(R.id.slectservicesterxt);
        homeownernameedittext = view.findViewById(R.id.homeownernameedittext);
        homestreetedittext = view.findViewById(R.id.homestreetedittext);
        homeownercityedittext = view.findViewById(R.id.homeownercityedittext);
        homeownerphoneedittext = view.findViewById(R.id.homeownerphoneedittext);

        companynameview = view.findViewById(R.id.companynameview);
        streetview = view.findViewById(R.id.streetview);
        cityview = view.findViewById(R.id.cityview);
        phoneview = view.findViewById(R.id.phoneview);
        homeownernameview = view.findViewById(R.id.homeownernameview);
        homestreetview = view.findViewById(R.id.homestreetview);
        homeownercityview = view.findViewById(R.id.homeownercityview);
        homeownerphoneview = view.findViewById(R.id.homeownerphoneview);
        phonegreencheck = view.findViewById(R.id.phonegreencheck);
        phoneredcheck = view.findViewById(R.id.phoneredcheck);
        homeownerphoneredcheck = view.findViewById(R.id.homeownerphoneredcheck);
        homeownerphonegreencheck = view.findViewById(R.id.homeownerphonegreencheck);

        phoneedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isPhoneNumberValid("+966"+s.toString())) {
                    phonegreencheck.setVisibility(View.VISIBLE);
                    phoneredcheck.setVisibility(View.GONE);
                    phoneview.getBackground().setColorFilter(Color.parseColor("#00C149"),
                            PorterDuff.Mode.SRC_ATOP);

//                    int colorTo = Color.parseColor("#00C149");
//                    int color =getResources().getColor(android.R.color.holo_red_light);
//                    Drawable background = view.getBackground();
//                    if (background instanceof ColorDrawable) {
//                        color = ((ColorDrawable) background).getColor();
//                    }
//                    Log.e("colorchevkalue",""+color);
//                    int colorFrom = color;
//                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//                    colorAnimation.setDuration(250);
//                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animator) {
//                            Log.e("coloranimationcheck", "here");
//                            phoneview.getBackground().setColorFilter((int) animator.getAnimatedValue(),
//                                    PorterDuff.Mode.SRC_ATOP);
//                        }
//
//                    });
//                    colorAnimation.start();
                } else {
                    phonegreencheck.setVisibility(View.GONE);
                    phoneredcheck.setVisibility(View.VISIBLE);
                    phoneview.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light),
                            PorterDuff.Mode.SRC_ATOP);
//                    int color = Color.parseColor("#00C149");
//                    Drawable background = view.getBackground();
//                    if (background instanceof ColorDrawable) {
//                        color = ((ColorDrawable) background).getColor();
//                    }
//                    Log.e("colorchevkalue1",""+color);
//                    int colorFrom = color;
//                    int colorTo = getResources().getColor(android.R.color.holo_red_light);
//                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//                    colorAnimation.setDuration(250);
//                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animator) {
//                            Log.e("coloranimationcheck1", "here");
//                            phoneview.getBackground().setColorFilter((int) animator.getAnimatedValue(),
//                                    PorterDuff.Mode.SRC_ATOP);
//                        }
//
//                    });
//                    colorAnimation.start();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        homeownerphoneedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isPhoneNumberValid("+966"+s.toString())) {
                    homeownerphonegreencheck.setVisibility(View.VISIBLE);
                    homeownerphoneredcheck.setVisibility(View.GONE);
                    homeownerphoneview.getBackground().setColorFilter(Color.parseColor("#00C149"),
                            PorterDuff.Mode.SRC_ATOP);
                } else {
                    homeownerphonegreencheck.setVisibility(View.GONE);
                    homeownerphoneredcheck.setVisibility(View.VISIBLE);
                    homeownerphoneview.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light),
                            PorterDuff.Mode.SRC_ATOP);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setFocusChangeListener(companynameedittext, companynameview);
        setFocusChangeListener(streetedittext, streetview);
        setFocusChangeListener(cityedittext, cityview);
        setFocusChangeListener(phoneedittext, phoneview);
        setFocusChangeListener(homeownernameedittext, homeownernameview);
        setFocusChangeListener(homestreetedittext, homestreetview);
        setFocusChangeListener(homeownercityedittext, homeownercityview);
        setFocusChangeListener(homeownerphoneedittext, homeownerphoneview);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    new Helper().HideKeyboard(getActivity());
                }
                if (CheckCondition()) {
                    InitialWarrantyModel initialmodel = new InitialWarrantyModel();
                    initialmodel.setCompanyName(companynameedittext.getText().toString());
                    initialmodel.setStreet(streetedittext.getText().toString());
                    initialmodel.setCity(cityedittext.getText().toString());
                    initialmodel.setPhoneNumber("+966" + phoneedittext.getText().toString());
                    initialmodel.setHomeOwnerName(homeownernameedittext.getText().toString());
                    initialmodel.setHomeOwnerStreet(homestreetedittext.getText().toString());
                    initialmodel.setHomeOwnerCity(homeownercityedittext.getText().toString());
                    initialmodel.setHomeOwnerPhoneNumber(homeownerphoneedittext.getText().toString());
                    InitialViewModel model = new ViewModelProvider(requireActivity()).get(InitialViewModel.class);
                    model.select(initialmodel);
                    DashboardActivity.backbtn.setVisibility(View.VISIBLE);
                    WarrantyFragment.pager.setCurrentItem(1);
                }
            }
        });
        return view;
    }

    private boolean CheckCondition() {
        String companyname = companynameedittext.getText().toString();
        String street = streetedittext.getText().toString();
        String city = cityedittext.getText().toString();
        String phoneNumber = "+966" + phoneedittext.getText().toString();
        String homeOwnerName = homeownernameedittext.getText().toString();
        String homeOwnerStreet = homestreetedittext.getText().toString();
        String homeOwnerCity = homeownercityedittext.getText().toString();
        String homeOwnerPhoneNumber = "+966" + homeownerphoneedittext.getText().toString();
        if (checkIfStringisEmpty(companyname)) {
            companynameedittext.setError(getString(R.string.company_name_empty));
        }
        if (checkIfStringisEmpty(street)) {
            streetedittext.setError(getString(R.string.street_cant_be_empty));
        }
        if (checkIfStringisEmpty(city)) {
            cityedittext.setError(getString(R.string.city_name_cant_be_empty));
        }
        if (!isPhoneNumberValid(phoneNumber)) {
            phoneedittext.setError(getString(R.string.company_phone_number_not_valid));
        }
        if (checkIfStringisEmpty(homeOwnerName)) {
            homeownernameedittext.setError(getString(R.string.homeownername_cant_be_empty));
        }
        if (checkIfStringisEmpty(homeOwnerStreet)) {
            homestreetedittext.setError(getString(R.string.homeownerstreet_cant_be_empty));
        }
        if (checkIfStringisEmpty(homeOwnerCity)) {
            homeownercityedittext.setError(getString(R.string.homeownercityname_cant_be_empty));
        }
        if (!isPhoneNumberValid(homeOwnerPhoneNumber)) {
            homeownerphoneedittext.setError(getString(R.string.homeowner_phone_number_not_Valid));
        }
        if (!checkIfStringisEmpty(companyname) && !checkIfStringisEmpty(street) && !checkIfStringisEmpty(city)
                && !checkIfStringisEmpty(phoneNumber) &&
                isPhoneNumberValid(phoneNumber) && !checkIfStringisEmpty(homeOwnerName)
                && !checkIfStringisEmpty(homeOwnerStreet) && !checkIfStringisEmpty(homeOwnerCity)
                && isPhoneNumberValid(homeOwnerPhoneNumber)) {
            Log.e("initialwarranty", "here");
            return true;
        } else {
            Log.e("initialwarranty", "here1");
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

    boolean isPhoneNumberValid(String number) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance(getContext());
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(number, "SA");
            return phoneUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return false;
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
                            view.getBackground().setColorFilter((int) animator.getAnimatedValue(),
                                    PorterDuff.Mode.SRC_ATOP);
                        }
                    });
                    colorAnimation.start();
                }
            }
        });
    }

    public List<Item> generateItems() {
        List<Item> selectableItems = new ArrayList<>();
        selectableItems.add(new Item("C++", false));
        selectableItems.add(new Item("Python", false));
        selectableItems.add(new Item("C", false));
        selectableItems.add(new Item("Java", false));
        return selectableItems;
    }
}