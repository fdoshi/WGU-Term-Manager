package com.example.wgutermmanager.Models;

public class Note {
    private int mNoteId;
    private String mNote;
    private int mCourseId;

    public Note(int mNoteId, String mNote, int mCourseId) {
        this.mNoteId = mNoteId;
        this.mNote = mNote;
        this.mCourseId = mCourseId;
    }

    public int getmNoteId() {
        return mNoteId;
    }

    public int getmCourseId() {
        return mCourseId;
    }

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }
}
