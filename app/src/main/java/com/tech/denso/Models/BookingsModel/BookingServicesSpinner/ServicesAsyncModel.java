package com.tech.denso.Models.BookingsModel.BookingServicesSpinner;

import com.tech.denso.Item;

import java.util.ArrayList;
import java.util.List;

public class ServicesAsyncModel {
    public List<Datum> getList() {
        return list;
    }

    public void setList(List<Datum> list) {
        this.list = list;
    }

    List<Datum> list;

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    boolean isComplete;
}
