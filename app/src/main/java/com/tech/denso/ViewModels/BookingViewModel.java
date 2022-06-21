package com.tech.denso.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookingViewModel extends ViewModel {
    private final MutableLiveData<BookingModel> selected = new MutableLiveData<BookingModel>();

    public void select(BookingModel item) {
        selected.setValue(item);
    }

    public LiveData<BookingModel> getSelected() {
        return selected;
    }
}