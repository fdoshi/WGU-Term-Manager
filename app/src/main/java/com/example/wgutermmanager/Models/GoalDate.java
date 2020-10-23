package com.example.wgutermmanager.Models;

public class GoalDate {

    private int mGoalDateId;
    private String mDescription;
    private String mDate;
    private int mAssessmentId;

    public GoalDate(String mDescription, String mDate) {
        this.mDescription = mDescription;
        this.mDate = mDate;
    }

    public int getmGoalDateId() {
        return mGoalDateId;
    }

    public int getmAssessmentId() {
        return mAssessmentId;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
