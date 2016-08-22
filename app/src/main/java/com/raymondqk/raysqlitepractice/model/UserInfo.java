package com.raymondqk.raysqlitepractice.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 陈其康 raymondchan on 2016/8/13 0013.
 */
public class UserInfo {
    @SerializedName("phone")
    private String phone;
    @SerializedName("passwd")
    private String passwd;
    @SerializedName("status")
    private boolean isLogging;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public boolean isLogging() {
        return isLogging;
    }

    public void setLogging(boolean logging) {
        isLogging = logging;
    }

    @Override
    public String toString() {
        return " phone: " + getPhone() + " \n " + "passwd: " + getPasswd() + " \n" + "logginStatus: " + isLogging() + " \n--------\n";
    }
}
