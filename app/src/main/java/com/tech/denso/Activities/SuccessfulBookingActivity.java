package com.tech.denso.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tech.denso.R;

public class SuccessfulBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_booking);
        findViewById(R.id.gobackbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessfulBookingActivity.this, DashboardActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(this, DashboardActivity.class));
    }
}