package com.tech.denso.Activities;

import com.tech.denso.Adapter.NavigationRecyclerAdapter;

public class DashboardActivityModel {
    public DashboardActivity.MyPagerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(DashboardActivity.MyPagerAdapter adapter) {
        this.adapter = adapter;
    }

    DashboardActivity.MyPagerAdapter adapter;
    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    boolean isComplete;
    public NavigationRecyclerAdapter getNavigationRecyclerAdapter() {
        return navigationRecyclerAdapter;
    }

    public void setNavigationRecyclerAdapter(NavigationRecyclerAdapter navigationRecyclerAdapter) {
        this.navigationRecyclerAdapter = navigationRecyclerAdapter;
    }

    NavigationRecyclerAdapter navigationRecyclerAdapter;
}
