package com.tech.denso.Fragments;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.tech.denso.Activities.DashboardActivity;
import com.tech.denso.Adapter.BookingBranchSpinnerAdapter;
import com.tech.denso.Adapter.WarrantyModelSpinnerAdapter;
import com.tech.denso.Adapter.WarrantyNameSpinnerAdapter;
import com.tech.denso.Helper.App;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.Helper;
import com.tech.denso.Models.InitialWarrantyFragment.InitialWarrantyModel;
import com.tech.denso.Models.Vehicles.VehicleModel.Datum;
import com.tech.denso.Models.Vehicles.VehicleName.Vehicles;
import com.tech.denso.Models.Vehicles.VehiclesModel;
import com.tech.denso.R;
import com.tech.denso.ViewModels.InitialViewModel;
import com.tech.denso.ViewModels.NextViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import needle.Needle;
import needle.UiRelatedTask;


public class NextWarrantyFragment extends Fragment {

    private int page;
    private String title;
    View view;
    EditText salesorderedittext, manufactureredittext, unitmodeledittext, unitserialedittext, unitpartnumberedittext, message_edittext;
    MaterialButton nextbtn;
    View unitserialview, unitmodelview, manufacturerview, salesorderview, unitpartnumberview;
    InitialWarrantyModel item;
    TextView originalunitselecttextview, failedpartselecttextview, failedparterror, originalunitdateerror;
    final Calendar originalUnitCalendar = Calendar.getInstance();
    final Calendar failedPartCalendar = Calendar.getInstance();
    Spinner selectnamespinner, selectmodelspinner;
    TextView selectvehiclenametext, selectvehiclemodeltext;
    TextView nonametextview, nomodeltextview;
    ImageView namedropdownicon, modeldropdownicon;

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
        unitpartnumberedittext = view.findViewById(R.id.unitpartnumberedittext);

        selectnamespinner = view.findViewById(R.id.selectnamespinner);
        selectmodelspinner = view.findViewById(R.id.selectmodelspinner);

        selectvehiclenametext = view.findViewById(R.id.selectvehiclenametext);
        selectvehiclemodeltext = view.findViewById(R.id.selectvehiclemodeltext);
        nonametextview = view.findViewById(R.id.nonametextview);
        nomodeltextview = view.findViewById(R.id.nomodeltextview);
        namedropdownicon = view.findViewById(R.id.namedropdownicon);
        modeldropdownicon = view.findViewById(R.id.modeldropdownicon);

        unitserialview = view.findViewById(R.id.unitserialview);
        unitmodelview = view.findViewById(R.id.unitmodelview);
        manufacturerview = view.findViewById(R.id.manufacturerview);
        salesorderview = view.findViewById(R.id.salesorderview);
        unitpartnumberview = view.findViewById(R.id.unitpartnumberview);
        nextbtn = view.findViewById(R.id.nextbtn);
        failedparterror = view.findViewById(R.id.failedparterror);
        originalunitdateerror = view.findViewById(R.id.originalunitdateerror);

        ShowVehicleName();
        ShowVehicleModel();

