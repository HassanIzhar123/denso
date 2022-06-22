package com.tech.denso.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tech.denso.Interfaces.SendData;
import com.tech.denso.R;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class HomeFragment extends Fragment implements SendData {
    View v;
    SmoothBottomBar bottomBar;
    ViewPager viewpager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        bottomBar = v.findViewById(R.id.bottombar);
        viewpager = v.findViewById(R.id.viewpager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(BookingFragment.newInstance());
        fragments.add(MapsFragment.newInstance(1, "Page # 2"));
        fragments.add(ServicingFragment.newInstance(2, "Page # 3"));
        fragments.add(UserFragment.newInstance(3, "Page # 4"));
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapterViewPager);
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int position) {
                viewpager.setCurrentItem(position);
            }
        });
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    com.tech.denso.Activities.DashboardActivity.titletextview.setText("MAKE A BOOKING");
                } else if (position == 1) {
                    com.tech.denso.Activities.DashboardActivity.titletextview.setText("MAP LOCATOR");
                } else if (position == 2) {
                    com.tech.denso.Activities.DashboardActivity.titletextview.setText("SERVICING");
                } else if (position == 3) {
                    com.tech.denso.Activities.DashboardActivity.titletextview.setText("MY ACCOUNT");
                }
                bottomBar.setActiveItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }


    @Override
    public void send(String firstname, String lastname, String email) {
        Toast.makeText(getContext(), "Toastcheck", Toast.LENGTH_SHORT).show();
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
            super(fragmentManager);
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}
