package com.tech.denso.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
//import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.tech.denso.Manifest;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.tech.denso.Adapter.CustomAdapter;
import com.tech.denso.Helper.App;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.CustomInfoWindowAdapter;
import com.tech.denso.Helper.TaskRunner;
import com.tech.denso.Models.Locations.LocationsModel;
import com.tech.denso.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class MapsFragment extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    Dialog dialog;
    RelativeLayout loadingrel;
    AppCompatSpinner branchspinner;
    ImageButton spinneropener;
    EditText search_branch_edittext;
    ArrayList<CameraUpdate> cameraarray = new ArrayList<>();
    boolean spinnerbol = false;
    List<com.tech.denso.Models.Locations.Datum> datumstemp = new ArrayList<>();
    ArrayList<com.tech.denso.Models.Locations.Datum> finalarr = new ArrayList<>();
    TextView nobranchtext;

    public static MapsFragment newInstance(int page, String title) {
        MapsFragment fragmentFirst = new MapsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        loadingrel = view.findViewById(R.id.loadingrel);
        branchspinner = view.findViewById(R.id.branchspinner);
        spinneropener = view.findViewById(R.id.spinneropener);
        nobranchtext = view.findViewById(R.id.nobranchtext);
        search_branch_edittext = view.findViewById(R.id.search_branch_edittext);
        mMapView = (MapView) view.findViewById(R.id.mapView);

        new TaskRunner().executeAsync(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                GetLocationData(savedInstanceState);
                return null;
            }
        }, new TaskRunner.Callback<Object>() {
            @Override
            public void onStart() {
                loadingrel.setVisibility(View.VISIBLE);
                mMapView.setVisibility(View.GONE);
            }

            @Override
            public void onComplete(Object result) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingrel.setVisibility(View.GONE);
                        mMapView.setVisibility(View.VISIBLE);
                    }
                }, 3000);
            }

            @Override
            public void onError(Exception e) {
                Log.e("Eceptionindialog", "" + e.toString());
                loadingrel.setVisibility(View.GONE);
                mMapView.setVisibility(View.VISIBLE);
            }
        });

        spinneropener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branchspinner.performClick();
            }
        });
        return view;
    }

    private void InitializeGoogleMaps(Bundle savedInstanceState, List<com.tech.denso.Models.Locations.Datum> responsedata) {
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                for (int i = 0; i < responsedata.size(); i++) {
                    LatLng pos = new LatLng(responsedata.get(i).getLatitude(), responsedata.get(i).getLongitude());
                    MarkerOptions marker = new MarkerOptions().position(pos).title("Marker Title").snippet("Marker Description");
                    googleMap.addMarker(marker).setTag(i);
                    if (i == 0) {
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }

                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext(), responsedata));
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mMapView != null) {
//            mMapView.onResume();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mMapView != null)
//            mMapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mMapView != null)
//            mMapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        if (mMapView != null)
//            mMapView.onLowMemory();
//    }

    private void GetLocationData(Bundle savedInstanceState) {
        String url = new Const().getBaseUrl() + "/api/locations/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                LocationsModel responsedata = gson.fromJson(response, LocationsModel.class);
                List<com.tech.denso.Models.Locations.Datum> datums = responsedata.getData();
//                List<com.tech.denso.Models.Locations.Datum> data = responsedata.getData();
//                for (int i = 0; i < data.size(); i++) {
//                    if (data.get(i).getEmail().equals(new Const().getEmail())) {
//                        datums.add(data.get(i));
//                    }
//                }
                datumstemp = datums;
                CustomAdapter[] customAdapter = null;
                if (datums.size() > 0) {
                    nobranchtext.setVisibility(View.GONE);
                    branchspinner.setVisibility(View.VISIBLE);
                    customAdapter = new CustomAdapter[]{new CustomAdapter(getContext(), datums)};
                    branchspinner.setAdapter(customAdapter[0]);
                    branchspinner.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            spinnerbol = true;
                            return false;
                        }
                    });
                } else {
                    nobranchtext.setVisibility(View.VISIBLE);
                    branchspinner.setVisibility(View.GONE);
                }
                InitializeGoogleMaps(savedInstanceState, datums);
                CustomAdapter[] finalCustomAdapter = customAdapter;
                search_branch_edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        finalarr.clear();
                        spinnerbol = true;
                        for (int i = 0; i < datumstemp.size(); i++) {
                            if (datumstemp.get(i).getAddress().toLowerCase().startsWith(s.toString().toLowerCase())) {
                                finalarr.add(datumstemp.get(i));
                            }
                        }
                        if (finalCustomAdapter != null) {
                            if (finalCustomAdapter.length > 0) {
                                nobranchtext.setVisibility(View.GONE);
                                branchspinner.setVisibility(View.VISIBLE);
                                finalCustomAdapter[0] = new CustomAdapter(getContext(), finalarr);
                                branchspinner.setAdapter(finalCustomAdapter[0]);
                                branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (spinnerbol) {
                                            if (googleMap != null) {
                                                if (search_branch_edittext.getText().toString().isEmpty()) {
                                                    LatLng pos = new LatLng(datums.get(position).getLatitude(), datums.get(position).getLongitude());
                                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
                                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                                } else {
                                                    LatLng pos = new LatLng(finalarr.get(position).getLatitude(), finalarr.get(position).getLongitude());
                                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
                                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                                }
                                            }
                                            spinnerbol = false;
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else {
                                nobranchtext.setVisibility(View.VISIBLE);
                                branchspinner.setVisibility(View.GONE);
                            }
                        } else {
                            nobranchtext.setVisibility(View.VISIBLE);
                            branchspinner.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (spinnerbol) {
                            if (googleMap != null) {
                                if (search_branch_edittext.getText().toString().isEmpty()) {
                                    LatLng pos = new LatLng(datums.get(position).getLatitude(), datums.get(position).getLongitude());
                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                } else {
                                    LatLng pos = new LatLng(finalarr.get(position).getLatitude(), finalarr.get(position).getLongitude());
                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                }
                            }
                            spinnerbol = false;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

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
//        queue.add(req);
        App.getInstance().addToRequestQueue(req, url);
    }
}