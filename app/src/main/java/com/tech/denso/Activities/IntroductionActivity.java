package com.tech.denso.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tech.denso.Fragments.AppInformationFragment;
import com.tech.denso.Fragments.FeatureFragment;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.R;
import com.tech.denso.Fragments.RemainingFeatureFragment;

import me.relex.circleindicator.CircleIndicator;

public class IntroductionActivity extends AppCompatActivity {
    ViewPager viewpager;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        viewpager = findViewById(R.id.viewpager);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapterViewPager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);
        adapterViewPager.registerDataSetObserver(indicator.getDataSetObserver());
        findViewById(R.id.nextbtn).setOnClickListener(new View.OnClickListener() {
            int i = 0;

            @Override
            public void onClick(View v) {
                if (viewpager != null) {
                    i = viewpager.getCurrentItem() + 1;
                    viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                    if (i == 3) {
                        new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("ShowIntro", true);
                        new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("englishselected", true);
                        startActivity(new Intent(IntroductionActivity.this, LanguageActivity.class));
                        i = 0;
                    }
                }
            }
        });
        findViewById(R.id.skipbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("ShowIntro", true);
                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("englishselected", true);
                startActivity(new Intent(IntroductionActivity.this, LanguageActivity.class));
            }
        });
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AppInformationFragment.newInstance(0, "Page # 1");
                case 1:
                    return FeatureFragment.newInstance(1, "Page # 2");
                case 2:
                    return RemainingFeatureFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}