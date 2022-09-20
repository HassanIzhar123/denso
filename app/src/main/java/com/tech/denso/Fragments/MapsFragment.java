package com.tech.denso.Fragments;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.tech.denso.Activities.DashboardActivity;
import com.tech.denso.Adapter.CustomAdapter;
import com.tech.App;
import com.tech.denso.Helper.Const;
import com.tech.denso.Models.Locations.Datum;
import com.tech.denso.Models.Locations.LocationsModel;
import com.tech.denso.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import needle.Needle;
import needle.UiRelatedTask;

public class MapsFragment extends Fragment {
    //    MapView mapView;
//    private GoogleMap googleMap;
    Dialog dialog;
    RelativeLayout loadingrel;
    AppCompatSpinner branchspinner;
    ImageButton spinneropener;
    EditText search_branch_edittext;
    //    ArrayList<CameraUpdate> cameraarray = new ArrayList<>();
    boolean spinnerbol = false;
    List<com.tech.denso.Models.Locations.Datum> datumstemp = new ArrayList<>();
    ArrayList<com.tech.denso.Models.Locations.Datum> finalarr = new ArrayList<>();
    TextView nobranchtext;
    CardView whatsappbtn;
    MapView mapView;
    MarkerViewManager markerViewManager;
    MapboxMap mapBox;

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
        String mapBoxToken = "pk.eyJ1IjoiZGphdXRvIiwiYSI6ImNsNmRhYWNxMzBjdXgzZ292OW9iM2cxdnIifQ.t5zv8DMKl_6hpFMWckqUvQ";
        Mapbox.getInstance(requireActivity(), mapBoxToken);
        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        whatsappbtn = view.findViewById(R.id.whatsappbtn);
        loadingrel = view.findViewById(R.id.loadingrel);
        branchspinner = view.findViewById(R.id.branchspinner);
        spinneropener = view.findViewById(R.id.spinneropener);
        nobranchtext = view.findViewById(R.id.nobranchtext);
        search_branch_edittext = view.findViewById(R.id.search_branch_edittext);
//        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView = (com.mapbox.mapboxsdk.maps.MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        loadingrel.setVisibility(View.VISIBLE);
        mapView.setVisibility(View.GONE);
        String url = new Const().getBaseUrl() + "/api/locations/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Needle.onBackgroundThread().execute(new UiRelatedTask<MapAsyncModel>() {
                    @Override
                    protected MapAsyncModel doWork() {
                        Log.e("responsedata", "" + response);
                        MapAsyncModel model = new MapAsyncModel();
                        Gson gson = new Gson();
                        LocationsModel responsedata = gson.fromJson(response, LocationsModel.class);
                        List<com.tech.denso.Models.Locations.Datum> datums = responsedata.getData();
                        datumstemp = datums;
                        CustomAdapter[] customAdapter = null;
                        if (datums.size() > 0) {
                            customAdapter = new CustomAdapter[]{new CustomAdapter(getContext(), datums)};
                            model.setComplete(true);
                            model.setAdapter(customAdapter[0]);
                            model.setDatums(datums);
                        } else {
                            model.setComplete(false);
                            model.setOnNoData(true);
                        }

                        return model;
                    }

