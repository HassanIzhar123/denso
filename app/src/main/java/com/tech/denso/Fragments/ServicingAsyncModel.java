package com.tech.denso.Fragments;

import com.tech.denso.Models.Services.Datum;

import java.util.List;

public class ServicingAsyncModel {
    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public List<Datum> getList() {
        return list;
    }

    public void setList(List<Datum> list) {
        this.list = list;
    }

    boolean isComplete;
    List<Datum> list;
}
