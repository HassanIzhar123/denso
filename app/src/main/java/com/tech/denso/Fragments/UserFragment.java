package com.tech.denso.Fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tech.denso.Adapter.BookingHistoryViewAdapter;
import com.tech.App;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.Models.BookingsModel.BookingsModel;
import com.tech.denso.Models.BookingsModel.Datum;
import com.tech.denso.Models.ChangePassword.ChangePasswordModel;
import com.tech.denso.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import needle.Needle;
import needle.UiRelatedTask;

public class UserFragment extends Fragment implements View.OnClickListener {
    private String title;
    private int page;
    View view;
    SegmentedButtonGroup buttongroup;
    RecyclerView historyrecyclerview;
    RelativeLayout resetrel, nodatarel, loadingrel;
    Button updatebtn;
    EditText old_password_edittext, new_password_edittext, confirm_password_edittext;
    ImageButton showbtn, hidebtn, newshowbtn, newhidebtn, confirmshowbtn, confirmhidebtn;
    Boolean buttonbol = false, button1bol = false;
    SegmentedButton historybtn, resetpasswordbtn;
    BookingHistoryViewAdapter adapter;

    public static UserFragment newInstance(int page, String title) {
        UserFragment fragmentFirst = new UserFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, container, false);
        buttongroup = view.findViewById(R.id.buttongroup);
        historyrecyclerview = view.findViewById(R.id.historyrecyclerview);
        resetrel = view.findViewById(R.id.resetrel);
        nodatarel = view.findViewById(R.id.nodatarel);
        loadingrel = view.findViewById(R.id.loadingrel);
        updatebtn = view.findViewById(R.id.updatebtn);
        old_password_edittext = view.findViewById(R.id.old_password_edittext);
        new_password_edittext = view.findViewById(R.id.new_password_edittext);
        confirm_password_edittext = view.findViewById(R.id.confirm_password_edittext);
        showbtn = view.findViewById(R.id.showbtn);
        hidebtn = view.findViewById(R.id.hidebtn);
        newshowbtn = view.findViewById(R.id.newshowbtn);
        newhidebtn = view.findViewById(R.id.newhidebtn);
        confirmshowbtn = view.findViewById(R.id.confirmshowbtn);
        confirmhidebtn = view.findViewById(R.id.confirmhidebtn);
        showbtn.setOnClickListener(this);
        hidebtn.setOnClickListener(this);
        newshowbtn.setOnClickListener(this);
        newhidebtn.setOnClickListener(this);
        confirmshowbtn.setOnClickListener(this);
        confirmhidebtn.setOnClickListener(this);
        historybtn = view.findViewById(R.id.historybtn);
        resetpasswordbtn = view.findViewById(R.id.resetpasswordbtn);
        old_password_edittext.setTransformationMethod(new PasswordTransformationMethod());
        new_password_edittext.setTransformationMethod(new PasswordTransformationMethod());
        confirm_password_edittext.setTransformationMethod(new PasswordTransformationMethod());
        historybtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                buttonbol = true;
                return false;
            }

        });
        historybtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                button1bol = true;
                return false;
            }
        });
        buttongroup.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(int position) {
                historyrecyclerview.setVisibility(View.GONE);
                resetrel.setVisibility(View.GONE);
                nodatarel.setVisibility(View.GONE);
                if (position == 0) {
                    resetrel.setVisibility(View.GONE);
                    historyrecyclerview.setVisibility(View.VISIBLE);
                    nodatarel.setVisibility(View.GONE);
                    loadingrel.setVisibility(View.VISIBLE);
                    LoadData();
                } else if (position == 1) {
                    historyrecyclerview.setAdapter(null);
                    historyrecyclerview.setVisibility(View.GONE);
                    resetrel.setVisibility(View.VISIBLE);
                    nodatarel.setVisibility(View.GONE);
                }
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (old_password_edittext.getText().toString().isEmpty()) {
                    old_password_edittext.setError("Please enter old password!");
                }
                if (!old_password_edittext.getText().toString().equals(new Const().getPassword())) {
                    old_password_edittext.setError("Password is not Correct!");
                }
                if (new_password_edittext.getText().toString().isEmpty()) {
                    new_password_edittext.setError("please enter new password!");
                }
                if (!new_password_edittext.getText().toString().equals(confirm_password_edittext.getText().toString())) {
                    new_password_edittext.setError("Password doesn't Match!");
                    confirm_password_edittext.setError("Password doesn't Match!");
                }
                if (!old_password_edittext.getText().toString().isEmpty() && old_password_edittext.getText().toString().equals(new Const().getPassword())
                        && !new_password_edittext.getText().toString().isEmpty() && new_password_edittext.getText().toString().equals(confirm_password_edittext.getText().toString())) {
                    ChnagePassword(new Const().getEmail(), old_password_edittext.getText().toString(), new_password_edittext.getText().toString());
                    old_password_edittext.getText().clear();
                    new_password_edittext.getText().clear();
                    confirm_password_edittext.getText().clear();
                }
            }
        });
        if (buttongroup.getPosition() == 0) {
            LoadData();
        }

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(getContext(), "OnResume", Toast.LENGTH_SHORT).show();
//        if (buttongroup.getPosition() == 0) {
//            resetrel.setVisibility(View.GONE);
//            historyrecyclerview.setVisibility(View.VISIBLE);
//            nodatarel.setVisibility(View.GONE);
//        } else if (buttongroup.getPosition() == 1) {
//            resetrel.setVisibility(View.VISIBLE);
//            historyrecyclerview.setVisibility(View.GONE);
//            nodatarel.setVisibility(View.GONE);
//        }
//    }

    private void ChnagePassword(String email, String oldpassword, String newpassword) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            String URL = new Const().getBaseUrl() + "/api/customers/changepassword/";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", oldpassword);
            jsonBody.put("newPassword", newpassword);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("responsevaluechech", "" + response);
                    Gson gson = new Gson();
                    ChangePasswordModel responsedata = gson.fromJson(response, ChangePasswordModel.class);
                    if (responsedata.getMessage() != null) {
                        if (responsedata.getMessage().equals("Customer is not registered..")) {
                            Toast.makeText(getContext(), "" + responsedata.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (responsedata.getMessage().equals("Password not matched")) {
                            Toast.makeText(getContext(), "" + responsedata.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (responsedata.getMessage().equals("Password updated Successfully")) {
                            new SharedPreference(getContext(), getContext().toString()).setString("Password", newpassword);
                            new Const().setPassword(newpassword);
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(true);
                            dialog.setContentView(R.layout.change_password_success_dialog);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            Button dialogButton = (Button) dialog.findViewById(R.id.okbtn);
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEYERROR", error.toString());
                    Toast.makeText(getContext(), "Error Occured!", Toast.LENGTH_SHORT).show();
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

    private void LoadData() {
        Log.e("buttongroupvalue", "inside loaddata");
        String url = new Const().getBaseUrl() + "/api/bookings/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Needle.onBackgroundThread().execute(new UiRelatedTask<HistoryAsyncModel>() {
                    @Override
                    protected HistoryAsyncModel doWork() {
                        HistoryAsyncModel model = new HistoryAsyncModel();
                        if (buttongroup.getPosition() == 0) {
                            Gson gson = new Gson();
                            BookingsModel responsedata = gson.fromJson(response, BookingsModel.class);
                            if (responsedata.getData().size() > 0) {
                                List<Datum> datums = new ArrayList<>();
                                List<Datum> data = responsedata.getData();
                                for (int i = 0; i < data.size(); i++) {
                                    if (new Const().getEmail() != null) {
                                        if (data.get(i).getEmail() != null && data.get(i).getEmail().equals(new Const().getEmail())) {
                                            datums.add(data.get(i));
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                Log.e("datumarraycheck", "" + datums.size());
                                if (datums.size() > 0) {
                                    adapter = new BookingHistoryViewAdapter(getContext(), datums);
                                    model.setComplete(true);
                                    model.setAdapter(adapter);
                                } else {
                                    model.setComplete(false);
                                    model.setNoData(true);
                                }
                            } else {
                                model.setComplete(false);
                                model.setNoData(true);
                            }
                        } else {
                            model.setComplete(false);
                            model.setResetPassword(true);
                        }
                        return model;
                    }

                    @Override
                    protected void thenDoUiRelatedWork(HistoryAsyncModel result) {
                        if (result.isComplete) {
                            historyrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                            historyrecyclerview.setAdapter(result.getAdapter());
                            loadingrel.setVisibility(View.GONE);
                            historyrecyclerview.setVisibility(View.VISIBLE);
                            nodatarel.setVisibility(View.GONE);
                        } else if (!result.isComplete()) {
                            if (result.isNoData()) {
                                loadingrel.setVisibility(View.GONE);
                                historyrecyclerview.setVisibility(View.GONE);
                                nodatarel.setVisibility(View.VISIBLE);
                            } else if (result.isResetPassword()) {
                                nodatarel.setVisibility(View.GONE);
                                loadingrel.setVisibility(View.GONE);
                            }
                        }
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingrel.setVisibility(View.GONE);
                historyrecyclerview.setVisibility(View.GONE);
                nodatarel.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                Const constant = new Const();
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

    private void GetBookingData() {
        String url = new Const().getBaseUrl() + "/api/bookings/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                BookingsModel responsedata = gson.fromJson(response, BookingsModel.class);
                if (responsedata.getData().size() > 0) {
                    List<Datum> datums = new ArrayList<>();
                    List<Datum> data = responsedata.getData();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getEmail().equals(new Const().getEmail())) {
                            datums.add(data.get(i));
                        }
                    }
                    Log.e("datumarraycheck", "" + datums.size());
                    if (datums.size() > 0) {
                        BookingHistoryViewAdapter adapter = new BookingHistoryViewAdapter(getContext(), datums);
                        historyrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                        historyrecyclerview.setAdapter(adapter);
                        loadingrel.setVisibility(View.GONE);
                        historyrecyclerview.setVisibility(View.VISIBLE);
                        nodatarel.setVisibility(View.GONE);
                    } else {
                        loadingrel.setVisibility(View.GONE);
                        historyrecyclerview.setVisibility(View.GONE);
                        nodatarel.setVisibility(View.VISIBLE);
                    }
                } else {
                    loadingrel.setVisibility(View.GONE);
                    historyrecyclerview.setVisibility(View.GONE);
                    nodatarel.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingrel.setVisibility(View.GONE);
                historyrecyclerview.setVisibility(View.GONE);
                nodatarel.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                Const constant = new Const();
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

    @Override
    public void onClick(View v) {
        if (v == showbtn) {
            old_password_edittext.setTransformationMethod(null);
            showbtn.setVisibility(View.GONE);
            hidebtn.setVisibility(View.VISIBLE);
        } else if (v == hidebtn) {
            old_password_edittext.setTransformationMethod(new PasswordTransformationMethod());
            showbtn.setVisibility(View.VISIBLE);
            hidebtn.setVisibility(View.GONE);
        } else if (v == newshowbtn) {
            new_password_edittext.setTransformationMethod(null);
            newshowbtn.setVisibility(View.GONE);
            newhidebtn.setVisibility(View.VISIBLE);
        } else if (v == newhidebtn) {
            new_password_edittext.setTransformationMethod(new PasswordTransformationMethod());
            newshowbtn.setVisibility(View.VISIBLE);
            newhidebtn.setVisibility(View.GONE);
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