package com.example.wgutermmanager.Models;

import java.io.Serializable;

public class Assessment implements Serializable {
    private int mAssessmentId;
    private String mTitle;
    private String mType;
    private String mDue;
    private int mCourseId;

    public Assessment(int mAssessmentId, String mTitle, String mType, String mDue, int mCourseId) {
        this.mAssessmentId = mAssessmentId;
        this.mTitle = mTitle;
        this.mType = mType;
        this.mDue = mDue;
        this.mCourseId = mCourseId;
    }

    public int getmAssessmentId() {
        return mAssessmentId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmDue() {
        return mDue;
    }

    public void setmDue(String mDue) {
        this.mDue = mDue;
    }

    public int getmCourseId() {
        return mCourseId;
    }

    public void setmCourseId(int mCourseId) {
        this.mCourseId = mCourseId;
    }
}
