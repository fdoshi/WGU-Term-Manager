package com.example.wgutermmanager.Models;

import java.io.Serializable;

public class Course implements Serializable {
    private int mCourseId;
    private String mTitle;
    private String mStart;
    private String mEnd;
    private String mStatus;
    private int mTermId;

    public Course(int mCourseId, String mTitle, String mStatus, String mStart, String mEnd, int mTermId) {
        this.mCourseId = mCourseId;
        this.mTitle = mTitle;
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mStatus = mStatus;
        this.mTermId = mTermId;
    }

    public int getmCourseId() {
        return mCourseId;
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

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public int getmTermId() {
        return mTermId;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
