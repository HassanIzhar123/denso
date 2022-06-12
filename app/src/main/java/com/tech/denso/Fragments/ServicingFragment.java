package com.tech.denso.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.tech.denso.Activities.DashboardActivity;
import com.tech.denso.Adapter.ServicingViewAdapter;
import com.tech.denso.Helper.App;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.TaskRunner;
import com.tech.denso.Models.Services.ServicesModel;
import com.tech.denso.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ServicingFragment extends Fragment {
    private String title;
    private int page;
    RecyclerView servicingrecyclerview;
    RelativeLayout loadingrel;

    public static ServicingFragment newInstance(int page, String title) {
        ServicingFragment fragmentFirst = new ServicingFragment();
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
        View view = inflater.inflate(R.layout.servicing_fragment, container, false);
        DashboardActivity.titletextview.setText("DENSO SERVICES");
        servicingrecyclerview = view.findViewById(R.id.servicingrecyclerview);
        loadingrel = view.findViewById(R.id.loadingrel);
        DisplayServicingData();
        return view;
    }

    private void DisplayServicingData() {
        new TaskRunner().executeAsync(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                GetServicingData();
                return null;
            }
        }, new TaskRunner.Callback<Object>() {
            @Override
            public void onStart() {
                loadingrel.setVisibility(View.VISIBLE);
                servicingrecyclerview.setVisibility(View.GONE);
            }

            @Override
            public void onComplete(Object result) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingrel.setVisibility(View.GONE);
                        servicingrecyclerview.setVisibility(View.VISIBLE);
                    }
                }, 3000);
            }

            @Override
            public void onError(Exception e) {
                Log.e("Eceptionindialog", "" + e.toString());
                loadingrel.setVisibility(View.GONE);
                servicingrecyclerview.setVisibility(View.VISIBLE);
            }
        });
    }

    private void GetServicingData() {
        String url = new Const().getBaseUrl() + "/api/services/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.e("responsecheckvalue", "" + response);
                Gson gson = new Gson();
                ServicesModel responsedata = gson.fromJson(response, ServicesModel.class);
                if (responsedata.getData().size() > 0) {
                    ServicingViewAdapter adapter = new ServicingViewAdapter(getContext(), responsedata.getData());
                    servicingrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                    servicingrecyclerview.setAdapter(adapter);
                    loadingrel.setVisibility(View.GONE);
                    servicingrecyclerview.setVisibility(View.VISIBLE);
                } else {
                    loadingrel.setVisibility(View.GONE);
                    servicingrecyclerview.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("responsecheckvalue1", "" + url + " " + error.getMessage() + " " + Arrays.toString(error.getStackTrace()));
                loadingrel.setVisibility(View.GONE);
                servicingrecyclerview.setVisibility(View.GONE);
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
}