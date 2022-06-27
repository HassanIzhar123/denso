package com.tech.denso.Fragments;

import com.tech.denso.Models.Vehicles.VehicleModel.Datum;
import com.tech.denso.Models.Vehicles.VehicleModel.VehiclesModel;

import java.util.ArrayList;
import java.util.List;

public class VehiclesModelModel {
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
