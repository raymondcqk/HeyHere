package com.raymondqk.raysqlitepractice.model;

/**
 * Created by 陈其康 raymondchan on 2016/8/17 0017.
 */
public class ContactDetail {
    private String userTrueName;
    private String username;
    private String email;
    private String phone;
    private String identity;
    //    Image avatar;


    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
