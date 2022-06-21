package com.tech.denso.Activities;

import com.android.volley.VolleyError;
import com.tech.denso.Models.SignUpModel.SignUpModel;

public class SignAsyncUpModel {
    public SignUpModel getSignUpModel() {
        return signUpModel;
    }

    public void setSignUpModel(SignUpModel signUpModel) {
        this.signUpModel = signUpModel;
    }

    SignUpModel signUpModel;

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    boolean isComplete;
}
