package com.tech.denso.Models;

import com.tech.denso.Models.LoginModel.LoginModel;

public class BookingLoginModel {
    public LoginModel getResponseData() {
        return responseData;
    }

    public void setResponseData(LoginModel responseData) {
        this.responseData = responseData;
    }

    LoginModel responseData;
}
