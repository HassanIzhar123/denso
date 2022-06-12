package com.tech.denso.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.Helper.TaskRunner;
import com.tech.denso.Models.SignUpModel.SignUpModel;
import com.tech.denso.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Callable;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signinbtn;
    Button signupbtn;
    EditText firstname_edittext, lastname_edittext, email_edittext, password_edittext, confirm_password_edittext;
    ImageButton showbtn, hidebtn, confirmshowbtn, confirmhidebtn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
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
                    new TaskRunner().executeAsync(new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            Gson gson = new Gson();
                            SignUpModel responsedata = gson.fromJson(response, SignUpModel.class);
                            if (responsedata.getMessage() != null) {
                                if (responsedata.getMessage().equals("New customer created!")) {
                                    new Const().setEmail(email);
                                    new Const().setPassword(password);
                                    new Const().setFirstname(firstname);
                                    new Const().setLastname(lastname);
                                    new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("LoggedIn", true);

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.cancel();
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Email", email);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Password", password);
                                            Boolean bol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("ShowIntro");
                                            if (!bol) {
                                                startActivity(new Intent(SignUpActivity.this, IntroductionActivity.class));
                                            } else {
                                                startActivity(new Intent(SignUpActivity.this, SucessfullSignupActivity.class));
                                            }
                                        }
                                    }, 3000);
                                } else if (responsedata.getMessage().equals("Customer already registered")) {
                                    Toast.makeText(getApplicationContext(), "" + responsedata.getMessage(), Toast.LENGTH_SHORT).show();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.cancel();
                                        }
                                    }, 1000);
                                }
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