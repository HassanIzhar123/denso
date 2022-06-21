package com.tech.denso.Fragments;

import static android.app.Activity.RESULT_OK;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.common.base.CharMatcher;
import com.google.gson.Gson;
import com.tech.denso.Activities.DashboardActivity;
import com.tech.denso.Activities.IntroductionActivity;
import com.tech.denso.Activities.LoginActivity;
import com.tech.denso.Activities.SuccessfulBookingActivity;
import com.tech.denso.Adapter.BookingBranchSpinnerAdapter;
import com.tech.denso.Adapter.BookingMakeSpinnerAdapter;
import com.tech.denso.Adapter.BookingModelSpinnerAdapter;
import com.tech.denso.Adapter.BookingPreferredAndRequiredAdapter;
import com.tech.denso.Adapter.BookingTimeSpinnerAdapter;
import com.tech.denso.Adapter.BookingYearSpinnerAdapter;
import com.tech.denso.Adapter.CustomAdapter;
import com.tech.denso.Helper.App;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.Helper.TaskRunner;
import com.tech.denso.Interfaces.ListenFromActivity;
import com.tech.denso.Models.BookingsModel.BookingBranchSpinnerModel.BookingBranchSpinnerModel;
import com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.BookingMakeSpinnerModel;
import com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.BookingModelsSpinnerModel;
import com.tech.denso.Models.BookingsModel.BookingSendModel;
import com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.BookingTimeSpinnerModel;
import com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.BookingYearSpinnerModel;
import com.tech.denso.Models.Locations.Datum;
import com.tech.denso.Models.Locations.LocationsModel;
import com.tech.denso.Models.LoginModel.LoginModel;
import com.tech.denso.R;
import com.tech.denso.ViewModels.BookingModel;
import com.tech.denso.ViewModels.BookingViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class BookingFragment extends Fragment implements ListenFromActivity {
    private String title;
    private int page;
    EditText firstnameedittext, lastnamedittext, emailedittext, phoneedittext;
    TextView selectdatetextview;
    Spinner selectbranchspinner, selectmakespinner, selectmodelspinner, selectyearspinner, selectpreferredspinner, selectservicesspinner, selecttimeslotsspinner;
    ImageView emailgreencheck, emailredcheck, phoneredcheck, phonegreencheck;
    TextView firsttexterror, lasttexterror;
    TextView nobranchtextview, nomaketextview, nomodeltextview, noyeartextview, nopreferredtextview, noservicestextview, notimetextview;
    //    public ListenFromActivity activityListener;
    TextView selectmaketext, selectmodeltext, selectyeartext, selectbranchtext, selectpreferredtext, selectservicetext, selecttimeslottext;
    View firstnameview, lastnameview, emailview, phoneview;

    public void refreshList(String userList) {
        Toast.makeText(getContext(), "Come here", Toast.LENGTH_SHORT).show();
    }

    public static BookingFragment newInstance() {
        BookingFragment fragmentFirst = new BookingFragment();
        return fragmentFirst;
    }

    private String getMake() {
        com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.Datum model = (com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.Datum) selectmakespinner.getSelectedItem();
        return model.getVehicleType();
    }

    private String getModel() {
        com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum model = (com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum)
                selectmodelspinner.getSelectedItem();
        return model.getTransmissionName();
    }

    private String getYear() {
        com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.Datum model =
                (com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.Datum) selectyearspinner.getSelectedItem();
        return model.getModelYear();
    }

    private String getBranch() {
        com.tech.denso.Models.Locations.Datum model = (com.tech.denso.Models.Locations.Datum) selectbranchspinner.getSelectedItem();
        return model.getBranchName();
    }

    private String getPreferred() {
        String model = (String) selectpreferredspinner.getSelectedItem();
        return model;
    }

    private String getService() {
        String model = (String) selectservicesspinner.getSelectedItem();
        return model;
    }

    private String getTimeSlot() {
        com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.Datum model = (com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.Datum) selecttimeslotsspinner.getSelectedItem();
        return model.getTimeSlot();
    }

    public void doSomethingInFragment() {
        String make = getMake();
        String model = getModel();
        String year = getYear();
        String branch = getBranch();
        String preferred = getPreferred();
        String mserviceodel = getService();
        String timeslot = getTimeSlot();
        if (isEditTextEmptyOrnot(firstnameedittext)) {
            firstnameedittext.setError(getString(R.string.firstnameerror));
        }
        if (isEditTextEmptyOrnot(lastnamedittext)) {
            lastnamedittext.setError(getString(R.string.lastnameerror));
        }
        if (isEditTextEmptyOrnot(emailedittext)) {
            emailedittext.setError(getString(R.string.emailerror));
        }
        if (isEditTextEmptyOrnot(phoneedittext)) {
            phoneedittext.setError(getString(R.string.phonenumbererror));
        }
        if (make.equals("All Makes")) {
            selectmaketext.setVisibility(View.VISIBLE);
        }
        if (model.equals("All Models")) {
            selectmodeltext.setVisibility(View.VISIBLE);
        }
        if (year.equals("Year Model")) {
            selectyeartext.setVisibility(View.VISIBLE);
        }
        if (branch.equals("Select Branch")) {
            selectbranchtext.setVisibility(View.VISIBLE);
        }
        if (preferred.equals("Select Preferred")) {
            selectpreferredtext.setVisibility(View.VISIBLE);
        }
        if (mserviceodel.equals("Select Required Service")) {
            selectservicetext.setVisibility(View.VISIBLE);
        }
        if (timeslot.equals("HH:MM")) {
            selecttimeslottext.setVisibility(View.VISIBLE);
        }
        if (nobranchtextview.getVisibility() == View.VISIBLE) {
            Toast.makeText(getContext(), R.string.BookingFragment_nobrancherror, Toast.LENGTH_SHORT).show();
        }
        if (!isEditTextEmptyOrnot(firstnameedittext) && !isEditTextEmptyOrnot(lastnamedittext)
                && !isEditTextEmptyOrnot(emailedittext)
                && !isEditTextEmptyOrnot(phoneedittext)
                && !(make.equals("All Makes"))
                && !(model.equals("All Models"))
                && !(year.equals("Year Model"))
                && !(branch.equals("Select Branch"))
                && !((preferred.equals("Select Preferred"))
                && !(mserviceodel.equals("Select Required Service"))
                && !(timeslot.equals("HH:MM"))
                && isEmailValid(emailedittext.getText().toString()) && isPhoneNumberValid(phoneedittext.getText().toString()) &&
                !isFirstNameHavingSpaceOrNot(firstnameedittext.getText().toString()) && !isFirstNameHavingSpaceOrNot(lastnamedittext.getText().toString()))) {
            Boolean loggedbol = new SharedPreference(getContext(), getContext().toString()).getPreferenceBoolean("LoggedIn");
            if (loggedbol) {
                SendBooking(firstnameedittext.getText().toString().trim(), lastnamedittext.getText().toString().trim()
                        , emailedittext.getText().toString().trim(), phoneedittext.getText().toString().trim(),
                        make, "Waiting", model, mserviceodel, year, false, branch, timeslot, selectdatetextview.getText().toString().trim());
            } else {
                Toast.makeText(getContext(), "Please SignUp Or Login First!", Toast.LENGTH_SHORT).show();
                BookingSendModel bookingmodel = new BookingSendModel();
                bookingmodel.setFirstName(firstnameedittext.getText().toString().trim());
                bookingmodel.setLastName(lastnamedittext.getText().toString().trim());
                bookingmodel.setEmail(emailedittext.getText().toString().trim());
                bookingmodel.setPhoneNumber(phoneedittext.getText().toString().trim());
                bookingmodel.setMake(make);
                bookingmodel.setStatus("Waiting");
                bookingmodel.setModel(model);
                bookingmodel.setService(mserviceodel);
                bookingmodel.setYear(year);
                bookingmodel.setIsListed(String.valueOf(false));
                bookingmodel.setBranch(branch);
                bookingmodel.setTimeSlot(timeslot);
                bookingmodel.setDate(selectdatetextview.getText().toString().trim());
                Intent i = new Intent(getContext(), LoginActivity.class);
                i.putExtra("bookingfromfragment", true);
                i.putExtra("bookingmodel", bookingmodel);
                startActivityForResult(i, 2);
            }
//            SendBooking(firstnameedittext.getText().toString().trim(), lastnamedittext.getText().toString().trim()
//                    , emailedittext.getText().toString().trim(), phoneedittext.getText().toString().trim(),
//                    caredittext.getText().toString().trim(), yearsedittext.getText().toString().trim()
//                    , transmissionsedittext.getText().toString().trim(), wairsedittext.getText().toString().trim(),
//                    listedcheckbox.isChecked(), descriptionedittext.getText().toString().trim(),
//                    dat.getAddress().trim()
//                    , selectdatetextview.getText().toString().trim(), selecttimetextiew.getText().toString().trim());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Boolean check = data.getBooleanExtra("comingbackbooking", false);
                if (check) {
                    BookingSendModel bookingmodel = (BookingSendModel) data.getSerializableExtra("bookingmodel");
                    SendBooking(bookingmodel.getFirstName(), bookingmodel.getLastName()
                            , bookingmodel.getEmail(), bookingmodel.getPhoneNumber(),
                            bookingmodel.getMake(), bookingmodel.getStatus(), bookingmodel.getModel(), bookingmodel.getService(), bookingmodel.getYear(), Boolean.valueOf(bookingmodel.getIsListed()), bookingmodel.getBranch(), bookingmodel.getTimeSlot(), bookingmodel.getDate());
                }
            }
        }
    }

    private void SendBooking(String firstname, String lastname, String email, String phonenumber, String vehicleType, String waitingStatus, String transmission,
                             String serviceDetails, String model, Boolean isListed, String branchName, String bookingTime, String bookingDate) {
        try {
            Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.loading_dialog);
            dialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            String URL = new Const().getBaseUrl() + "/api/bookings";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("bookingDate", bookingDate);
            jsonBody.put("bookingTime", bookingTime);
            jsonBody.put("branchName", branchName);
            jsonBody.put("email", email);
            jsonBody.put("firstName", firstname);
            jsonBody.put("isListed", String.valueOf(isListed));
            jsonBody.put("lastName", lastname);
            jsonBody.put("phoneNumber", phonenumber);
            jsonBody.put("serviceDetails", serviceDetails);
            jsonBody.put("transmission", transmission);
            jsonBody.put("vehicleType", vehicleType);
            jsonBody.put("model", model);
            jsonBody.put("waitingStatus", "Waiting");
//            String finaldate=localToGMT(date,"dd/MM/yyyy","yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//            String finaltime=localToGMT(time,"HH:mm","HH:mm:ss'Z'");
            final String requestBody = jsonBody.toString();
            Log.e("jsonbodychjeck", "" + requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new TaskRunner().executeAsync(new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            Gson gson = new Gson();
                            LoginModel responsedata = gson.fromJson(response, LoginModel.class);
                            if (responsedata.getMessage() != null) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.cancel();
                                    }
                                }, 1000);
                            }
                            return null;
                        }
                    }, new TaskRunner.Callback<Object>() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onComplete(Object result) {
                            if (dialog != null) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                    startActivity(new Intent(getActivity(), SuccessfulBookingActivity.class));
                                    setAllEdittextClear();
                                    emailedittext.setText(new Const().getEmail());
                                    selectdatetextview.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
                                    Boolean loggedbol = new SharedPreference(getContext(), getContext().toString()).getPreferenceBoolean("LoggedIn");
                                    if (loggedbol) {
                                        BookingViewModel model = new ViewModelProvider(requireActivity()).get(BookingViewModel.class);
                                        BookingModel bookingmodel = new BookingModel();
                                        bookingmodel.setLogOutVisibility(View.VISIBLE);
                                        bookingmodel.setHistoryRelVisibility(View.VISIBLE);
                                        bookingmodel.setLoginVisibility(View.GONE);
                                        model.select(bookingmodel);
                                    } else {
                                        BookingViewModel model = new ViewModelProvider(requireActivity()).get(BookingViewModel.class);
                                        BookingModel bookingmodel = new BookingModel();
                                        bookingmodel.setLogOutVisibility(View.GONE);
                                        bookingmodel.setHistoryRelVisibility(View.GONE);
                                        bookingmodel.setLoginVisibility(View.VISIBLE);
                                        model.select(bookingmodel);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            if (dialog != null) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEYERROR", error.toString());
                    Toast.makeText(getContext(), "Error Occured!", Toast.LENGTH_SHORT).show();
                    if (dialog != null) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String localToGMT(String datestr, String inputformat, String outputformat) {
//        Date myDate = null;
//        String dtStart = date;
//        SimpleDateFormat format = new SimpleDateFormat(inputformat);
//        try {
//            myDate = format.parse(dtStart);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
//        calendar.setTime(myDate);
//        Date time = calendar.getTime();
//        SimpleDateFormat outputFmt = new SimpleDateFormat(outputformat);
//        String dateAsString = outputFmt.format(time);
//        System.out.println(dateAsString);
//        return dateAsString;
        String expiryDateString = datestr;
        final SimpleDateFormat formatter = new SimpleDateFormat(inputformat);
        final SimpleDateFormat outformatter = new SimpleDateFormat(outputformat);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        outformatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = formatter.parse(expiryDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return outformatter.format(date);
    }

    private void Showbranch() {
        ArrayList<com.tech.denso.Models.Locations.Datum> list = new ArrayList();
        com.tech.denso.Models.Locations.Datum datum = new com.tech.denso.Models.Locations.Datum();
        datum.setBranchName("Select Branch");
        list.add(datum);
        final BookingBranchSpinnerAdapter[] customAdapter = {new BookingBranchSpinnerAdapter(getContext(), list)};
        selectbranchspinner.setAdapter(customAdapter[0]);
        selectbranchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectbranchtext.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String url = new Const().getBaseUrl() + "/api/locations/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new TaskRunner().executeAsync(new Callable<List<com.tech.denso.Models.Locations.Datum>>() {
                    @Override
                    public List<com.tech.denso.Models.Locations.Datum> call() {
                        Gson gson = new Gson();
                        LocationsModel responsedata = gson.fromJson(response, LocationsModel.class);
                        list.addAll(responsedata.getData());
//                        Gson gson = new Gson();
//                        BookingBranchSpinnerModel responsedata = gson.fromJson(response, BookingBranchSpinnerModel.class);
//                        list.addAll(responsedata.getData());
                        return list;
                    }
                }, new TaskRunner.Callback<List<com.tech.denso.Models.Locations.Datum>>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(List<com.tech.denso.Models.Locations.Datum> result) {
                        Log.e("datumvaluecheclk", "" + result);
                        if (result.size() <= 1) {
                            nobranchtextview.setVisibility(View.VISIBLE);
                            selectbranchspinner.setVisibility(View.GONE);
                        } else {
                            nobranchtextview.setVisibility(View.GONE);
                            selectbranchspinner.setVisibility(View.VISIBLE);
                            customAdapter[0].notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
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

    private void ShowMakes() {
        ArrayList<com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.Datum> list = new ArrayList();
        com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.Datum datum = new com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.Datum();
        datum.setVehicleType("All Makes");
        list.add(datum);
        final BookingMakeSpinnerAdapter[] customAdapter = {new BookingMakeSpinnerAdapter(getContext(), list)};
        selectmakespinner.setAdapter(customAdapter[0]);
        selectmakespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectmaketext.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String url = new Const().getBaseUrl() + "/api/vehicles/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new TaskRunner().executeAsync(new Callable<List<com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.Datum>>() {
                    @Override
                    public List<com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.Datum> call() {
                        Gson gson = new Gson();
                        BookingMakeSpinnerModel responsedata = gson.fromJson(response, BookingMakeSpinnerModel.class);
                        list.addAll(responsedata.getData());
                        return list;
                    }
                }, new TaskRunner.Callback<List<com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.Datum>>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(List<com.tech.denso.Models.BookingsModel.BookingMakeSpinnerModel.Datum> result) {
                        Log.e("datumvaluecheclk", "" + result);
                        if (result.size() <= 1) {
                            nomaketextview.setVisibility(View.VISIBLE);
                            selectmakespinner.setVisibility(View.GONE);
                        } else {
                            nomaketextview.setVisibility(View.GONE);
                            selectmakespinner.setVisibility(View.VISIBLE);
                            customAdapter[0].notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
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

    private void ShowTransmission() {
        ArrayList<com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum> list = new ArrayList();
        com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum datum = new com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum();
        datum.setTransmissionName("All Models");
        list.add(datum);
        final BookingModelSpinnerAdapter[] customAdapter = {new BookingModelSpinnerAdapter(getContext(), list)};
        selectmodelspinner.setAdapter(customAdapter[0]);
        selectmodelspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectmodeltext.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String url = new Const().getBaseUrl() + "/api/transmissions/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new TaskRunner().executeAsync(new Callable<List<com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum>>() {
                    @Override
                    public List<com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum> call() {
                        Gson gson = new Gson();
                        BookingModelsSpinnerModel responsedata = gson.fromJson(response, BookingModelsSpinnerModel.class);
                        list.addAll(responsedata.getData());
                        return list;
                    }
                }, new TaskRunner.Callback<List<com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum>>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(List<com.tech.denso.Models.BookingsModel.BookingModelsSpinnerModel.Datum> result) {
                        Log.e("datumvaluecheclk", "" + result);
                        if (result.size() <= 1) {
                            nomodeltextview.setVisibility(View.VISIBLE);
                            selectmodelspinner.setVisibility(View.GONE);
                        } else {
                            nomodeltextview.setVisibility(View.GONE);
                            selectmodelspinner.setVisibility(View.VISIBLE);
                            customAdapter[0].notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
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

    private void ShowYears() {
        ArrayList<com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.Datum> list = new ArrayList();
        com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.Datum datum = new com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.Datum();
        datum.setModelYear("Year Model");
        list.add(datum);
        final BookingYearSpinnerAdapter[] customAdapter = {new BookingYearSpinnerAdapter(getContext(), list)};
        selectyearspinner.setAdapter(customAdapter[0]);
        selectyearspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectyeartext.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String url = new Const().getBaseUrl() + "/api/models/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new TaskRunner().executeAsync(new Callable<List<com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.Datum>>() {
                    @Override
                    public List<com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.Datum> call() {
                        Gson gson = new Gson();
                        BookingYearSpinnerModel responsedata = gson.fromJson(response, BookingYearSpinnerModel.class);
                        list.addAll(responsedata.getData());
                        return list;
                    }
                }, new TaskRunner.Callback<List<com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.Datum>>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(List<com.tech.denso.Models.BookingsModel.BookingYearSpinnerModel.Datum> result) {
                        Log.e("datumvaluecheclk", "" + result);
                        if (result.size() <= 1) {
                            noyeartextview.setVisibility(View.VISIBLE);
                            selectyearspinner.setVisibility(View.GONE);
                        } else {
                            noyeartextview.setVisibility(View.GONE);
                            selectyearspinner.setVisibility(View.VISIBLE);
                            customAdapter[0].notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
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

    private void ShowTimeSlots() {
        ArrayList<com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.Datum> list = new ArrayList();
        com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.Datum datum = new com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.Datum();
        datum.setTimeSlot("HH:MM");
        list.add(datum);
        final BookingTimeSpinnerAdapter[] customAdapter = {new BookingTimeSpinnerAdapter(getContext(), list)};
        selecttimeslotsspinner.setAdapter(customAdapter[0]);
        selecttimeslotsspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecttimeslottext.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String url = new Const().getBaseUrl() + "/api/timeslots/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("timeslotcheck", "" + response);
                new TaskRunner().executeAsync(new Callable<List<com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.Datum>>() {
                    @Override
                    public List<com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.Datum> call() {
                        Gson gson = new Gson();
                        BookingTimeSpinnerModel responsedata = gson.fromJson(response, BookingTimeSpinnerModel.class);
                        list.addAll(responsedata.getData());
                        return list;
                    }
                }, new TaskRunner.Callback<List<com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.Datum>>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(List<com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel.Datum> result) {
                        Log.e("timedatumvaluecheclk", "" + result);
                        if (result.size() <= 1) {
                            notimetextview.setVisibility(View.VISIBLE);
                            selecttimeslotsspinner.setVisibility(View.GONE);
                        } else {
                            notimetextview.setVisibility(View.GONE);
                            selecttimeslotsspinner.setVisibility(View.VISIBLE);
                            customAdapter[0].notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
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

    private void ShowPreferred() {
        ArrayList<String> list = new ArrayList();
        list.add("Select Preferred");
        list.add("Dropping Off Vehicle");
        list.add("Waiting");
        final BookingPreferredAndRequiredAdapter[] customAdapter = {new BookingPreferredAndRequiredAdapter(getContext(), list)};
        selectpreferredspinner.setAdapter(customAdapter[0]);
        selectpreferredspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectpreferredtext.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ShowRequiredServices() {
        ArrayList<String> list = new ArrayList();
        list.add("Select Required Service");
        list.add("A/C Inspection");
        list.add("A/C Repair");
        final BookingPreferredAndRequiredAdapter[] customAdapter = {new BookingPreferredAndRequiredAdapter(getContext(), list)};
        selectservicesspinner.setAdapter(customAdapter[0]);
        selectservicesspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectservicetext.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public Boolean isEditTextEmptyOrnot(EditText edittext) {
        if (edittext != null) {
            if (edittext.getText().toString().isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_fragment, container, false);
        ((DashboardActivity) getActivity()).setActivityListener(BookingFragment.this);
        selectmaketext = view.findViewById(R.id.selectmaketext);
        selectmodeltext = view.findViewById(R.id.selectmodeltext);
        selectyeartext = view.findViewById(R.id.selectyeartext);
        selectbranchtext = view.findViewById(R.id.selectbranchtext);
        selectpreferredtext = view.findViewById(R.id.selectpreferredtext);
        selectservicetext = view.findViewById(R.id.selectservicetext);
        selecttimeslottext = view.findViewById(R.id.selecttimeslottext);
        nobranchtextview = view.findViewById(R.id.nobranchtextview);
        nomaketextview = view.findViewById(R.id.nomaketextview);
        nomodeltextview = view.findViewById(R.id.nomodeltextview);
        noyeartextview = view.findViewById(R.id.noyeartextview);
        nopreferredtextview = view.findViewById(R.id.nopreferredtextview);
        noservicestextview = view.findViewById(R.id.noservicestextview);
        notimetextview = view.findViewById(R.id.notimetextview);
        selecttimeslotsspinner = view.findViewById(R.id.selecttimeslotsspinner);
        firsttexterror = view.findViewById(R.id.firsttexterror);
        lasttexterror = view.findViewById(R.id.lasttexterror);
        firstnameedittext = view.findViewById(R.id.firstnameedittext);
        lastnamedittext = view.findViewById(R.id.lastnameedittext);
        emailedittext = view.findViewById(R.id.emailedittext);
        phoneedittext = view.findViewById(R.id.phoneedittext);
        emailgreencheck = view.findViewById(R.id.emailgreencheck);
        emailredcheck = view.findViewById(R.id.emailredcheck);
        phonegreencheck = view.findViewById(R.id.phonegreencheck);
        phoneredcheck = view.findViewById(R.id.phoneredcheck);
        selectbranchspinner = view.findViewById(R.id.selectbranchspinner);
        selectmakespinner = view.findViewById(R.id.selectmakespinner);
        selectmodelspinner = view.findViewById(R.id.selectmodelspinner);
        selectyearspinner = view.findViewById(R.id.selectyearspinner);
        selectpreferredspinner = view.findViewById(R.id.selectpreferredspinner);
        selectservicesspinner = view.findViewById(R.id.selectservicesspinner);
        selectdatetextview = view.findViewById(R.id.selectdatetextview);

        firstnameview = view.findViewById(R.id.firstnameview);
        lastnameview = view.findViewById(R.id.lastnameview);
        emailview = view.findViewById(R.id.emailview);
        phoneview = view.findViewById(R.id.phoneview);
        selectdatetextview = view.findViewById(R.id.selectdatetextview);

        emailedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isEmailValid(emailedittext.getText().toString())) {
                    emailgreencheck.setVisibility(View.GONE);
                    emailredcheck.setVisibility(View.VISIBLE);
                } else {
                    emailgreencheck.setVisibility(View.VISIBLE);
                    emailredcheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phoneedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isPhoneNumberValid(phoneedittext.getText().toString())) {
                    phonegreencheck.setVisibility(View.GONE);
                    phoneredcheck.setVisibility(View.VISIBLE);
                } else {
                    phonegreencheck.setVisibility(View.VISIBLE);
                    phoneredcheck.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        firstnameedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    if (isFirstNameHavingSpaceOrNot(firstnameedittext.getText().toString())) {
                        firsttexterror.setVisibility(View.VISIBLE);
                        ChangeEdittextColor(firstnameview, Color.parseColor("#FE0025"));
                    } else {
                        firsttexterror.setVisibility(View.GONE);
                        ChangeEdittextColor(firstnameview, Color.parseColor("#00C149"));
                    }
                } else {
                    ChangeEdittextColor(firstnameview, Color.parseColor("#FE0025"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        lastnamedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    if (isFirstNameHavingSpaceOrNot(lastnamedittext.getText().toString())) {
                        lasttexterror.setVisibility(View.VISIBLE);
                        ChangeEdittextColor(lastnameview, Color.parseColor("#FE0025"));
                    } else {
                        lasttexterror.setVisibility(View.GONE);
                        ChangeEdittextColor(lastnameview, Color.parseColor("#00C149"));
                    }
                } else {
                    ChangeEdittextColor(lastnameview, Color.parseColor("#FE0025"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        selectdatetextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String finalyears = String.valueOf(year);
                        if (year < 10) {
                            finalyears = "0" + String.valueOf(year);
                        }
                        String finalmonth = String.valueOf(month + 1);
                        if (month < 10) {
                            finalmonth = "0" + String.valueOf(month + 1);
                        }
                        String finalday = String.valueOf(dayOfMonth);
                        if (dayOfMonth < 10) {
                            finalday = "0" + String.valueOf(dayOfMonth);
                        }
                        selectdatetextview.setText(finalday + "/" + finalmonth + "/" + finalyears);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        emailedittext.setText(new Const().getEmail());
        selectdatetextview.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        selectdatetextview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setAllEdittextFocusToFalse();
                selectdatetextview.setFocusable(true);
                return false;
            }
        });
        selectbranchspinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setAllEdittextFocusToFalse();
                selectbranchspinner.setFocusable(true);
                return false;
            }
        });
        ShowMakes();
        ShowTransmission();
        ShowYears();
        Showbranch();
        ShowPreferred();
        ShowRequiredServices();
        ShowTimeSlots();
        setFocusChangeListener(emailedittext);
        setFocusChangeListener(phoneedittext);
        setFocusChangeListener(firstnameedittext);
        setFocusChangeListener(lastnamedittext);
        setFocusChangeListener(emailedittext, emailview);
        setFocusChangeListener(phoneedittext, phoneview);
        setFocusChangeListener(firstnameedittext, firstnameview);
        setFocusChangeListener(lastnamedittext, lastnameview);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    View v = getActivity().getCurrentFocus();
                    if (v instanceof EditText) {
                        Rect outRect = new Rect();
                        v.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            v.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return true;
            }
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

    public void setFocusChangeListener(EditText edittext) {
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edittext.getBackground().setColorFilter(getResources().getColor(android.R.color.holo_red_light),
                            PorterDuff.Mode.SRC_ATOP);
                } else {
                    edittext.getBackground().setColorFilter(getResources().getColor(android.R.color.darker_gray),
                            PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
    }

    private void setAllEdittextFocusToFalse() {
        firstnameedittext.clearFocus();
        lastnamedittext.clearFocus();
        emailedittext.clearFocus();
        phoneedittext.clearFocus();
    }

    private void setAllEdittextClear() {
        firstnameedittext.setText("");
        lastnamedittext.setText("");
        emailedittext.setText("");
        phoneedittext.setText("");
        if (selectbranchspinner.getAdapter().getCount() > 0) {
            selectbranchspinner.setSelection(0);
        }
        selectmakespinner.setSelection(0);
        selectmodelspinner.setSelection(0);
        selectyearspinner.setSelection(0);
        selectpreferredspinner.setSelection(0);
        selectservicesspinner.setSelection(0);
        selecttimeslotsspinner.setSelection(0);
    }

    public void ChangeEdittextColor(View edittext, int color) {
        edittext.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isPhoneNumberValid(String number) {
        String phonestr = "^((?:[+?0?0?966]+)(?:\\s?\\d{2})(?:\\s?\\d{7}))$";
        return Pattern.compile(phonestr).matcher(number).matches();
    }

    boolean isFirstNameHavingSpaceOrNot(String firstname) {
        return CharMatcher.whitespace().matchesAnyOf(firstname);
    }
}