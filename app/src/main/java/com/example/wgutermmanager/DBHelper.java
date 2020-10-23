package com.example.wgutermmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wgutermmanager.Models.Assessment;
import com.example.wgutermmanager.Models.Course;
import com.example.wgutermmanager.Models.GoalDate;
import com.example.wgutermmanager.Models.Mentor;
import com.example.wgutermmanager.Models.Note;
import com.example.wgutermmanager.Models.Term;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WGU_Term_Manager.db";

    private static final String TERM_TABLE_NAME = "term";
    private static final String TERM_COLUMN_ID = "term_id";
    private static final String TERM_COLUMN_TITLE = "title";
    private static final String TERM_COLUMN_START = "start_date";
    private static final String TERM_COLUMN_END = "end_date";

    private static final String COURSE_TABLE_NAME = "course";
    private static final String COURSE_COLUMN_ID = "course_id";
    private static final String COURSE_COLUMN_TITLE = "title";
    private static final String COURSE_COLUMN_STATUS = "status";
    private static final String COURSE_COLUMN_START = "start_date";
    private static final String COURSE_COLUMN_END = "end_date";
    private static final String COURSE_COLUMN_TERM_ID = "term_id";

    private static final String MENTOR_TABLE_NAME = "mentor";
    private static final String MENTOR_COLUMN_ID = "mentor_id";
    private static final String MENTOR_COLUMN_NAME = "name";
    private static final String MENTOR_COLUMN_PHONE = "phone";
    private static final String MENTOR_COLUMN_EMAIL = "email";
    private static final String MENTOR_COLUMN_COURSE_ID = "course_id";

    private static final String NOTE_TABLE_NAME = "note";
    private static final String NOTE_COLUMN_ID = "note_id";
    private static final String NOTE_COLUMN_NOTE = "note";
    private static final String NOTE_COLUMN_COURSE_ID = "course_id";

    private static final String ASSESSMENT_TABLE_NAME = "assessment";
    private static final String ASSESSMENT_COLUMN_ID = "assessment_id";
    private static final String ASSESSMENT_COLUMN_TITLE = "title";
    private static final String ASSESSMENT_COLUMN_TYPE = "type";
    private static final String ASSESSMENT_COLUMN_DUE = "due_date";
    private static final String ASSESSMENT_COLUMN_COURSE_ID = "course_id";

    private static final String GOAL_DATE_TABLE_NAME = "goal_date";
    private static final String GOAL_DATE_COLUMN_ID = "goal_date_id";
    private static final String GOAL_DATE_COLUMN_DESCRIPTION = "description";
    private static final String GOAL_DATE_COLUMN_DATE = "date";
    private static final String GOAL_DATE_COLUMN_ASSESSMENT_ID = "assessment_id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Database onCreate methods
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TERM_TABLE_NAME + "(" +
                        TERM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TERM_COLUMN_TITLE + " TEXT NOT NULL," +
                        TERM_COLUMN_START + " TEXT NOT NULL," +
                        TERM_COLUMN_END + " TEXT NOT NULL);");

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + COURSE_TABLE_NAME + "(" +
                        COURSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COURSE_COLUMN_TITLE + " TEXT NOT NULL," +
                        COURSE_COLUMN_STATUS + " TEXT NOT NULL," +
                        COURSE_COLUMN_START + " TEXT NOT NULL," +
                        COURSE_COLUMN_END + " TEXT NOT NULL," +
                        COURSE_COLUMN_TERM_ID + " INTEGER NOT NULL," +
                        "FOREIGN KEY(" + COURSE_COLUMN_TERM_ID + ") " +
                        "REFERENCES " + TERM_TABLE_NAME + "(" + TERM_COLUMN_ID + "));");

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + MENTOR_TABLE_NAME + "(" +
                        MENTOR_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MENTOR_COLUMN_NAME + " TEXT NOT NULL," +
                        MENTOR_COLUMN_PHONE + " TEXT NOT NULL," +
                        MENTOR_COLUMN_EMAIL + " TEXT NOT NULL," +
                        MENTOR_COLUMN_COURSE_ID + " INTEGER NOT NULL," +
                        "FOREIGN KEY(" + MENTOR_COLUMN_COURSE_ID + ") " +
                        "REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_COLUMN_ID + "));");

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + NOTE_TABLE_NAME + "(" +
                        NOTE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        NOTE_COLUMN_NOTE + " TEXT NOT NULL," +
                        NOTE_COLUMN_COURSE_ID + " INTEGER NOT NULL," +
                        "FOREIGN KEY(" + NOTE_COLUMN_COURSE_ID + ") " +
                        "REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_COLUMN_ID + "));");

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + ASSESSMENT_TABLE_NAME + "(" +
                        ASSESSMENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ASSESSMENT_COLUMN_TITLE + " TEXT NOT NULL," +
                        ASSESSMENT_COLUMN_TYPE + " TEXT NOT NULL," +
                        ASSESSMENT_COLUMN_DUE + " TEXT NOT NULL," +
                        ASSESSMENT_COLUMN_COURSE_ID + " INTEGER NOT NULL," +
                        "FOREIGN KEY(" + ASSESSMENT_COLUMN_COURSE_ID + ") " +
                        "REFERENCES " + COURSE_TABLE_NAME + "(" + COURSE_COLUMN_ID + "));");

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + GOAL_DATE_TABLE_NAME + "(" +
                        GOAL_DATE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        GOAL_DATE_COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                        GOAL_DATE_COLUMN_DATE + " TEXT NOT NULL," +
                        GOAL_DATE_COLUMN_ASSESSMENT_ID + " INTEGER NOT NULL," +
                        "FOREIGN KEY(" + GOAL_DATE_COLUMN_ASSESSMENT_ID + ")" +
                        "REFERENCES " + ASSESSMENT_TABLE_NAME + "(" + ASSESSMENT_COLUMN_ID + "));");

    }

    //Database onUpgrade methods
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TERM_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MENTOR_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ASSESSMENT_TABLE_NAME + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GOAL_DATE_TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }


    public void onUpgrade1() {
        onUpgrade(this.getWritableDatabase(), 1, 1);
    }

    //Add Methods
    public boolean addTerm(String title, String start, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TERM_COLUMN_TITLE, title);
        cv.put(TERM_COLUMN_START, start);
        cv.put(TERM_COLUMN_END, end);
        db.insert(TERM_TABLE_NAME, null, cv);
        return true;
    }

    public boolean addCourse(
            String title,
            String start,
            String end,
            String status,
            int termId,
            ArrayList<Mentor> mentors
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COURSE_COLUMN_TITLE, title);
        cv.put(COURSE_COLUMN_START, start);
        cv.put(COURSE_COLUMN_END, end);
        cv.put(COURSE_COLUMN_STATUS, status);
        cv.put(COURSE_COLUMN_TERM_ID, termId);
        long mCourseId = db.insert(COURSE_TABLE_NAME, null, cv);
        for (Mentor mentor : mentors) {
            ContentValues cv1 = new ContentValues();
            cv1.put(MENTOR_COLUMN_NAME, mentor.getmName());
            cv1.put(MENTOR_COLUMN_PHONE, mentor.getmPhone());
            cv1.put(MENTOR_COLUMN_EMAIL, mentor.getmEmail());
            cv1.put(MENTOR_COLUMN_COURSE_ID, mCourseId);
            db.insert(MENTOR_TABLE_NAME, null, cv1);
        }
        return true;
    }

    public boolean addAssessment(
            String title,
            String type,
            String due,
            int courseId,
            ArrayList<GoalDate> goalDates

    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ASSESSMENT_COLUMN_TITLE, title);
        cv.put(ASSESSMENT_COLUMN_TYPE, type);
        cv.put(ASSESSMENT_COLUMN_DUE, due);
        cv.put(ASSESSMENT_COLUMN_COURSE_ID, courseId);
        long mAssessmentId = db.insert(ASSESSMENT_TABLE_NAME, null, cv);
        for (GoalDate goalDate : goalDates) {
            ContentValues cv1 = new ContentValues();
            cv1.put(GOAL_DATE_COLUMN_DESCRIPTION, goalDate.getmDescription());
            cv1.put(GOAL_DATE_COLUMN_DATE, goalDate.getmDate());
            cv1.put(GOAL_DATE_COLUMN_ASSESSMENT_ID, mAssessmentId);
            db.insert(GOAL_DATE_TABLE_NAME, null, cv1);
        }
        return true;
    }

    public void addNote(int courseId, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_COLUMN_COURSE_ID, courseId);
        cv.put(NOTE_COLUMN_NOTE, note);
        db.insert(NOTE_TABLE_NAME, null, cv);
    }


    //Update Methods
    public boolean updateTerm(int id, String title, String start, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TERM_COLUMN_TITLE, title);
        cv.put(TERM_COLUMN_START, start);
        cv.put(TERM_COLUMN_END, end);
        db.update(
                TERM_TABLE_NAME,
                cv,
                TERM_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) }
        );
        return true;
    }

    public boolean updateCourse(
            int id,
            String title,
            String start,
            String end,
            String status,
            ArrayList<Mentor> mentors
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COURSE_COLUMN_TITLE, title);
        cv.put(COURSE_COLUMN_START, start);
        cv.put(COURSE_COLUMN_END, end);
        cv.put(COURSE_COLUMN_STATUS, status);
        db.update(
                COURSE_TABLE_NAME,
                cv,
                COURSE_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) }
        );
        db.delete(MENTOR_TABLE_NAME, MENTOR_COLUMN_COURSE_ID + " = " + id, null);
        for (Mentor mentor : mentors) {
            ContentValues cv1 = new ContentValues();
            cv1.put(MENTOR_COLUMN_NAME, mentor.getmName());
            cv1.put(MENTOR_COLUMN_PHONE, mentor.getmPhone());
            cv1.put(MENTOR_COLUMN_EMAIL, mentor.getmEmail());
            cv1.put(MENTOR_COLUMN_COURSE_ID, id);
            db.insert(MENTOR_TABLE_NAME, null, cv1);
        }
        return true;
    }

    public boolean updateAssessment(
            int id,
            String title,
            String type,
            String due,
            ArrayList<GoalDate> goalDates
    ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ASSESSMENT_COLUMN_TITLE, title);
        cv.put(ASSESSMENT_COLUMN_TYPE, type);
        cv.put(ASSESSMENT_COLUMN_DUE, due);
        db.update(
                ASSESSMENT_TABLE_NAME,
                cv,
                ASSESSMENT_COLUMN_ID + " = ?",
                new String[] { Integer.toString(id) }
        );
        db.delete(GOAL_DATE_TABLE_NAME, GOAL_DATE_COLUMN_ASSESSMENT_ID + " = " + id, null);
        for (GoalDate goalDate : goalDates) {
            ContentValues cv1 = new ContentValues();
            cv1.put(GOAL_DATE_COLUMN_DESCRIPTION, goalDate.getmDescription());
            cv1.put(GOAL_DATE_COLUMN_DATE, goalDate.getmDate());
            cv1.put(GOAL_DATE_COLUMN_ASSESSMENT_ID, id);
            db.insert(GOAL_DATE_TABLE_NAME, null, cv1);
        }
        return true;
    }

    public void updateNote(int id, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_COLUMN_NOTE, note);
        db.update(
                NOTE_TABLE_NAME,
                cv,
                NOTE_COLUMN_ID + " = ?",
                new String[] { Integer.toString(id)}
        );
    }

    //Delete Method
    public boolean deleteTerm(int termId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TERM_TABLE_NAME, TERM_COLUMN_ID + " = " + termId, null);
        return true;
    }

    public boolean deleteCourse(int courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COURSE_TABLE_NAME, COURSE_COLUMN_ID + " = " + courseId, null);
        db.delete(ASSESSMENT_TABLE_NAME, ASSESSMENT_COLUMN_COURSE_ID + " = " + courseId, null);
        db.delete(NOTE_TABLE_NAME, NOTE_COLUMN_COURSE_ID + " = " + courseId, null);
        return true;
    }

    public boolean deleteAssessment(int assessmentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ASSESSMENT_TABLE_NAME, ASSESSMENT_COLUMN_ID + " = " + assessmentId, null);
        db.delete(GOAL_DATE_TABLE_NAME, GOAL_DATE_COLUMN_ASSESSMENT_ID + " = " + assessmentId, null);
        return true;
    }

    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTE_TABLE_NAME, NOTE_COLUMN_ID + " = " + noteId, null);
    }

    //Get Methods
    public Term getTerm(int termId) {
        Term term;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + TERM_TABLE_NAME + " WHERE " + TERM_COLUMN_ID + " = " + termId,
                null
        );
        res.moveToFirst();
        term = new Term(
                res.getInt(0),
                res.getString(1),
                res.getString(2),
                res.getString(3)
        );
        res.close();
        return term;
    }

    public Course getCourse(int courseId) {
        Course course;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COURSE_COLUMN_ID + " = " + courseId,
                null
        );
        res.moveToFirst();
        course = new Course(
                res.getInt(0),
                res.getString(1),
                res.getString(2),
                res.getString(3),
                res.getString(4),
                res.getInt(5)
        );
        res.close();
        return course;
    }

    public Assessment getAssessment(int assessmentId) {
        Assessment assessment;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + ASSESSMENT_TABLE_NAME + " WHERE " + ASSESSMENT_COLUMN_ID + " = " + assessmentId,
                null
        );
        res.moveToFirst();
        assessment = new Assessment(
                res.getInt(0),
                res.getString(1),
                res.getString(2),
                res.getString(3),
                res.getInt(4)
        );
        res.close();
        return assessment;
    }

    public ArrayList<Term> getTerms() {
        ArrayList<Term> a = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TERM_TABLE_NAME, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int mId = res.getInt(0);
            String mTitle = res.getString(1);
            String mStart = res.getString(2);
            String mEnd = res.getString(3);
            a.add(new Term(mId, mTitle, mStart, mEnd));
            res.moveToNext();
        }
        res.close();
        return a;
    }

    public ArrayList<Course> getCourses(int termId) {
        ArrayList<Course> a = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + COURSE_TABLE_NAME + " WHERE " + COURSE_COLUMN_TERM_ID + " = " + termId,
                null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int mId = res.getInt(0);
            String mTitle = res.getString(1);
            String mStatus = res.getString(2);
            String mStart = res.getString(3);
            String mEnd = res.getString(4);
            int mTermId = res.getInt(5);
            a.add(new Course(mId, mTitle, mStatus, mStart, mEnd, mTermId));
            res.moveToNext();
        }
        res.close();
        return a;
    }

    public ArrayList<Mentor> getMentors(int courseId) {
        ArrayList<Mentor> a = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + MENTOR_TABLE_NAME + " WHERE " + MENTOR_COLUMN_COURSE_ID + " = " + courseId,
                null
        );
        res.moveToFirst();
        while (!res.isAfterLast()) {
            String mName = res.getString(1);
            String mPhone = res.getString(2);
            String mEmail = res.getString(3);
            a.add(new Mentor(mName, mPhone, mEmail));
            res.moveToNext();
        }
        res.close();
        return a;
    }

    public ArrayList<Assessment> getAssessments(int courseId) {
        ArrayList<Assessment> a = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + ASSESSMENT_TABLE_NAME + " WHERE " + ASSESSMENT_COLUMN_COURSE_ID + " = " + courseId,
                null
        );
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int mId = res.getInt(0);
            String mTitle = res.getString(1);
            String mType = res.getString(2);
            String mDue = res.getString(3);
            int mCourseId = res.getInt(4);
            a.add(new Assessment(mId, mTitle, mType, mDue, mCourseId));
            res.moveToNext();
        }
        res.close();
        return a;
    }

    public ArrayList<Note> getNotes(int courseId) {
        ArrayList<Note> a = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + NOTE_TABLE_NAME + " WHERE " + NOTE_COLUMN_COURSE_ID + " = " + courseId,
                null
        );
        res.moveToFirst();
        while (!res.isAfterLast()) {
            int mId = res.getInt(0);
            String mNote = res.getString(1);
            int mCourseId = res.getInt(2);
            a.add(new Note(mId, mNote, mCourseId));
            res.moveToNext();
        }
        res.close();
        return a;
    }

    public ArrayList<GoalDate> getGoalDates(int assessmentId) {
        ArrayList<GoalDate> a = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + GOAL_DATE_TABLE_NAME + " WHERE " + GOAL_DATE_COLUMN_ASSESSMENT_ID + " = " + assessmentId,
                null
        );
        res.moveToFirst();
        while (!res.isAfterLast()) {
            String mDescription = res.getString(1);
            String mDate = res.getString(2);
            a.add(new GoalDate(mDescription, mDate));
            res.moveToNext();
        }
        res.close();
        return a;
    }
}