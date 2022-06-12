package com.tech.denso.Models.BookingsModel.BookingTimeSpinnerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("bookingStatus")
    @Expose
    private Boolean bookingStatus;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("timeSlot")
    @Expose
    private String timeSlot;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Boolean getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

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

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}