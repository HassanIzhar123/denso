package com.tech.denso.Helper;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tech.denso.R;

public class Helper {
    /**
     * @param fragment      The fragment to get the listener for.
     * @param listenerClass The class of the listener to get.
     * @param <T>           Type of the listener to get.
     * @return A listener object for the given fragment, cast from the owning parent fragment or
     * Activity, or null if neither is a listener.
     */
    @Nullable
    public static <T> T getListener(@NonNull Fragment fragment, @NonNull Class<T> listenerClass) {
        T listener = null;
        if (listenerClass.isInstance(fragment.getParentFragment())) {
            listener = listenerClass.cast(fragment.getParentFragment());
        } else if (listenerClass.isInstance(fragment.getActivity())) {
            listener = listenerClass.cast(fragment.getActivity());
        }

        return listener;
    }

    /**
     * @param activity Activity for getting current focus
     */
//    public void HideKeyboard(Activity activity) {
//        View view = activity.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
    public void HideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * @param view view on which to perform animation
     */
    public void ShowAnimationWithVisibility(View view) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

//        Animation fadeOut = new AlphaAnimation(1, 0);
//        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
//        fadeOut.setStartOffset(1000);
//        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
//        animation.addAnimation(fadeOut);
        view.setAnimation(animation);
    }

    public int getBottomBarPosition(int position) {
        if (position == 0) {
            return R.id.item0;
        } else if (position == 1) {
            return R.id.item1;
        } else if (position == 2) {
            return R.id.item2;
        } else if (position == 3) {
            return R.id.item3;
        } else if (position == 4) {
            return R.id.item4;
        } else {
            return R.id.item4;
        }
    }

    public int getPositionFromMenuItem(int item) {
        if (item == R.id.item0) {
            return 0;
        } else if (item == R.id.item1) {
            return 1;
        } else if (item == R.id.item2) {
            return 2;
        } else if (item == R.id.item3) {
            return 3;
        } else if (item == R.id.item4) {
            return 4;
        } else {
            return 4;
        }

    }
}
