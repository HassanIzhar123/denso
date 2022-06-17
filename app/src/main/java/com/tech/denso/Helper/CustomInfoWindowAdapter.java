package com.tech.denso.Helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.MotionEventCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.tech.denso.Models.Locations.Datum;
import com.tech.denso.R;

import java.util.List;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;
    List<Datum> array;

    public CustomInfoWindowAdapter(Context context, List<Datum> array) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.infowindowlayout, null);
        this.array = array;
    }

    private void rendowWindowText(Marker marker, View view) {
        int position = (int) marker.getTag();
        String title = marker.getTitle();
        TextView cityname = (TextView) view.findViewById(R.id.cityname);
        TextView maptext = (TextView) view.findViewById(R.id.maptext);
        TextView timetext = (TextView) view.findViewById(R.id.timetext);
        TextView calltext = (TextView) view.findViewById(R.id.calltext);
        RelativeLayout bookingbtn = (RelativeLayout) view.findViewById(R.id.bookingbtn);
        RelativeLayout emailrel = (RelativeLayout) view.findViewById(R.id.emailrel);
        RelativeLayout directionbtn = (RelativeLayout) view.findViewById(R.id.directionbtn);
        cityname.setText(array.get(position).getBranchName());
        maptext.setText(array.get(position).getAddress());
        timetext.setText(mContext.getResources().getString(R.string.openingtime) + array.get(position).getOpeningSaturday() + mContext.getResources().getString(R.string.to) + array.get(position).getTillThursday());
        calltext.setText(String.valueOf(array.get(position).getPhoneNumber()));
//        bookingbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        emailrel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        bookingbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch (action){
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(mContext, "boooking buttin clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        emailrel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch (action){
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(mContext, "email buttin clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        directionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?saddr=" + "9982878" + "," + "76285774" + "&daddr=" + "9992084" + "," + "76286455";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public View getInfoWindow(Marker marker) {
//        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}