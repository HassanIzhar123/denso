package com.tech.denso.Fragments;

import com.tech.denso.Adapter.BookingHistoryViewAdapter;

public class HistoryAsyncModel {
    public boolean isResetPassword() {
        return isResetPassword;
    }

    public void setResetPassword(boolean resetPassword) {
        isResetPassword = resetPassword;
    }

    boolean isResetPassword;

    public BookingHistoryViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BookingHistoryViewAdapter adapter) {
        this.adapter = adapter;
    }

    BookingHistoryViewAdapter adapter;

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isNoData() {
        return noData;
    }

    public void setNoData(boolean noData) {
        this.noData = noData;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    boolean isComplete;
    boolean noData;
    boolean isError;

}
