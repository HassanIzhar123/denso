package com.tech.denso.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
import com.tech.denso.Adapter.BookingHistoryViewAdapter;
import com.tech.denso.Adapter.ServicingViewAdapter;
import com.tech.denso.Helper.App;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.TaskRunner;
import com.tech.denso.Models.BookingsModel.BookingsModel;
import com.tech.denso.Models.Services.Datum;
import com.tech.denso.Models.Services.ServicesModel;
import com.tech.denso.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import needle.Needle;
import needle.UiRelatedTask;

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
        loadingrel.setVisibility(View.VISIBLE);
        servicingrecyclerview.setVisibility(View.GONE);
        String url = new Const().getBaseUrl() + "/api/services/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Needle.onBackgroundThread().execute(new UiRelatedTask<ServicingAsyncModel>() {
                    @Override
                    protected ServicingAsyncModel doWork() {
                        ServicingAsyncModel model = new ServicingAsyncModel();
                        Gson gson = new Gson();
                        ServicesModel responsedata = gson.fromJson(response, ServicesModel.class);
                        List<Datum> list = responsedata.getData();
                        List<Datum> filteredlist = new ArrayList<>();
                        List<String> stringTemp1 = new ArrayList<>();
                        List<Datum> mList1 = new ArrayList<>();
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            String catName = list.get(i).getServiceName();
                            String subCatName = list.get(i).getServiceCategory();
                            String id = list.get(i).getId();
                            int v = list.get(i).getV();
                            String createdate = list.get(i).getCreateDate();
                            if (stringTemp1.isEmpty() || !stringTemp1.contains(catName)) {
                                stringTemp1.add(catName);
                                mList1.add(new Datum(id, createdate, catName, "//" + subCatName + "\n//", v));
                            } else {
                                int index = stringTemp1.indexOf(catName);
                                String text = mList1.get(index).getServiceCategory();
                                text = "\n" + text + subCatName + "\n//";
                                text = text.trim();
                                mList1.set(index, new Datum(id, createdate, catName, text, v));
                            }
                        }
                        for (int i = 0; i < mList1.size(); i++) {
                            String category = mList1.get(i).getServiceCategory();
                            category = category.replaceFirst("//$", "");
                            mList1.get(i).setServiceCategory(category);
                        }
                        model.setList(mList1);
                        model.setComplete(true);
                        return model;
                    }

                    @Override
                    protected void thenDoUiRelatedWork(ServicingAsyncModel result) {
                        List<Datum> list = result.getList();
                        if (list.size() > 0) {
                            ServicingViewAdapter adapter = new ServicingViewAdapter(getContext(), list);
                            servicingrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                            servicingrecyclerview.setAdapter(adapter);
                            adapter.setClickListener(new ServicingViewAdapter.ItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    ((DashboardActivity) getActivity()).OpenPage(0);
                                }
                            });
                            loadingrel.setVisibility(View.GONE);
                            servicingrecyclerview.setVisibility(View.VISIBLE);
                        } else {
                            loadingrel.setVisibility(View.GONE);
                            servicingrecyclerview.setVisibility(View.GONE);
                        }
                    }
                });

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

//        new TaskRunner().executeAsync(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                GetServicingData();
//                return null;
//            }
//        }, new TaskRunner.Callback<Object>() {
//            @Override
//            public void onStart() {
//                loadingrel.setVisibility(View.VISIBLE);
//                servicingrecyclerview.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onComplete(Object result) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadingrel.setVisibility(View.GONE);
//                        servicingrecyclerview.setVisibility(View.VISIBLE);
//                    }
//                }, 3000);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.e("Eceptionindialog", "" + e.toString());
//                loadingrel.setVisibility(View.GONE);
//                servicingrecyclerview.setVisibility(View.VISIBLE);
//            }
//        });
    }

    public String getColoredString(String text) {
        String htmlText = text.replace(text, "<font color='#c5c5c5'>" + text + "</font>");

        return htmlText;
    }

    private void GetServicingData() {
        String url = new Const().getBaseUrl() + "/api/services/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ServicesModel responsedata = gson.fromJson(response, ServicesModel.class);
                List<Datum> list = responsedata.getData();
                if (list.size() > 0) {
                    ServicingViewAdapter adapter = new ServicingViewAdapter(getContext(), list);
                    servicingrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                    servicingrecyclerview.setAdapter(adapter);
                    adapter.setClickListener(new ServicingViewAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            ((DashboardActivity) getActivity()).OpenPage(0);
                        }
                    });
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