package com.tech.denso.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.mapbox.mapboxsdk.maps.MapView;

public class MapViewPager extends ViewPager {

    public MapViewPager(Context context) {
        super(context);
    }

    public MapViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof MapView) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}