package com.tech.denso.ViewModels;

public class BookingModel {
    public int getLoginVisibility() {
        return loginVisibility;
    }

    public void setLoginVisibility(int loginVisibility) {
        this.loginVisibility = loginVisibility;
    }

    int loginVisibility;
    public int getLogOutVisibility() {
        return logOutVisibility;
    }

    public void setLogOutVisibility(int logOutVisibility) {
        this.logOutVisibility = logOutVisibility;
    }

    public int getHistoryRelVisibility() {
        return historyRelVisibility;
    }

    public void setHistoryRelVisibility(int historyRelVisibility) {
        this.historyRelVisibility = historyRelVisibility;
    }

    int logOutVisibility;
    int historyRelVisibility;
}
