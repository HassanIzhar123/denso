package com.tech.denso.Helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
}
