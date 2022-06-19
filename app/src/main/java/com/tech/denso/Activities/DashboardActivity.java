package com.tech.denso.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.tech.denso.Adapter.NavigationRecyclerAdapter;
import com.tech.denso.Fragments.BookingFragment;
import com.tech.denso.Fragments.ContactFragment;
import com.tech.denso.Fragments.FinalWarrantyFragment;
import com.tech.denso.Fragments.MapsFragment;
import com.tech.denso.Fragments.NextWarrantyFragment;
import com.tech.denso.Fragments.SendData;
import com.tech.denso.Fragments.ServicesFragment;
import com.tech.denso.Fragments.ServicingFragment;
import com.tech.denso.Fragments.UserFragment;
import com.tech.denso.Fragments.WarrantyFragment;
import com.tech.denso.Fragments.WhyDensoFragment;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.Interfaces.CallBackModel;
import com.tech.denso.Interfaces.ListenFromActivity;
import com.tech.denso.R;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class DashboardActivity extends AppCompatActivity implements CallBackModel.OnCustomStateListener {
    RecyclerView navigation_menu;
    private DrawerLayout mDrawerLayout;
    ImageButton navigationbtn;
    NavigationView nav_view;
    public static TextView titletextview;
    RelativeLayout bottombarrel;
    //    CardView othercard;
    SmoothBottomBar bottomBar;
    ViewPager viewpager;
    RelativeLayout logoutrel, myhistoryrel, loginrel;
    public static ImageButton sendbtn;
    SendData sendData;
    public ListenFromActivity activityListener;
    TextView nametext;
    public static ImageButton backbtn;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    public void setActivityListener(ListenFromActivity activityListener) {
        this.activityListener = activityListener;
    }

    @Override
    public void OnBack() {
        if (bottomBar != null && viewpager != null) {
            bottomBar.setActiveItem(0);
            viewpager.setCurrentItem(0);
        }
    }

    @Override
    public void OnSignUp() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        nametext = findViewById(R.id.nametext);
        if (null != activityListener) {
            activityListener.doSomethingInFragment();
        }
        CallBackModel.getInstance().setListener(DashboardActivity.this);
        new Const().setEmail(new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreference("Email"));
        new Const().setPassword(new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreference("Password"));
        new Const().setFirstname(new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreference("Firstname"));
        new Const().setLastname(new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreference("Lastname"));
        nametext.setText("Welcome, " + new Const().getFirstname() + " " + new Const().getLastname());
        bottomBar = findViewById(R.id.bottombar);
        viewpager = findViewById(R.id.viewpager);
        bottombarrel = findViewById(R.id.bottombarrel);
        myhistoryrel = findViewById(R.id.myhistoryrel);
        navigation_menu = findViewById(R.id.navigation_menu);
        navigationbtn = findViewById(R.id.navigationbtn);
        nav_view = findViewById(R.id.nav_view);
        titletextview = findViewById(R.id.titletextview);
        logoutrel = findViewById(R.id.logoutrel);
        loginrel = findViewById(R.id.loginrel);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        backbtn = findViewById(R.id.backbtn);
        sendbtn = findViewById(R.id.sendbtn);
        navigation_menu.setAdapter(null);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarrantyFragment.MyPagerAdapter viewpageradapter = WarrantyFragment.adapter;
                if (viewpageradapter.getCurrentFragment() instanceof NextWarrantyFragment) {
                    DashboardActivity.backbtn.setVisibility(View.GONE);
                    WarrantyFragment.pager.setCurrentItem(0);
                } else if (viewpageradapter.getCurrentFragment() instanceof FinalWarrantyFragment) {
                    WarrantyFragment.pager.setCurrentItem(1);
                }
            }
        });
        Boolean loggedbol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("LoggedIn");
        if (loggedbol) {
            myhistoryrel.setVisibility(View.VISIBLE);
            logoutrel.setVisibility(View.VISIBLE);
        } else {
            myhistoryrel.setVisibility(View.GONE);
            logoutrel.setVisibility(View.GONE);
        }

        ArrayList<String> array = new ArrayList<>();
        array.add("Home");
        array.add("Services");
        array.add("Why Denso Services?");
        array.add("B2B Login");
        array.add("E-Catalog");
        array.add("Our Gallery");
        array.add("Contact");
        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.home_icon);
        icons.add(R.drawable.mensu_services_icon);
        icons.add(R.drawable.why_denso_icon);
        icons.add(R.drawable.b2b_icon);
        icons.add(R.drawable.e_catalog_icon);
        icons.add(R.drawable.gallery_icon);
        icons.add(R.drawable.contact_us_icon);
        NavigationRecyclerAdapter adapter = new NavigationRecyclerAdapter(array, icons);
        navigation_menu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        navigation_menu.setAdapter(adapter);
