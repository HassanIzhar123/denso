package com.tech.denso.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.tech.denso.R;

public class SucessfullSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucessfull_signup);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (getIntent().getStringExtra("fromclaim") != null) {
                    if (getIntent().getStringExtra("fromclaim").equals("true")) {
                        startActivity(new Intent(SucessfullSignupActivity.this,
                                SucessfullClaimActivity.class));
                    } else if (getIntent().getStringExtra("fromclaim").equals("false")) {
                        startActivity(new Intent(SucessfullSignupActivity.this,
                                SuccessfulBookingActivity.class));
                    }
                } else if (getIntent().getStringExtra("frombooking") != null && getIntent().getStringExtra("frombooking").equals("true")) {
                    startActivity(new Intent(SucessfullSignupActivity.this,
                            SuccessfulBookingActivity.class));
                }
            }
        }, 5000);
        findViewById(R.id.gobackbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SucessfullSignupActivity.this, DashboardActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}