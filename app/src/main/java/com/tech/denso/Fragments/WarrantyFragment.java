package com.tech.denso.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tech.denso.CustomViews.NonSwipeableViewPager;
import com.tech.denso.R;

public class WarrantyFragment extends Fragment {
    private String title;
    private int page;
    View view;
    public static NonSwipeableViewPager pager;
    public static MyPagerAdapter adapter;

    public static WarrantyFragment newInstance(int page, String title) {
        WarrantyFragment fragmentFirst = new WarrantyFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.warranty_fragment, container, false);
        pager = view.findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(adapter);

        return view;
    }

    public  class MyPagerAdapter extends FragmentPagerAdapter {
        private Fragment mCurrentFragment;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return InitialWarrantyFragment.newInstance(0, "Page # 1");
                case 1:
                    return NextWarrantyFragment.newInstance(1, "Page # 2");
                case 2:
                    return FinalWarrantyFragment.newInstance(2, "Page # 3");
                default:
                    return FinalWarrantyFragment.newInstance(3, "Page # 3");
            }
        }

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}