//        LoadFragment(DashboardActivity.this, new HomeFragment());
        LoadDashboardViewpager();
        adapter.setOnItemCLickListener(new NavigationRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position, String value) {
                if (value.equals("Home")) {
//                    LoadFragment(DashboardActivity.this, new HomeFragment());
                    findViewById(R.id.framelayout).setVisibility(View.GONE);
                    bottombarrel.setVisibility(View.VISIBLE);
                    mDrawerLayout.closeDrawer(nav_view);
                    sendbtn.setVisibility(View.VISIBLE);
                } else if (value.equals("Services")) {
                    LoadFragment(DashboardActivity.this, new ServicesFragment());
                    findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                    bottombarrel.setVisibility(View.GONE);
                    sendbtn.setVisibility(View.GONE);
                } else if (value.equals("Why Denso Services?")) {
                    LoadFragment(DashboardActivity.this, new WhyDensoFragment());
                    findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                    bottombarrel.setVisibility(View.GONE);
                    sendbtn.setVisibility(View.GONE);
                } else if (value.equals("B2B Login")) {
                    open_Webpage("https://shop.dj-auto.com/");
                } else if (value.equals("E-Catalog")) {
                    open_Webpage("https://djauto-service.com/ecatalog/");
                } else if (value.equals("Our Gallery")) {
                    open_Webpage("https://djauto-service.com/gallery/");
                } else if (value.equals("Contact")) {
                    LoadFragment(DashboardActivity.this, new ContactFragment());
                    findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                    bottombarrel.setVisibility(View.GONE);
                    sendbtn.setVisibility(View.GONE);
                }
            }
        });
        logoutrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("LoggedIn", false);
                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).removeAllValues();
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                i.putExtra("clickedlogout", true);
                startActivity(i);
            }
        });
        loginrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        navigationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(nav_view)) {
                    mDrawerLayout.closeDrawer(nav_view);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != activityListener) {
                    activityListener.doSomethingInFragment();
                }
            }
        });
        myhistoryrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DashboardActivity.this, MyAccountActivity.class));

                Boolean loggedbol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("LoggedIn");
                if (loggedbol) {
                    mDrawerLayout.closeDrawer(nav_view);
                    viewpager.setCurrentItem(4);
                    bottomBar.setActiveItem(4);
                    DashboardActivity.titletextview.setText("MY ACCOUNT");
                    sendbtn.setVisibility(View.GONE);
                } else {
                    Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                    i.putExtra("clickedonuser", true);
                    startActivityForResult(i, 1);
                }
            }
        });
    }

    private void open_Webpage(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void LoadDashboardViewpager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(BookingFragment.newInstance());
        fragments.add(MapsFragment.newInstance(1, "Page # 2"));
        fragments.add(ServicingFragment.newInstance(2, "Page # 3"));
        fragments.add(WarrantyFragment.newInstance(3, "Page # 4"));
        fragments.add(UserFragment.newInstance(4, "Page # 5"));
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapterViewPager);
        viewpager.setOffscreenPageLimit(5);
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelect(int position) {
                if (position == 4) {
                    Boolean loggedbol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("LoggedIn");
                    if (loggedbol) {
                        viewpager.setCurrentItem(position);
                        DashboardActivity.titletextview.setText("MY ACCOUNT");
                        sendbtn.setVisibility(View.GONE);
                    } else {
                        viewpager.setCurrentItem(position);
                        Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                        i.putExtra("clickedonuser", true);
                        startActivityForResult(i, 1);
                    }
                } else {
                    viewpager.setCurrentItem(position);
                }
            }
        });
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    DashboardActivity.titletextview.setText("MAKE A BOOKING");
                    sendbtn.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    DashboardActivity.titletextview.setText("MAP LOCATOR");
                    sendbtn.setVisibility(View.GONE);
                } else if (position == 2) {
                    DashboardActivity.titletextview.setText("SERVICING");
                    sendbtn.setVisibility(View.GONE);
                } else if (position == 3) {
                    DashboardActivity.titletextview.setText("WARRANTY CLAIM");
                    sendbtn.setVisibility(View.GONE);
                } else if (position == 4) {
                    DashboardActivity.titletextview.setText("MY ACCOUNT");
                    sendbtn.setVisibility(View.GONE);
                }
                bottomBar.setActiveItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Boolean check = data.getBooleanExtra("comingback", false);
                if (check) {
                    bottomBar.setActiveItem(0);
                    viewpager.setCurrentItem(0);
                }
            }
        }
    }

    public void LoadFragment(Activity activity, Fragment frag) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentManager ft = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction transaction = ft.beginTransaction();
//                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction.replace(R.id.framelayout, frag); // replace a Fragment with Frame Layout
                transaction.addToBackStack(null);
                transaction.commit();
                mDrawerLayout.closeDrawer(nav_view);
            }
        }, 0);
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