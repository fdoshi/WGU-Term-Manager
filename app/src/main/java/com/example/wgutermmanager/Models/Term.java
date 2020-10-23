package com.example.wgutermmanager.Models;

import java.io.Serializable;

public class Term implements Serializable {
    private int mTermId;
    private String mTitle;
    private String mStart;
    private String mEnd;

    public Term(int mTermId, String mTitle, String mStart, String mEnd) {
        this.mTermId = mTermId;
        this.mTitle = mTitle;
        this.mStart = mStart;
        this.mEnd = mEnd;
    }

    public int getmTermId() {
        return mTermId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmStart() {
        return mStart;
    }

    public void setmStart(String mStart) {
        this.mStart = mStart;
    }

    public String getmEnd() {
        return mEnd;
    }

    public void setmEnd(String mEnd) {
        this.mEnd = mEnd;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
