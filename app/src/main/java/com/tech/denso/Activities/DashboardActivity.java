package com.tech.denso.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.tech.denso.Adapter.NavigationRecyclerAdapter;
import com.tech.denso.CustomViews.MapViewPager;
import com.tech.denso.Fragments.B2BLoginFragment;
import com.tech.denso.Fragments.BookingFragment;
import com.tech.denso.Fragments.ContactFragment;
import com.tech.denso.Fragments.ECatalogFragment;
import com.tech.denso.Fragments.FinalWarrantyFragment;
import com.tech.denso.Fragments.GalleryFragment;
import com.tech.denso.Fragments.HomeFragment;
import com.tech.denso.Fragments.MapsFragment;
import com.tech.denso.Fragments.NextWarrantyFragment;
import com.tech.denso.Fragments.WhyDensoServices;
import com.tech.denso.Helper.Helper;
import com.tech.denso.Interfaces.SendData;
import com.tech.denso.Fragments.ServicesFragment;
import com.tech.denso.Fragments.ServicingFragment;
import com.tech.denso.Fragments.UserFragment;
import com.tech.denso.Fragments.WarrantyFragment;
import com.tech.denso.Helper.Const;
import com.tech.denso.Helper.SharedPreference;
import com.tech.denso.Interfaces.CallBackModel;
import com.tech.denso.Interfaces.ListenFromActivity;
import com.tech.denso.Models.BookingLoginModel;
import com.tech.denso.R;
import com.tech.denso.ViewModels.BookingViewModel;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import needle.Needle;
import needle.UiRelatedTask;

