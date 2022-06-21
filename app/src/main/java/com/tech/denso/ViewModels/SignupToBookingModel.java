package com.tech.denso.ViewModels;

import com.tech.denso.Models.BookingsModel.BookingSendModel;

import java.io.Serializable;

public class SignupToBookingModel implements Serializable {
    public BookingSendModel getModel() {
        return model;
    }

    public void setModel(BookingSendModel model) {
        this.model = model;
    }

    BookingSendModel model;

    public boolean isComingBackFlag() {
        return comingBackFlag;
    }

    public void setComingBackFlag(boolean comingBackFlag) {
        this.comingBackFlag = comingBackFlag;
    }

    boolean comingBackFlag;
}
