package com.tech.denso.Models.Vehicles;

import com.tech.denso.Models.Vehicles.VehicleName.Datum;
import com.tech.denso.Models.Vehicles.VehicleName.Vehicles;

import java.util.ArrayList;

public class VehiclesModel {
    public ArrayList<Datum> getList() {
        return list;
    }

    public void setList(ArrayList<Datum> list) {
        this.list = list;
    }

    ArrayList<Datum> list;


    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    boolean isComplete;
}
