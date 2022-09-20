package com.tech.denso.Fragments;

import java.util.ArrayList;

public class ServicesFragmentModel {
    ArrayList<String>servicesname;

    public ServicesFragmentModel(ArrayList<String> servicesname, ArrayList<String> serviceheader, ArrayList<String> servicedescription) {
        this.servicesname = servicesname;
        this.serviceheader = serviceheader;
        this.servicedescription = servicedescription;
    }

    public ArrayList<String> getServicesname() {
        return servicesname;
    }

    public void setServicesname(ArrayList<String> servicesname) {
        this.servicesname = servicesname;
    }

    public ArrayList<String> getServiceheader() {
        return serviceheader;
    }

    public void setServiceheader(ArrayList<String> serviceheader) {
        this.serviceheader = serviceheader;
    }

    public ArrayList<String> getServicedescription() {
        return servicedescription;
    }

    public void setServicedescription(ArrayList<String> servicedescription) {
        this.servicedescription = servicedescription;
    }

    ArrayList<String> serviceheader;
    ArrayList<String> servicedescription;
}
