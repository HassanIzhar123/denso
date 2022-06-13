package com.tech.denso.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tech.denso.Fragments.AppInformationFragment;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Boolean bol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("ShowIntro");
                                if (!bol) {
                                    startActivity(new Intent(SplashActivity.this, IntroductionActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                                    finish();
                                }
//                                Boolean loggedbol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("LoggedIn");
//                                if (loggedbol) {
//                                    Boolean bol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("ShowIntro");
//                                    if (!bol) {
//                                        startActivity(new Intent(SplashActivity.this, IntroductionActivity.class));
//                                        finish();
//                                    } else {
//                                        startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
//                                        finish();
//                                    }
//                                } else {
//                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                                    finish();
//                                }
                            }
                        }, 3000);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getApplicationContext(), "Cant Proceed without permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();
    }
}