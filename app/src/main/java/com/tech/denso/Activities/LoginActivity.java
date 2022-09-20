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

import androidx.annotation.Nullable;
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
import com.tech.denso.Helper.Helper;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.Models.BookingLoginModel;
import com.tech.denso.Models.BookingsModel.BookingSendModel;
import com.tech.denso.Models.InitialWarrantyFragment.InitialWarrantyModel;
import com.tech.denso.Models.LoginModel.LoginModel;
import com.tech.denso.Models.WarrantyClaim.WarrantyClaim;
import com.tech.denso.R;
import com.tech.denso.ViewModels.BookingModel;
import com.tech.denso.ViewModels.BookingViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import needle.Needle;
import needle.UiRelatedTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signupbtn, forgotpasswordbtn;
    EditText email_edittext, password_edittext;
    Button signinbtn;
    ImageButton showbtn, hidebtn, confirmshowbtn, confirmhidebtn;

    @Override
    public void onBackPressed() {
        if (getIntent() != null) {
            Boolean check = getIntent().getBooleanExtra("clickedonuser", false);
            Boolean logout = getIntent().getBooleanExtra("clickedlogout", false);
            if (check) {
                Intent intent = new Intent();
                intent.putExtra("comingback", true);
                setResult(RESULT_OK, intent);
                finish();
            } else if (logout) {
                Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(i);
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotpasswordbtn = findViewById(R.id.forgotpasswordbtn);
        signupbtn = findViewById(R.id.signupbtn);
        signinbtn = findViewById(R.id.signinbtn);
        email_edittext = findViewById(R.id.email_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        showbtn = findViewById(R.id.showbtn);
        hidebtn = findViewById(R.id.hidebtn);
        confirmshowbtn = findViewById(R.id.confirmshowbtn);
        confirmhidebtn = findViewById(R.id.confirmhidebtn);
        showbtn.setOnClickListener(this);
        hidebtn.setOnClickListener(this);
        password_edittext.setTransformationMethod(new PasswordTransformationMethod());
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Helper().HideKeyboard(LoginActivity.this);
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                if (getIntent() != null) {
                    Log.e("onsignupclciked", "checked here");
                    if (getIntent().getSerializableExtra("bookingmodel") != null) {
                        i.putExtra("clickedonuser", getIntent().getBooleanExtra("bookingfromfragment", false));
                        i.putExtra("signupbookingmodel", getIntent().getSerializableExtra("bookingmodel"));
                    } else if (getIntent().getSerializableExtra("warrantymodel") != null) {
                        i.putExtra("warrantyclickedonuser", getIntent().getBooleanExtra("warrantyfromfragment", false));
                        i.putExtra("signupwarrantymodel", getIntent().getSerializableExtra("warrantymodel"));
                    } else {
                        i.putExtra("singleSignupActivity", "true");
                    }
                    startActivity(i);
//                    finish();
                }
            }
        });
        forgotpasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Helper().HideKeyboard(LoginActivity.this);
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Helper().HideKeyboard(LoginActivity.this);
                if (CheckEditTextEmptyOrNot(email_edittext)) {
                    SetError(email_edittext, "Email Field cannot be Empty!");
                }
                if (CheckEditTextEmptyOrNot(password_edittext)) {
                    SetError(password_edittext, "Password Field cannot be Empty!");
                }
                if (!isEmailValid(email_edittext.getText().toString().trim().replaceAll(" ", ""))) {
                    SetError(email_edittext, "Email is not Correct!");
                }
                if (!CheckEditTextEmptyOrNot(email_edittext) && !CheckEditTextEmptyOrNot(password_edittext) && isEmailValid(email_edittext.getText())) {
                    SignIn(email_edittext.getText().toString().trim().replaceAll(" ", ""), password_edittext.getText().toString().trim().replaceAll(" ", ""));
                }
            }
        });
    }

    private void SignIn(String email, String password) {
        try {
            Dialog dialog = new Dialog(LoginActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.loading_dialog);
            dialog.show();
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            String URL = new Const().getBaseUrl() + "/api/customers/login";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Needle.onBackgroundThread().execute(new UiRelatedTask<BookingLoginModel>() {
                        @Override
                        protected BookingLoginModel doWork() {
                            Gson gson = new Gson();
                            LoginModel responsedata = gson.fromJson(response, LoginModel.class);
                            Log.e("responsenamedata", "" + response);
                            BookingLoginModel model = new BookingLoginModel();
                            model.setResponseData(responsedata);
                            return model;
                        }

                        @Override
                        protected void thenDoUiRelatedWork(BookingLoginModel result) {
                            LoginModel responsedata = result.getResponseData();
                            if (responsedata.getMessage().equals("Login Successfully")) {
                                new Const().setEmail(email);
                                new Const().setPassword(password);
                                new Const().setFirstname(responsedata.getCustomer().getFirstName());
                                new Const().setLastname(responsedata.getCustomer().getLastName());
                                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("LoggedIn", true);
                                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Firstname", (responsedata.getCustomer().getFirstName()));
                                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Lastname", (responsedata.getCustomer().getLastName()));
                                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Email", email);
                                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Password", password);
                                Boolean bookingfromfragment = getIntent().getBooleanExtra("bookingfromfragment", false);
                                Boolean warrantyfromfragment = getIntent().getBooleanExtra("warrantyfromfragment", false);
                                if (bookingfromfragment) {
                                    BookingSendModel bookingmodel = (BookingSendModel) getIntent().getSerializableExtra("bookingmodel");
                                    SendBooking(bookingmodel.getEmail(),
                                            bookingmodel.getMake(), bookingmodel.getStatus(),
                                            bookingmodel.getModel(), bookingmodel.getService(),
                                            bookingmodel.getYear(), Boolean.valueOf(bookingmodel.getIsListed()),
                                            bookingmodel.getBranch(), bookingmodel.getTimeSlot(), bookingmodel.getDate());
                                    finish();
                                } else if (warrantyfromfragment) {
                                    InitialWarrantyModel warrantymodel = (InitialWarrantyModel) getIntent().getSerializableExtra("warrantymodel");
                                    try {
                                        SubmitClaimRequest(warrantymodel);
                                        finish();
                                    } catch (Exception e) {
                                        Log.e("", "");
                                    }

                                } else {
                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                }
                                dialog.cancel();
                            } else if (responsedata.getMessage().equals("Customer is not registered..")) {
                                Toast.makeText(getApplicationContext(), "" + responsedata.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            } else if (responsedata.getMessage().equals("Password not matched")) {
                                Toast.makeText(getApplicationContext(), "" + responsedata.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.cancel();
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
        jsonBody.put("vehicleName", item.getVehicleName());//
        jsonBody.put("vehicleModel", item.getVehicleModel());
        jsonBody.put("failureReason", item.getFailureReasonMessage());
        jsonBody.put("newPartNumber", item.getNewPartNumber());
        jsonBody.put("newPartName", item.getNewPartName());
        jsonBody.put("newSerialNumber", item.getNewSerialNumber());
        jsonBody.put("newPartInvoiceNumber", item.getNewPartInvoice());
        jsonBody.put("message", item.getComments());//
        jsonBody.put("email", new Const().getEmail());
        Log.e("finaljsonobkect", "" + jsonBody.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("responsevaluechecl", "" + response);
                Gson gson = new Gson();
                WarrantyClaim responsedata = gson.fromJson(response.toString(), WarrantyClaim.class);
                if (responsedata.getMessage().equals("Warranty Claim has been Submitted Successfully!")) {
                    Intent i = new Intent(LoginActivity.this, SucessfullClaimActivity.class);
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

    private void SendBooking(String email, String vehicleType, String waitingStatus, String transmission,
                             String serviceDetails, String model, Boolean isListed, String branchName, String bookingTime, String bookingDate) {
        try {
            Dialog dialog = new Dialog(LoginActivity.this);
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
//            jsonBody.put("firstName", firstname);
            jsonBody.put("isListed", String.valueOf(isListed));
//            jsonBody.put("lastName", lastname);
//            jsonBody.put("phoneNumber", phonenumber);
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
                            startActivity(new Intent(LoginActivity.this, SuccessfulBookingActivity.class));
                            Boolean loggedbol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("LoggedIn");
                            if (loggedbol) {
                                BookingViewModel model = new ViewModelProvider(LoginActivity.this).get(BookingViewModel.class);
                                BookingModel bookingmodel = new BookingModel();
                                bookingmodel.setLogOutVisibility(View.VISIBLE);
                                bookingmodel.setHistoryRelVisibility(View.VISIBLE);
                                bookingmodel.setLoginVisibility(View.GONE);
                                model.select(bookingmodel);
                            } else {
                                BookingViewModel model = new ViewModelProvider(LoginActivity.this).get(BookingViewModel.class);
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
        }
    }
}