        setFocusChangeListener(unitserialedittext, unitserialview);
        setFocusChangeListener(unitmodeledittext, unitmodelview);
        setFocusChangeListener(manufactureredittext, manufacturerview);
        setFocusChangeListener(salesorderedittext, salesorderview);
        setFocusChangeListener(unitpartnumberedittext, unitpartnumberview);
        message_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    message_edittext.setBackgroundResource(R.drawable.selected_warranty_failure_edittext_background);
                } else {
                    message_edittext.setBackgroundResource(R.drawable.warranty_failure_edittext_background);
                }
            }
        });
        InitialViewModel model = new ViewModelProvider(requireActivity()).get(InitialViewModel.class);
        model.getSelected().observe(getViewLifecycleOwner(), item -> {
            Log.e("minitialvaluecheck", "" + item.getCity());
            this.item = item;
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    new Helper().HideKeyboard(getActivity());
                }
                if (item != null && CheckCondition()) {
                    String salesorder = salesorderedittext.getText().toString();
                    String manufacturer = manufactureredittext.getText().toString();
                    String unitmodel = unitmodeledittext.getText().toString();
                    String unitserial = unitserialedittext.getText().toString();
                    String unitpartnumber = unitpartnumberedittext.getText().toString();
                    String failurereasonmessage = message_edittext.getText().toString();
                    String originalunitdate = originalunitselecttextview.getText().toString();
                    String failedpartdate = failedpartselecttextview.getText().toString();
                    String vehiclename = getCurrentVehicleName();
                    String vehiclemodel = getCurrentVehicleModel();
                    item.setSaleOrder(salesorder);
                    item.setManufacturer(manufacturer);
                    item.setUnitModelNumber(unitmodel);
                    item.setUnitSerialNumber(unitserial);
                    item.setUnitPartNumber(unitpartnumber);
                    item.setVehicleName(vehiclename);
                    item.setVehicleModel(vehiclemodel);
                    item.setFailureReasonMessage(failurereasonmessage);
                    item.setOriginalUnitDate(originalunitdate);
                    item.setFailedUnitDate(failedpartdate);
                    NextViewModel model = new ViewModelProvider(requireActivity()).get(NextViewModel.class);
                    model.select(item);
                    DashboardActivity.backbtn.setVisibility(View.VISIBLE);
                    WarrantyFragment.pager.setCurrentItem(2);
                }
            }
        });
        originalunitselecttextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        originalUnitCalendar.set(Calendar.YEAR, year);
                        originalUnitCalendar.set(Calendar.MONTH, month);
                        originalUnitCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        setDateToTextView(originalUnitCalendar, originalunitselecttextview, originalunitdateerror);
                    }
                }, originalUnitCalendar.get(Calendar.YEAR), originalUnitCalendar.get(Calendar.MONTH), originalUnitCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        failedpartselecttextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        failedPartCalendar.set(Calendar.YEAR, year);
                        failedPartCalendar.set(Calendar.MONTH, month);
                        failedPartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        setDateToTextView(failedPartCalendar, failedpartselecttextview, failedparterror);
                    }
                }, failedPartCalendar.get(Calendar.YEAR), failedPartCalendar.get(Calendar.MONTH), failedPartCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        return view;
    }

    private void ShowVehicleName() {
        ArrayList<com.tech.denso.Models.Vehicles.VehicleName.Datum> list = new ArrayList();
        com.tech.denso.Models.Vehicles.VehicleName.Datum datum = new com.tech.denso.Models.Vehicles.VehicleName.Datum();
        datum.setVehicleType("Select Vehicle Name");
        list.add(datum);
        final WarrantyNameSpinnerAdapter[] customAdapter = {new WarrantyNameSpinnerAdapter(getContext(), list)};
        selectnamespinner.setAdapter(customAdapter[0]);
        selectnamespinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new Helper().HideKeyboard(getActivity());
                return false;
            }
        });
        selectnamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectvehiclenametext.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String url = new Const().getBaseUrl() + "/api/vehicles/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Needle.onBackgroundThread().execute(new UiRelatedTask<VehiclesModel>() {
                    @Override
                    protected VehiclesModel doWork() {
                        Gson gson = new Gson();
                        Vehicles responsedata = gson.fromJson(response, Vehicles.class);
                        VehiclesModel model = new VehiclesModel();
                        list.addAll(responsedata.getData());
                        model.setList(list);
                        model.setComplete(true);
                        return model;
                    }

                    @Override
                    protected void thenDoUiRelatedWork(VehiclesModel result) {
                        if (result.isComplete()) {
                            if (result.getList().size() > 1) {
                                nonametextview.setVisibility(View.GONE);
                                namedropdownicon.setVisibility(View.VISIBLE);
                                selectnamespinner.setVisibility(View.VISIBLE);
                                customAdapter[0].notifyDataSetChanged();
                            } else {
                                nonametextview.setVisibility(View.VISIBLE);
                                namedropdownicon.setVisibility(View.GONE);
                                selectnamespinner.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("responsecheckvalue1", "" + Arrays.toString(error.getStackTrace()));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                Const constant = new Const();
                Log.e("Emailpasswordcheck", "" + constant.getEmail() + " " + constant.getPassword());
                String creds = String.format("%s:%s", constant.getEmail(), constant.getPassword());
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(req, url);
    }

    private void ShowVehicleModel() {
        List<Datum> list = new ArrayList();
        com.tech.denso.Models.Vehicles.VehicleModel.Datum datum = new com.tech.denso.Models.Vehicles.VehicleModel.Datum();
        datum.setModelYear("Select Vehicle Model");
        list.add(datum);
        final WarrantyModelSpinnerAdapter[] customAdapter = {new WarrantyModelSpinnerAdapter(getContext(), list)};
        selectmodelspinner.setAdapter(customAdapter[0]);
        selectmodelspinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new Helper().HideKeyboard(getActivity());
                return false;
            }
        });
        selectmodelspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectvehiclemodeltext.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String url = new Const().getBaseUrl() + "/api/models/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Needle.onBackgroundThread().execute(new UiRelatedTask<VehiclesModelModel>() {
                    @Override
                    protected VehiclesModelModel doWork() {
                        Gson gson = new Gson();
                        com.tech.denso.Models.Vehicles.VehicleModel.VehiclesModel responsedata = gson.fromJson(response, com.tech.denso.Models.Vehicles.VehicleModel.VehiclesModel.class);
                        VehiclesModelModel model = new VehiclesModelModel();
                        list.addAll(responsedata.getData());
                        model.setList(list);
                        model.setComplete(true);
                        return model;
                    }

                    @Override
                    protected void thenDoUiRelatedWork(VehiclesModelModel result) {
                        if (result.isComplete()) {
                            if (result.getList().size() > 1) {
                                nomodeltextview.setVisibility(View.GONE);
                                modeldropdownicon.setVisibility(View.VISIBLE);
                                selectmodelspinner.setVisibility(View.VISIBLE);
                                customAdapter[0].notifyDataSetChanged();
                            } else {
                                nomodeltextview.setVisibility(View.VISIBLE);
                                modeldropdownicon.setVisibility(View.GONE);
                                selectmodelspinner.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("responsecheckvalue1", "" + Arrays.toString(error.getStackTrace()));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                Const constant = new Const();
                Log.e("Emailpasswordcheck", "" + constant.getEmail() + " " + constant.getPassword());
                String creds = String.format("%s:%s", constant.getEmail(), constant.getPassword());
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(req, url);
    }

    private void setDateToTextView(Calendar cal, TextView textView, TextView errorTextView) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        textView.setText(dateFormat.format(cal.getTime()));
        errorTextView.setVisibility(View.GONE);
    }

    public String getCurrentVehicleName() {
        com.tech.denso.Models.Vehicles.VehicleName.Datum model = (com.tech.denso.Models.Vehicles.VehicleName.Datum) selectnamespinner.getSelectedItem();
        return model.getVehicleType();
    }

    public String getCurrentVehicleModel() {
        com.tech.denso.Models.Vehicles.VehicleModel.Datum model = (com.tech.denso.Models.Vehicles.VehicleModel.Datum) selectmodelspinner.getSelectedItem();
        return model.getModelYear();
    }

    private boolean CheckCondition() {
        String salesorder = salesorderedittext.getText().toString();
        String manufacturer = manufactureredittext.getText().toString();
        String unitmode = unitmodeledittext.getText().toString();
        String unitserial = unitserialedittext.getText().toString();
        String message = message_edittext.getText().toString();
        String originalunit = originalunitselecttextview.getText().toString();
        String failedpart = failedpartselecttextview.getText().toString();
        String unitpartnumber = unitpartnumberedittext.getText().toString();
        String vehiclename = getCurrentVehicleName();
        String vehiclemodel = getCurrentVehicleModel();
        if (checkIfStringisEmpty(salesorder)) {
            salesorderedittext.setError(getString(R.string.salesorder_cant_be_empty));
        }
        if (vehiclename.equals("Select Vehicle Name")) {
            selectvehiclenametext.setVisibility(View.VISIBLE);
        }
        if (vehiclemodel.equals("Select Vehicle Model")) {
            selectvehiclemodeltext.setVisibility(View.VISIBLE);
        }
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
        if (checkIfStringisEmpty(unitpartnumber)) {
            unitpartnumberedittext.setError(getString(R.string.unit_part_number_cant_be_empty));
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
                && !checkIfStringisEmpty(originalunit)
                && !checkIfStringisEmpty(unitpartnumber)
                && (!vehiclename.equals("Select Vehicle Name"))
                && (!vehiclemodel.equals("Select Vehicle Model"))
                && !checkIfStringisEmpty(failedpart)
                && !(failedpart.equals(getString(R.string.select_date))) && !(originalunit.equals(getString(R.string.select_date)))) {
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