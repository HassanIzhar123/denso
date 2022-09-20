package com.tech.denso.Models.BookingsModel.BookingServicesSpinner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    private boolean isSelected = false;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("serviceCategory")
    @Expose
    private String serviceCategory;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        Datum itemCompare = (Datum) obj;
        if (itemCompare.getServiceCategory().equals(this.getServiceCategory()))
            return true;

        return false;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}