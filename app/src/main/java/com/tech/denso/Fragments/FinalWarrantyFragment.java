package com.tech.denso.Fragments;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.tech.denso.Activities.LoginActivity;
import com.tech.denso.Activities.SucessfullClaimActivity;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.Models.BookingsModel.BookingSendModel;
import com.tech.denso.Models.InitialWarrantyFragment.InitialWarrantyModel;
import com.tech.denso.Models.WarrantyClaim.WarrantyClaim;
import com.tech.denso.R;
import com.tech.denso.ViewModels.NextViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FinalWarrantyFragment extends Fragment {

    private int page;
    private String title;
    View view;
    EditText newpartedittext, newserialnumberedittext, newpartnameedittext, newpartinvoinceedittext, message_edittext;
    View newpartview, newserialnumberview, newpartnameview, newpartinvoiceview;
    InitialWarrantyModel item;
    MaterialButton submitbtn;

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
        message_edittext = view.findViewById(R.id.message_edittext);

        newpartview = view.findViewById(R.id.newpartview);
        newserialnumberview = view.findViewById(R.id.newserialnumberview);
        newpartnameview = view.findViewById(R.id.newpartnameview);
        newpartinvoiceview = view.findViewById(R.id.newpartinvoiceview);

        submitbtn = view.findViewById(R.id.submitbtn);
        setFocusChangeListener(newpartedittext, newpartview);
        setFocusChangeListener(newserialnumberedittext, newserialnumberview);
        setFocusChangeListener(newpartnameedittext, newpartnameview);
        setFocusChangeListener(newpartinvoinceedittext, newpartinvoiceview);
        NextViewModel model = new ViewModelProvider(requireActivity()).get(NextViewModel.class);
        model.getSelected().observe(getViewLifecycleOwner(), item -> {
            this.item = item;
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null && CheckCondition()) {
                    String newPartnumber = newpartedittext.getText().toString();
                    String newSerialNumber = newserialnumberedittext.getText().toString();
                    String newPartName = newpartnameedittext.getText().toString();
                    String newPartInvoice = newpartinvoinceedittext.getText().toString();
                    String comments = message_edittext.getText().toString();
                    item.setNewPartNumber(newPartnumber);
                    item.setNewSerialNumber(newSerialNumber);
                    item.setNewPartName(newPartName);
                    item.setNewPartInvoice(newPartInvoice);
                    item.setComments(comments);
                    try {
                        boolean loggedbol = new SharedPreference(getContext(), getContext().toString()).getPreferenceBoolean("LoggedIn");
                        if (loggedbol) {
                            SubmitClaimRequest(item);
                        } else {
                            Toast.makeText(getContext(), "Please SignUp Or Login First!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getContext(), LoginActivity.class);
                            i.putExtra("warrantyfromfragment", true);
                            i.putExtra("warrantymodel", item);
                            startActivity(i);
                        }

                    } catch (JSONException e) {
                        Log.e("exceptionissubmittion1", "" + Arrays.toString(e.getStackTrace()));
                    }
                }
            }
        });
        return view;
    }

    private void SubmitClaimRequest(InitialWarrantyModel item) throws JSONException {
        String url = new Const().getBaseUrl() + "/api/warrantyclaims/";
        Log.e("yurlcehckvalue1", "" + url);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("companyName", item.getCompanyName());
        jsonBody.put("street", item.getStreet());
        jsonBody.put("cityStateZip", item.getCity());
        jsonBody.put("phoneNumber", item.getPhoneNumber());
        jsonBody.put("homeOwnerName", item.getHomeOwnerName());
        jsonBody.put("homeOwnerStreet", item.getHomeOwnerStreet());
        jsonBody.put("homeOwnerCityStateZip", item.getHomeOwnerCity());
        jsonBody.put("homeOwnerPhoneNumber", item.getHomeOwnerPhoneNumber());
        jsonBody.put("salesOrderOrInvoiceNumber", item.getSaleOrder());
        jsonBody.put("originalUnitInstallDate", item.getOriginalUnitDate());
        jsonBody.put("failedDate", item.getFailedUnitDate());
        jsonBody.put("manufacturer", item.getManufacturer());
        jsonBody.put("unitModelNumber", item.getUnitModelNumber());
        jsonBody.put("unitSerialNumber", item.getUnitSerialNumber());
        jsonBody.put("unitPartNumber", item.getUnitPartNumber());//
        jsonBody.put("modelNumber", item.getModelNumber());//
        jsonBody.put("serialNumber", item.getSerialNumber());
        jsonBody.put("failureReason", item.getFailureReasonMessage());
        jsonBody.put("newPartNumber", item.getNewPartNumber());
        jsonBody.put("newPartName", item.getNewPartName());
        jsonBody.put("newSerialNumber", item.getNewSerialNumber());
        jsonBody.put("newPartInvoiceNumber", item.getNewPartInvoice());
        jsonBody.put("message", item.getComments());//
        Log.e("finaljsonobkect", "" + jsonBody.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("responsevaluechecl", "" + response);
                Gson gson = new Gson();
                WarrantyClaim responsedata = gson.fromJson(response.toString(), WarrantyClaim.class);
                if (responsedata.getMessage().equals("Warranty Claim has been Submitted Successfully!")) {
                    Intent i = new Intent(getActivity(), SucessfullClaimActivity.class);
                    startActivity(i);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("responsevaluechecl1", "" + Arrays.toString(error.getStackTrace()));
            }
        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                final Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Basic " + "c2FnYXJAa2FydHBheS5jb206cnMwM2UxQUp5RnQzNkQ5NDBxbjNmUDgzNVE3STAyNzI=");//put your token here
//                return headers;
//            }
        };
        requestQueue.add(jsonOblect);
    }

    private boolean CheckCondition() {
        String newPart = newpartedittext.getText().toString();
        String newSerialNumber = newserialnumberedittext.getText().toString();
        String newPartName = newpartnameedittext.getText().toString();
        String newPartInvoice = newpartinvoinceedittext.getText().toString();
        String comments = message_edittext.getText().toString();
        if (checkIfStringisEmpty(newPart)) {
            newpartedittext.setError(getString(R.string.salesorder_cant_be_empty));
        }
        if (checkIfStringisEmpty(newSerialNumber)) {
            newserialnumberedittext.setError(getString(R.string.manufacturer_cant_be_empty));
        }
        if (checkIfStringisEmpty(newPartName)) {
            newpartnameedittext.setError(getString(R.string.unit_model_cant_be_empty));
        }
        if (checkIfStringisEmpty(newPartInvoice)) {
            newpartinvoinceedittext.setError(getString(R.string.unit_serial_cant_be_empty));
        }
        if (checkIfStringisEmpty(comments)) {
            message_edittext.setError(getString(R.string.comments_cant_be_empty));
        }
        if (!checkIfStringisEmpty(newPart) && !checkIfStringisEmpty(newSerialNumber) && !checkIfStringisEmpty(newPartName)
                && !checkIfStringisEmpty(newPartInvoice)) {
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