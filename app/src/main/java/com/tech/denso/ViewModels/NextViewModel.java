package com.tech.denso.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tech.denso.Models.InitialWarrantyFragment.InitialWarrantyModel;

public class NextViewModel extends ViewModel {
    private final MutableLiveData<InitialWarrantyModel> selected = new MutableLiveData<InitialWarrantyModel>();

    public void select(InitialWarrantyModel item) {
        selected.setValue(item);
    }

    public LiveData<InitialWarrantyModel> getSelected() {
        return selected;
    }
}