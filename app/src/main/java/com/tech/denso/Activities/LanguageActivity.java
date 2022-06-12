package com.tech.denso.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.R;

public class LanguageActivity extends AppCompatActivity {
    Button englishbtn, arabicbtn;
    RelativeLayout englishbtnrel, arabicbtnrel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        englishbtnrel = findViewById(R.id.englishbtnrel);
        arabicbtnrel = findViewById(R.id.arabicbtnrel);
        englishbtn = findViewById(R.id.englishbtn);
        arabicbtn = findViewById(R.id.arabicbtn);
        englishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("englishselected", true);
                startActivity(new Intent(LanguageActivity.this, DashboardActivity.class));
            }
        });
        arabicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LanguageActivity.this, ArabicLanguageErrorActivity.class));
            }
        });
    }
}