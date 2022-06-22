package com.tech.denso.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.Interfaces.CallBackModel;
import com.tech.denso.Models.BookingsModel.BookingSendModel;
import com.tech.denso.Models.InitialWarrantyFragment.InitialWarrantyModel;
import com.tech.denso.Models.LoginModel.LoginModel;
import com.tech.denso.Models.SignUpModel.SignUpModel;
import com.tech.denso.Models.WarrantyClaim.WarrantyClaim;
import com.tech.denso.R;
import com.tech.denso.ViewModels.BookingModel;
import com.tech.denso.ViewModels.BookingViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import needle.Needle;
import needle.UiRelatedTask;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signinbtn;
    Button signupbtn;
    EditText firstname_edittext, lastname_edittext, email_edittext, password_edittext, confirm_password_edittext;
    ImageButton showbtn, hidebtn, confirmshowbtn, confirmhidebtn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CallBackModel.getInstance().onBackCallBack();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstname_edittext = findViewById(R.id.firstname_edittext);
        lastname_edittext = findViewById(R.id.lastname_edittext);
        email_edittext = findViewById(R.id.email_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        confirm_password_edittext = findViewById(R.id.confirm_password_edittext);
        signupbtn = findViewById(R.id.signupbtn);
        signinbtn = findViewById(R.id.signinbtn);
        showbtn = findViewById(R.id.showbtn);
        hidebtn = findViewById(R.id.hidebtn);
        confirmshowbtn = findViewById(R.id.confirmshowbtn);
        confirmhidebtn = findViewById(R.id.confirmhidebtn);
        showbtn.setOnClickListener(this);
        hidebtn.setOnClickListener(this);
        confirmshowbtn.setOnClickListener(this);
        confirmhidebtn.setOnClickListener(this);
        password_edittext.setTransformationMethod(new PasswordTransformationMethod());
        confirm_password_edittext.setTransformationMethod(new PasswordTransformationMethod());
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckEditTextEmptyOrNot(firstname_edittext)) {
                    SetError(firstname_edittext, "FirstName Field cannot be Empty!");
                }
                if (CheckEditTextEmptyOrNot(lastname_edittext)) {
                    SetError(lastname_edittext, "LastName Field cannot be Empty!");
                }
                if (CheckEditTextEmptyOrNot(email_edittext)) {
                    SetError(email_edittext, "Email Field cannot be Empty!");
                }
                if (!isEmailValid(email_edittext.getText().toString().trim().replaceAll(" ", ""))) {
                    SetError(email_edittext, "Email is not Correct!");
                }
                if (CheckEditTextEmptyOrNot(password_edittext)) {
                    SetError(password_edittext, "Password Field cannot be Empty!");
                }
                if (CheckEditTextEmptyOrNot(confirm_password_edittext)) {
                    SetError(confirm_password_edittext, "This Field cannot be Empty!");
                }
                if (!password_edittext.getText().toString().equals(confirm_password_edittext.getText().toString())) {
                    SetError(password_edittext, "Password is not Matching!");
                    SetError(confirm_password_edittext, "Password is not Matching!");
                }
                if (!CheckEditTextEmptyOrNot(firstname_edittext) && !CheckEditTextEmptyOrNot(lastname_edittext)
                        && !CheckEditTextEmptyOrNot(email_edittext) && isEmailValid(email_edittext.getText().toString().trim().replaceAll(" ", "")) && !CheckEditTextEmptyOrNot(password_edittext) && !CheckEditTextEmptyOrNot(confirm_password_edittext)
                        && password_edittext.getText().toString().equals(confirm_password_edittext.getText().toString())) {
                    SignUp(firstname_edittext.getText().toString().trim().replaceAll(" ", ""), lastname_edittext.getText().toString().trim().replaceAll(" ", ""),
                            email_edittext.getText().toString().trim().replaceAll(" ", ""), password_edittext.getText().toString());
                }
            }
        });
    }

    public String getCurrentUTCTime() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());
        return utcTime;
    }

    private void SignUp(String firstname, String lastname, String email, String password) {
        try {
            Dialog dialog = new Dialog(SignUpActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.loading_dialog);
            dialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL = new Const().getBaseUrl() + "/api/customers/";
            JSONObject jsonBody = new JSONObject();
            String currentutc = getCurrentUTCTime();
            jsonBody.put("create_date", currentutc);
            jsonBody.put("firstName", firstname);
            jsonBody.put("lastName", lastname);
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            jsonBody.put("type", "customer");
            jsonBody.put("__v", 0);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Needle.onBackgroundThread().execute(new UiRelatedTask<SignAsyncUpModel>() {
                        @Override
                        protected SignAsyncUpModel doWork() {
                            SignAsyncUpModel model = new SignAsyncUpModel();
                            Gson gson = new Gson();
                            SignUpModel responsedata = gson.fromJson(response, SignUpModel.class);
                            model.setSignUpModel(responsedata);
                            model.setComplete(true);
                            return model;
                        }

                        @Override
                        protected void thenDoUiRelatedWork(SignAsyncUpModel signAsyncUpModel) {
                            if (signAsyncUpModel.isComplete()) {
                                SignUpModel responsedata = signAsyncUpModel.getSignUpModel();
                                if (responsedata.getMessage() != null) {
                                    if (responsedata.getMessage().equals("New customer created!") || responsedata.getMessage().equals("Verify your email please")) {
                                        new Const().setEmail(email);
                                        new Const().setPassword(password);
                                        new Const().setFirstname(firstname);
                                        new Const().setLastname(lastname);
                                        new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("LoggedIn", true);
                                        dialog.cancel();
                                        if (getIntent().getBooleanExtra("clickedonuser", false)) {
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Email", email);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Password", password);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Firstname", firstname);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Lastname", lastname);
                                            BookingSendModel bookingmodel = (BookingSendModel) getIntent().getSerializableExtra("signupbookingmodel");
                                            SendBooking(bookingmodel.getFirstName(), bookingmodel.getLastName()
                                                    , bookingmodel.getEmail(), bookingmodel.getPhoneNumber(),
                                                    bookingmodel.getMake(), bookingmodel.getStatus(),
                                                    bookingmodel.getModel(), bookingmodel.getService(),
                                                    bookingmodel.getYear(), Boolean.valueOf(bookingmodel.getIsListed()),
                                                    bookingmodel.getBranch(), bookingmodel.getTimeSlot(), bookingmodel.getDate());
                                            finish();
                                        } else if (getIntent().getBooleanExtra("warrantyclickedonuser", false)) {
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Email", email);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Password", password);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Firstname", firstname);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Lastname", lastname);
                                            InitialWarrantyModel warrantymodel = (InitialWarrantyModel) getIntent().getSerializableExtra("signupwarrantymodel");
                                            try {
                                                SubmitClaimRequest(warrantymodel);
                                                finish();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Email", email);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Password", password);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Firstname", firstname);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Lastname", lastname);
                                            startActivity(new Intent(SignUpActivity.this, SucessfullSignupActivity.class));
//                                            Boolean bol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("ShowIntro");
//                                            if (!bol) {
//                                                startActivity(new Intent(SignUpActivity.this, IntroductionActivity.class));
//                                            } else {
//                                                startActivity(new Intent(SignUpActivity.this, SucessfullSignupActivity.class));
//                                            }
                                        }
                                    } else if (responsedata.getMessage().equals("Customer already registered")) {
                                        Toast.makeText(getApplicationContext(), "" + responsedata.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                }
                            } else {
                                if (dialog != null) {
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                            }
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEYERROR", error.toString());
                    Toast.makeText(getApplicationContext(), "Error Occured!", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("responsevaluechecl", "" + response);
                Gson gson = new Gson();
                WarrantyClaim responsedata = gson.fromJson(response.toString(), WarrantyClaim.class);
                if (responsedata.getMessage().equals("Warranty Claim has been Submitted Successfully!")) {
                    Intent i = new Intent(SignUpActivity.this, SucessfullClaimActivity.class);
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

    private void SendBooking(String firstname, String lastname, String email, String phonenumber, String vehicleType, String waitingStatus, String transmission,
                             String serviceDetails, String model, Boolean isListed, String branchName, String bookingTime, String bookingDate) {
        try {
            Dialog dialog = new Dialog(SignUpActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.loading_dialog);
            dialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
            final String requestBody = jsonBody.toString();
            Log.e("jsonbodychjeck", "" + requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    LoginModel responsedata = gson.fromJson(response, LoginModel.class);
                    if (dialog != null) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                            startActivity(new Intent(SignUpActivity.this, SuccessfulBookingActivity.class));
                            Boolean loggedbol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("LoggedIn");
                            if (loggedbol) {
                                BookingViewModel model = new ViewModelProvider(SignUpActivity.this).get(BookingViewModel.class);
                                BookingModel bookingmodel = new BookingModel();
                                bookingmodel.setLogOutVisibility(View.VISIBLE);
                                bookingmodel.setHistoryRelVisibility(View.VISIBLE);
                                bookingmodel.setLoginVisibility(View.GONE);
                                model.select(bookingmodel);
                            } else {
                                BookingViewModel model = new ViewModelProvider(SignUpActivity.this).get(BookingViewModel.class);
                                BookingModel bookingmodel = new BookingModel();
                                bookingmodel.setLogOutVisibility(View.GONE);
                                bookingmodel.setHistoryRelVisibility(View.GONE);
                                bookingmodel.setLoginVisibility(View.VISIBLE);
                                model.select(bookingmodel);
                            }
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEYERROR", error.toString());
                    Toast.makeText(getApplicationContext(), "Error Occured!", Toast.LENGTH_SHORT).show();
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

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void SetError(EditText firstname_edittext, String s) {
        firstname_edittext.setError(s);
    }

    private boolean CheckEditTextEmptyOrNot(EditText edittext) {
        if (edittext.getText() != null) {
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
    public void onClick(View v) {
        if (v == showbtn) {
            password_edittext.setTransformationMethod(null);
            showbtn.setVisibility(View.GONE);
            hidebtn.setVisibility(View.VISIBLE);
        } else if (v == hidebtn) {
            password_edittext.setTransformationMethod(new PasswordTransformationMethod());
            showbtn.setVisibility(View.VISIBLE);
            hidebtn.setVisibility(View.GONE);
        } else if (v == confirmshowbtn) {
            confirm_password_edittext.setTransformationMethod(null);
            confirmshowbtn.setVisibility(View.GONE);
            confirmhidebtn.setVisibility(View.VISIBLE);
        } else if (v == confirmhidebtn) {
            confirm_password_edittext.setTransformationMethod(new PasswordTransformationMethod());
            confirmshowbtn.setVisibility(View.VISIBLE);
            confirmhidebtn.setVisibility(View.GONE);
        }
    }
}