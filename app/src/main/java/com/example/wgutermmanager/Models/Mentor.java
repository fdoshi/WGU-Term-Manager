package com.example.wgutermmanager.Models;

public class Mentor {
    private String mName;
    private String mPhone;
    private String mEmail;

    public Mentor(String mName, String mPhone, String mEmail) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