public class DashboardActivity extends AppCompatActivity implements CallBackModel.OnCustomStateListener {
    RecyclerView navigation_menu;
    private DrawerLayout mDrawerLayout;
    ImageButton navigationbtn;
    NavigationView nav_view;
    public static TextView titletextview;
    RelativeLayout bottombarrel;
    //    CardView othercard;
    com.tech.denso.CustomViews.SmoothBottomBar bottomBar;
    //    BottomNavigationView bottomBar;
    MapViewPager viewpager;
    //    ViewPager viewpager;
    RelativeLayout logoutrel, myhistoryrel, loginrel;
    public static ImageButton sendbtn;
    SendData sendData;
    public ListenFromActivity activityListener;
    TextView nametext;
    public static ImageButton backbtn;
    MyPagerAdapter adapterViewPager;
    RelativeLayout loadingrel;

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
        //        View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
//        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
//            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
//            // Apply the insets as a margin to the view. Here the system is setting
//            // only the bottom, left, and right dimensions, but apply whichever insets are
//            // appropriate to your layout. You can also update the view padding
//            // if that's more appropriate.
//            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
//            mlp.leftMargin = insets.left;
//            mlp.bottomMargin = insets.bottom;
//            mlp.rightMargin = insets.right;
//            v.setLayoutParams(mlp);
//
//            // Return CONSUMED if you don't want want the window insets to keep being
//            // passed down to descendant views.
//            return WindowInsetsCompat.CONSUMED;
//        });
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
        loadingrel = findViewById(R.id.loadingrel);
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
        BookingViewModel model = new ViewModelProvider(this).get(BookingViewModel.class);
        model.getSelected().observe(this, item -> {
            if (item.getLogOutVisibility() == View.VISIBLE) {
                logoutrel.setVisibility(View.VISIBLE);
            } else if (item.getLogOutVisibility() == View.GONE) {
                logoutrel.setVisibility(View.GONE);
            } else {
                logoutrel.setVisibility(View.GONE);
            }
            if (item.getHistoryRelVisibility() == View.VISIBLE) {
                myhistoryrel.setVisibility(View.VISIBLE);
            } else if (item.getHistoryRelVisibility() == View.GONE) {
                myhistoryrel.setVisibility(View.GONE);
            } else {
                myhistoryrel.setVisibility(View.GONE);
            }
            if (item.getLoginVisibility() == View.VISIBLE) {
                loginrel.setVisibility(View.VISIBLE);
            } else if (item.getLoginVisibility() == View.GONE) {
                loginrel.setVisibility(View.GONE);
            } else {
                loginrel.setVisibility(View.GONE);
            }
        });
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
            loginrel.setVisibility(View.GONE);
        } else {
            myhistoryrel.setVisibility(View.GONE);
            logoutrel.setVisibility(View.GONE);
            loginrel.setVisibility(View.VISIBLE);
        }
        loadingrel.setVisibility(View.VISIBLE);
        Needle.onBackgroundThread().execute(new UiRelatedTask<DashboardActivityModel>() {
            @Override
            protected DashboardActivityModel doWork() {
                DashboardActivityModel model = new DashboardActivityModel();
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
                model.setNavigationRecyclerAdapter(adapter);
                ArrayList<Fragment> fragments = new ArrayList<>();
                fragments.add(BookingFragment.newInstance());
                fragments.add(MapsFragment.newInstance(1, "Page # 2"));
                fragments.add(ServicingFragment.newInstance(2, "Page # 3"));
                fragments.add(WarrantyFragment.newInstance(3, "Page # 4"));
                fragments.add(UserFragment.newInstance(4, "Page # 5"));
                adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), fragments);
                model.setAdapter(adapterViewPager);
                model.setComplete(true);
                return model;
            }

            @Override
            protected void thenDoUiRelatedWork(DashboardActivityModel result) {
                if (result != null && result.isComplete()) {
                    NavigationRecyclerAdapter adapter = result.getNavigationRecyclerAdapter();
                    navigation_menu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    navigation_menu.setAdapter(adapter);
                    viewpager.setAdapter(result.getAdapter());
                    viewpager.setOffscreenPageLimit(result.adapter.getCount());
                    adapter.setOnItemCLickListener(new NavigationRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(View v, int position, String value) {
                            if (value.equals("Home")) {
                                findViewById(R.id.framelayout).setVisibility(View.GONE);
                                bottombarrel.setVisibility(View.VISIBLE);
                                mDrawerLayout.closeDrawer(nav_view);
                                if (adapterViewPager.getCurrentFragment() instanceof WarrantyFragment) {
                                    WarrantyFragment.MyPagerAdapter viewpageradapter = WarrantyFragment.adapter;
                                    if (viewpageradapter.getCurrentFragment() instanceof NextWarrantyFragment) {
                                        DashboardActivity.backbtn.setVisibility(View.VISIBLE);
                                    } else if (viewpageradapter.getCurrentFragment() instanceof FinalWarrantyFragment) {
                                        DashboardActivity.backbtn.setVisibility(View.VISIBLE);
                                    } else {
                                        DashboardActivity.backbtn.setVisibility(View.GONE);
                                    }
                                }
                            } else if (value.equals("Services")) {
//                                new Helper().ShowAnimationWithVisibility( findViewById(R.id.framelayout));
                                DashboardActivity.titletextview.setText("SERVICES");
                                LoadFragment(DashboardActivity.this, new ServicesFragment());
                                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
                                bottombarrel.setVisibility(View.GONE);
                                sendbtn.setVisibility(View.GONE);
                                backbtn.setVisibility(View.GONE);
                            } else if (value.equals("Why Denso Services?")) {
                                DashboardActivity.titletextview.setText("WHY DENSO SERVICES");
                                open_Webpage("https://djautoac.com/#/whydensoservices");
                                backbtn.setVisibility(View.GONE);
//                                LoadFragment(DashboardActivity.this, new WhyDensoServices());
//                                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
//                                bottombarrel.setVisibility(View.GONE);
//                                sendbtn.setVisibility(View.GONE);
//                                backbtn.setVisibility(View.GONE);
                            } else if (value.equals("B2B Login")) {
                                DashboardActivity.titletextview.setText("B2B LOGIN");
                                open_Webpage("https://shop.dj-auto.com/");
                                backbtn.setVisibility(View.GONE);
//                                LoadFragment(DashboardActivity.this, new B2BLoginFragment());
//                                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
//                                bottombarrel.setVisibility(View.GONE);
//                                sendbtn.setVisibility(View.GONE);
//                                backbtn.setVisibility(View.GONE);
                            } else if (value.equals("E-Catalog")) {
                                DashboardActivity.titletextview.setText("E-CATALOG");
                                open_Webpage("https://djautoac.com/#/ecatalog/");
                                backbtn.setVisibility(View.GONE);
//                                LoadFragment(DashboardActivity.this, new ECatalogFragment());
//                                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
//                                bottombarrel.setVisibility(View.GONE);
//                                sendbtn.setVisibility(View.GONE);
//                                backbtn.setVisibility(View.GONE);
                            } else if (value.equals("Our Gallery")) {
                                DashboardActivity.titletextview.setText("OUR GALLERY");
                                open_Webpage("https://djautoac.com/#/gallery/");
                                backbtn.setVisibility(View.GONE);
//                                LoadFragment(DashboardActivity.this, new GalleryFragment());
//                                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
//                                bottombarrel.setVisibility(View.GONE);
//                                sendbtn.setVisibility(View.GONE);
//                                backbtn.setVisibility(View.GONE);
                            } else if (value.equals("Contact")) {
                                DashboardActivity.titletextview.setText("CONTACT");
                                open_Webpage("https://djautoac.com/#/contact");
                                backbtn.setVisibility(View.GONE);
//                                LoadFragment(DashboardActivity.this, new ContactFragment());
//                                findViewById(R.id.framelayout).setVisibility(View.VISIBLE);
//                                bottombarrel.setVisibility(View.GONE);
//                                sendbtn.setVisibility(View.GONE);
//                                backbtn.setVisibility(View.GONE);
                            }
                            new Helper().HideKeyboard(DashboardActivity.this);
                        }
                    });
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
                                backbtn.setVisibility(View.GONE);
                            } else if (position == 3) {
                                viewpager.setCurrentItem(position);
                                WarrantyFragment.MyPagerAdapter viewpageradapter = WarrantyFragment.adapter;
                                if (viewpageradapter.getCurrentFragment() instanceof NextWarrantyFragment ||
                                        viewpageradapter.getCurrentFragment() instanceof FinalWarrantyFragment) {
                                    backbtn.setVisibility(View.VISIBLE);
                                }
                            } else {
                                viewpager.setCurrentItem(position);
                                backbtn.setVisibility(View.GONE);
                            }
                            new Helper().HideKeyboard(DashboardActivity.this);
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
//                            bottomBar.setSelectedItemId(new Helper().getBottomBarPosition(position));
                            new Helper().HideKeyboard(DashboardActivity.this);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    loadingrel.setVisibility(View.GONE);
                }
            }
        });
        logoutrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("LoggedIn", false);
                new SharedPreference(getApplicationContext(), getApplicationContext().toString()).setBoolean("ShowIntro", false);
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
                new Helper().HideKeyboard(DashboardActivity.this);
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != activityListener) {
                    activityListener.doSomethingInFragment();
                    new Helper().HideKeyboard(DashboardActivity.this);
                }
            }
        });
        myhistoryrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean loggedbol = new SharedPreference(getApplicationContext(), getApplicationContext().toString()).getPreferenceBoolean("LoggedIn");
                if (loggedbol) {
                    mDrawerLayout.closeDrawer(nav_view);
                    viewpager.setCurrentItem(4);
                    bottomBar.setActiveItem(4);
                    if (!(adapterViewPager.getCurrentFragment() instanceof HomeFragment)) {
                        findViewById(R.id.framelayout).setVisibility(View.GONE);
                        bottombarrel.setVisibility(View.VISIBLE);
                        mDrawerLayout.closeDrawer(nav_view);
                        if (adapterViewPager.getCurrentFragment() instanceof WarrantyFragment) {
                            WarrantyFragment.MyPagerAdapter viewpageradapter = WarrantyFragment.adapter;
                            if (viewpageradapter.getCurrentFragment() instanceof NextWarrantyFragment) {
                                DashboardActivity.backbtn.setVisibility(View.VISIBLE);
                            } else if (viewpageradapter.getCurrentFragment() instanceof FinalWarrantyFragment) {
                                DashboardActivity.backbtn.setVisibility(View.VISIBLE);
                            } else {
                                DashboardActivity.backbtn.setVisibility(View.GONE);
                            }
                        }
                    }
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

    public void OpenPage(int position) {
        if (position == 2) {
            if (!(adapterViewPager.getCurrentFragment() instanceof HomeFragment)) {
//                findViewById(R.id.framelayout).animate().translationY(findViewById(R.id.framelayout).getHeight()).alpha(0.0f).setDuration(1200).setListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        findViewById(R.id.framelayout).setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
                if (adapterViewPager.getCurrentFragment() instanceof WarrantyFragment) {
                    WarrantyFragment.MyPagerAdapter viewpageradapter = WarrantyFragment.adapter;
                    if (viewpageradapter.getCurrentFragment() instanceof NextWarrantyFragment) {
                        DashboardActivity.backbtn.setVisibility(View.VISIBLE);
                    } else if (viewpageradapter.getCurrentFragment() instanceof FinalWarrantyFragment) {
                        DashboardActivity.backbtn.setVisibility(View.VISIBLE);
                    } else {
                        DashboardActivity.backbtn.setVisibility(View.GONE);
                    }
                }
            }
            findViewById(R.id.framelayout).setVisibility(View.GONE);
            bottombarrel.setVisibility(View.VISIBLE);
            mDrawerLayout.closeDrawer(nav_view);
            mDrawerLayout.closeDrawer(nav_view);
            viewpager.setCurrentItem(position);
            bottomBar.setActiveItem(position);
            DashboardActivity.titletextview.setText("MY ACCOUNT");
            sendbtn.setVisibility(View.GONE);
        } else {
            viewpager.setCurrentItem(position);
        }
    }

    private void open_Webpage(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
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
        FragmentManager ft = ((FragmentActivity) activity).getSupportFragmentManager();
        FragmentTransaction transaction = ft.beginTransaction();
        transaction.replace(R.id.framelayout, frag);
        transaction.addToBackStack(null);
        transaction.commit();
        mDrawerLayout.closeDrawer(nav_view);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private Fragment mCurrentFragment;

        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
            super(fragmentManager);
            this.fragments = fragments;
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