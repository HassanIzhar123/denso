package com.tech.denso.Fragments;

import com.tech.denso.Adapter.CustomAdapter;
import com.tech.denso.Models.Locations.Datum;

import java.util.List;

public class MapAsyncModel {
    public List<Datum> getDatums() {
        return datums;
    }

    public void setDatums(List<Datum> datums) {
        this.datums = datums;
    }

    List<Datum> datums;
    public boolean isOnNoData() {
        return onNoData;
    }

    public void setOnNoData(boolean onNoData) {
        this.onNoData = onNoData;
    }

    boolean onNoData;
    public CustomAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CustomAdapter adapter) {
        this.adapter = adapter;
    }

    CustomAdapter adapter;
    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isOnError() {
        return onError;
    }

    public void setOnError(boolean onError) {
        this.onError = onError;
    }

    boolean isComplete,onError;
}