                    @Override
                    protected void thenDoUiRelatedWork(MapAsyncModel result) {
                        if (result.isComplete()) {
                            InitializeGoogleMaps(savedInstanceState, result.getDatums());
                            nobranchtext.setVisibility(View.GONE);
                            branchspinner.setVisibility(View.VISIBLE);
                            loadingrel.setVisibility(View.GONE);
                            mapView.setVisibility(View.VISIBLE);
                            branchspinner.setAdapter(result.getAdapter());
                            branchspinner.setDropDownWidth(branchspinner.getWidth());
                            List<Datum> datums = result.getDatums();
                            branchspinner.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    spinnerbol = true;
                                    return false;
                                }
                            });
                            search_branch_edittext.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    finalarr.clear();
                                    spinnerbol = true;
                                    for (int i = 0; i < datumstemp.size(); i++) {
                                        if (datumstemp.get(i).getBranchName().toLowerCase().contains(s.toString().toLowerCase())
                                                && datumstemp.get(i).getBranchName().toLowerCase().startsWith(s.toString().toLowerCase())) {
                                            finalarr.add(datumstemp.get(i));
                                        }
                                    }
                                    CustomAdapter finalCustomAdapter = result.getAdapter();

                                    if (finalarr.size() != 0 && datums != null) {
                                        if (datums.size() > 0) {
                                            nobranchtext.setVisibility(View.GONE);
                                            branchspinner.setVisibility(View.VISIBLE);
                                            finalCustomAdapter = new CustomAdapter(getContext(), finalarr);
                                            branchspinner.setAdapter(finalCustomAdapter);
                                            branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    if (spinnerbol) {
                                                        LatLng pos = new LatLng(datums.get(position).getLatitude(), datums.get(position).getLongitude());
                                                        mapBox.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15f));
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
                                        LatLng pos = new LatLng(datums.get(position).getLatitude(), datums.get(position).getLongitude());
                                        mapBox.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15f));
                                        spinnerbol = false;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else if (result.isOnNoData()) {
                            nobranchtext.setVisibility(View.VISIBLE);
                            branchspinner.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingrel.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
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
//        new TaskRunner().executeAsync(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
//                GetLocationData(savedInstanceState);
//                return null;
//            }
//        }, new TaskRunner.Callback<Object>() {
//            @Override
//            public void onStart() {
//                loadingrel.setVisibility(View.VISIBLE);
//                mapView.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onComplete(Object result) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadingrel.setVisibility(View.GONE);
//                        mapView.setVisibility(View.VISIBLE);
//                    }
//                }, 3000);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.e("Eceptionindialog", "" + e.toString());
//                loadingrel.setVisibility(View.GONE);
//                mapView.setVisibility(View.VISIBLE);
//            }
//        });

        spinneropener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branchspinner.performClick();
            }
        });
        whatsappbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = "+966553023135"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = getContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getContext(), "Whatsapp app is not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (markerViewManager != null) {
            markerViewManager.onDestroy();
        }
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    private void InitializeGoogleMaps(Bundle savedInstanceState, List<com.tech.denso.Models.Locations.Datum> responsedata) {
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume(); // needed to get the map to display immediately
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mapView.getMapAsync(new com.mapbox.mapboxsdk.maps.OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapBox = mapboxMap;
                mapBox.getUiSettings().setAttributionEnabled(false);
                mapBox.getUiSettings().setLogoEnabled(false);
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Create and customize the LocationComponent's options
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(getContext())
                                .trackingGesturesManagement(true)
                                .accuracyColor(ContextCompat.getColor(getContext(), R.color.black))
                                .build();
                        LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions.builder(getContext(), style)
                                .locationComponentOptions(customLocationComponentOptions)
                                .build();
                        LocationComponent component = mapboxMap.getLocationComponent();
                        component.activateLocationComponent(locationComponentActivationOptions);

                        component.setLocationComponentEnabled(true);
                        component.setCameraMode(CameraMode.TRACKING);
                        component.setRenderMode(RenderMode.COMPASS);
                        for (int i = 0; i < responsedata.size(); i++) {
                            IconFactory iconFactory = IconFactory.getInstance(getContext());
                            Icon icon = iconFactory.fromResource(R.drawable.map_marker);
                            if (i == 0) {
                                mapboxMap.setCameraPosition(new com.mapbox.mapboxsdk.camera.CameraPosition.Builder()
                                        .target(new com.mapbox.mapboxsdk.geometry.LatLng(responsedata.get(i).getLatitude(), responsedata.get(i).getLongitude()))
                                        .zoom(12.0)
                                        .build());
                            }
                            mapboxMap.addMarker(new com.mapbox.mapboxsdk.annotations.MarkerOptions()
                                    .position(new com.mapbox.mapboxsdk.geometry.LatLng(responsedata.get(i).getLatitude(), responsedata.get(i).getLongitude()))
                                    .setSnippet(String.valueOf(i))
                                    .setIcon(icon)
                                    .title("Marker title"));
                        }
                    }
                });
