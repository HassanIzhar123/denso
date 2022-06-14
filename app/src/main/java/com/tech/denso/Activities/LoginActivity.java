package com.tech.denso.Activities;

import androidx.annotation.Nullable;
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
import com.tech.denso.Models.LoginModel.LoginModel;
import com.tech.denso.Models.SignUpModel.SignUpModel;
import com.tech.denso.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

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
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                if (getIntent() != null) {
                    Log.e("onsignupclciked","checked here");
                    i.putExtra("clickedonuser", getIntent().getBooleanExtra("clickedonuser", false));
                    finish();
                }
                startActivityForResult(i,1);
            }
        });
        forgotpasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    new TaskRunner().executeAsync(new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            Gson gson = new Gson();
                            LoginModel responsedata = gson.fromJson(response, LoginModel.class);
                            if (responsedata.getMessage() != null) {
                                Log.e("responsenamedata", "" + response);
                                if (responsedata.getMessage().equals("Login Successfully")) {
                                    new Const().setEmail(email);
                                    new Const().setPassword(password);
                                    new Const().setFirstname(responsedata.getCustomer().getFirstName());
                                    new Const().setLastname(responsedata.getCustomer().getLastName());
                                    new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("LoggedIn", true);
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.cancel();
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Firstname", (responsedata.getCustomer().getFirstName()));
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Lastname", (responsedata.getCustomer().getLastName()));
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Email", email);
                                            new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setString("Password", password);
                                            Boolean bol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("ShowIntro");
                                            if (!bol) {
                                                startActivity(new Intent(LoginActivity.this, IntroductionActivity.class));
                                            } else {
                                                Boolean bookingfromfragment = getIntent().getBooleanExtra("bookingfromfragment", false);
                                                if (bookingfromfragment) {
                                                    Intent intent = new Intent();
                                                    intent.putExtra("comingbackbooking", true);
                                                    intent.putExtra("bookingmodel", getIntent().getSerializableExtra("bookingmodel"));
                                                    setResult(RESULT_OK, intent);
                                                    finish();
                                                } else {
                                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                                }
                                            }
                                        }
                                    }, 3000);
                                } else if (responsedata.getMessage().equals("Customer is not registered..")) {
                                    Toast.makeText(getApplicationContext(), "" + responsedata.getMessage(), Toast.LENGTH_SHORT).show();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.cancel();
                                        }
                                    }, 3000);
                                } else if (responsedata.getMessage().equals("Password not matched")) {
                                    Toast.makeText(getApplicationContext(), "" + responsedata.getMessage(), Toast.LENGTH_SHORT).show();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.cancel();
                                        }
                                    }, 3000);
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
        }
    }
}