package com.tech.denso.Interfaces;

public class CallBackModel {

    public interface OnCustomStateListener {
        void OnBack();
        void OnSignUp();
    }

    private static CallBackModel mInstance;
    private OnCustomStateListener mListener;
    private boolean mState;

    private CallBackModel() {
    }

    public static CallBackModel getInstance() {
        if (mInstance == null) {
            mInstance = new CallBackModel();
        }
        return mInstance;
    }

    public void setListener(OnCustomStateListener listener) {
        mListener = listener;
    }

    public void onBackCallBack() {
        if (mListener != null) {
            mListener.OnBack();
        }
    }
    public void onSignUpClicked() {
        if (mListener != null) {
            mListener.OnSignUp();
        }
    }
}