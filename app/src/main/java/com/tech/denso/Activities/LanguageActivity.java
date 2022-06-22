package com.tech.denso.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tech.denso.Fragments.MapAsyncModel;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.R;

import needle.Needle;
import needle.UiRelatedTask;

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
//                Dialog dialog = new Dialog(LanguageActivity.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setCancelable(true);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.setContentView(R.layout.loading_dialog);
//                dialog.show();
//                Needle.onBackgroundThread().execute(new UiRelatedTask<Void>() {
//                    @Override
//                    protected Void doWork() {
//                        new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("englishselected", true);
//                        startActivity(new Intent(LanguageActivity.this, DashboardActivity.class));
//                        return null;
//                    }
//
//                    @Override
//                    protected void thenDoUiRelatedWork(Void unused) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
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