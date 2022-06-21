package com.tech.denso.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignupToBookingViewModel extends ViewModel {
    private final MutableLiveData<SignupToBookingModel> selected =
            new MutableLiveData<SignupToBookingModel>();

    public void select(SignupToBookingModel item) {
        selected.setValue(item);
    }

    public LiveData<SignupToBookingModel> getSelected() {
        return selected;
    }
}