//                mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
//                    @Nullable
//                    @org.jetbrains.annotations.Nullable
//                    @Override
//                    public View getInfoWindow(@NonNull @NotNull Marker marker) {
//                        markerViewManager = new MarkerViewManager(mapView, mapboxMap);
//                        View customView = LayoutInflater.from(getContext()).inflate(R.layout.mapbox_marker_layout, null);
//                        customView.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
//                        TextView factory_name = customView.findViewById(R.id.factory_name);
//                        TextView country_name = customView.findViewById(R.id.country_name);
//                        ImageButton next = customView.findViewById(R.id.togotonext);
//                        int position = Integer.parseInt(marker.getSnippet());
//                        factory_name.setText(factoriesconfigurations.get(position).getFactoryName());
//                        country_name.setText(factoriesconfigurations.get(position).getLocation());
//                        next.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                String url = comparefactories.get(position).getUrl();
//                                String[] urlsplit = url.split("^https?://");
//                                String domain = urlsplit[1];
//                                urlsplit[1] = "tt" + urlsplit[1];
//                                url = "https://" + urlsplit[1];
//                                url = url.replace(".thingtrax", "apibeta.thingtrax");
//                                BASE_URL = url;
//                                Const.Factory = String.valueOf(factoriesconfigurations.get(position).getFactoryId());
//                                Log.e("urclcheckval", "" + url + " " + Const.Factory/*+" "+ Arrays.toString(remainingspit)*/);
//                                SilentLoginAndOpen(domain, url);
//                            }
//                        });
//                        return customView;
//                    }
//                });
                mapBox.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
                    @Nullable
                    @org.jetbrains.annotations.Nullable
                    @Override
                    public View getInfoWindow(@NonNull @NotNull Marker marker) {
                        markerViewManager = new MarkerViewManager(mapView, mapBox);
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.infowindowlayout, null);
                        view.setLayoutParams(new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                        int position = Integer.parseInt(marker.getSnippet());
                        String title = marker.getTitle();
                        TextView cityName = (TextView) view.findViewById(R.id.cityname);
                        TextView mapText = (TextView) view.findViewById(R.id.maptext);
                        TextView timetext = (TextView) view.findViewById(R.id.timetext);
                        TextView calltext = (TextView) view.findViewById(R.id.calltext);
                        RelativeLayout bookingbtn = (RelativeLayout) view.findViewById(R.id.bookingbtn);
                        RelativeLayout emailrel = (RelativeLayout) view.findViewById(R.id.emailrel);
                        RelativeLayout directionbtn = (RelativeLayout) view.findViewById(R.id.directionbtn);
                        ImageButton exitbtn = (ImageButton) view.findViewById(R.id.exitbtn);
                        cityName.setText(responsedata.get(position).getBranchName());
                        mapText.setText(responsedata.get(position).getAddress());
                        timetext.setText(getContext().getResources().getString(R.string.openingtime) + responsedata.get(position).getOpeningSaturday() + getContext().getResources().getString(R.string.to) + responsedata.get(position).getTillThursday());
                        calltext.setText(String.valueOf(responsedata.get(position).getPhoneNumber()));
                        bookingbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mapboxMap.getMarkers().get(position).hideInfoWindow();
                                ((DashboardActivity) getActivity()).OpenPage(0);
                                BookingFragment.selectBranchSpinner(responsedata.get(position).getId());
                            }
                        });
                        emailrel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mapboxMap.getMarkers().get(position).hideInfoWindow();
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_EMAIL, new String[]{responsedata.get(position).getEmail()});
//                                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//                                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                                try {
                                    startActivity(Intent.createChooser(i, "Send mail..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                }
//                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                                emailIntent.putExtra(Intent.CATEGORY_APP_EMAIL, responsedata.get(position).getEmail());
//                                emailIntent.putExtra(Intent.EXTRA_EMAIL, responsedata.get(position).getEmail());
//                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
//                                emailIntent.setType("text/plain");
//                                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "content");
//                                final PackageManager pm = getContext().getPackageManager();
//                                final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
//                                ResolveInfo best = null;
//                                for (final ResolveInfo info : matches) {
//                                    Log.e("gmailpackagename", "" + info.activityInfo.name.toLowerCase());
//                                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
//                                        best = info;
//                                }
//                                if (best != null) {
//                                    emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
//                                }
//                                getContext().startActivity(emailIntent);
//                                String[] TO = {""};
//                                String[] CC = {""};
//                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                                emailIntent.setData(Uri.parse("mailto:"+responsedata.get(position).getEmail()));
//                                emailIntent.setType("text/plain");
//                                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//                                emailIntent.putExtra(Intent.EXTRA_CC, CC);
//                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
//                                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
//                                try {
//                                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                                    Log.i("FinishedSendingEmail", "here");
//                                } catch (android.content.ActivityNotFoundException ex) {
//                                    Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                                }
                            }
                        });
                        directionbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mapboxMap.getMarkers().get(position).hideInfoWindow();
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    Toast.makeText(getContext(), "Please enable location permission!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    Location lastKnownLocation = mapboxMap.getLocationComponent().getLastKnownLocation();
                                    if (lastKnownLocation != null) {
                                        double lastLatitude = lastKnownLocation.getLatitude();
                                        double lastLongitude = lastKnownLocation.getLongitude();
                                        String uri = "http://maps.google.com/maps?saddr=" + lastLatitude + "," + lastLongitude + "&daddr=" + responsedata.get(position).getLongitude() + "," + responsedata.get(position).getLatitude();
                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                        getContext().startActivity(intent);
                                    }
                                }
                            }
                        });
                        exitbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mapboxMap.getMarkers().get(position).hideInfoWindow();
                            }
                        });
                        return view;
                    }
                });
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                googleMap = mMap;
//                // For showing a move to my location button
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                googleMap.setMyLocationEnabled(true);
//                for (int i = 0; i < responsedata.size(); i++) {
//                    LatLng pos = new LatLng(responsedata.get(i).getLatitude(), responsedata.get(i).getLongitude());
//                    MarkerOptions marker = new MarkerOptions().position(pos).title("Marker Title").snippet("Marker Description");
//                    googleMap.addMarker(marker).setTag(i);
//                    if (i == 0) {
//                        CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
//                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                    }
//                }
//                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext(), responsedata));
//            }
//        });
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mapView != null) {
//            mapView.onResume();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mapView != null)
//            mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mapView != null)
//            mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        if (mapView != null)
//            mapView.onLowMemory();
//    }

    private void GetLocationData(Bundle savedInstanceState) {
        String url = new Const().getBaseUrl() + "/api/locations/";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("responsedata", "" + response);
                Gson gson = new Gson();
                LocationsModel responsedata = gson.fromJson(response, LocationsModel.class);
                List<com.tech.denso.Models.Locations.Datum> datums = responsedata.getData();
                datumstemp = datums;
                CustomAdapter[] customAdapter = null;
                if (datums.size() > 0) {
                    nobranchtext.setVisibility(View.GONE);
                    branchspinner.setVisibility(View.VISIBLE);
                    customAdapter = new CustomAdapter[]{new CustomAdapter(getContext(), datums)};
                    branchspinner.setAdapter(customAdapter[0]);
                    branchspinner.setDropDownWidth(branchspinner.getWidth());
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
                            if (datumstemp.get(i).getBranchName().toLowerCase().contains(s.toString().toLowerCase())
                                    && datumstemp.get(i).getBranchName().toLowerCase().startsWith(s.toString().toLowerCase())) {
                                finalarr.add(datumstemp.get(i));
                            }
                        }
                        if (finalarr.size() != 0 && finalCustomAdapter != null) {
                            if (finalCustomAdapter.length > 0) {
                                nobranchtext.setVisibility(View.GONE);
                                branchspinner.setVisibility(View.VISIBLE);
                                finalCustomAdapter[0] = new CustomAdapter(getContext(), finalarr);
                                branchspinner.setAdapter(finalCustomAdapter[0]);
                                branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (spinnerbol) {
//                                            if (googleMap != null) {
//                                                if (search_branch_edittext.getText().toString().isEmpty()) {
//                                                    LatLng pos = new LatLng(datums.get(position).getLatitude(), datums.get(position).getLongitude());
//                                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
//                                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                                                } else {
//                                                    LatLng pos = new LatLng(finalarr.get(position).getLatitude(), finalarr.get(position).getLongitude());
//                                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
//                                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                                                }
//                                            }
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
//                            if (googleMap != null) {
//                                if (search_branch_edittext.getText().toString().isEmpty()) {
//                                    LatLng pos = new LatLng(datums.get(position).getLatitude(), datums.get(position).getLongitude());
//                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
//                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                                } else {
//                                    LatLng pos = new LatLng(finalarr.get(position).getLatitude(), finalarr.get(position).getLongitude());
//                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
//                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                                }
//                            }
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