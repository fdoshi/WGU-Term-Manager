package com.example.wgutermmanager.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.wgutermmanager.Adapters.MentorListAdapter;
import com.example.wgutermmanager.AlertReceiver;
import com.example.wgutermmanager.DBHelper;
import com.example.wgutermmanager.Models.Course;
import com.example.wgutermmanager.Models.Mentor;
import com.example.wgutermmanager.Models.Term;
import com.example.wgutermmanager.R;
import com.example.wgutermmanager.Utility;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class CourseDetailActivity extends AppCompatActivity {

    private Course mCourse;
    private DBHelper mDBHelper;
    private Term mTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Course Details");
        Intent i = getIntent();
        mDBHelper = new DBHelper(this);
        mTerm = mDBHelper.getTerm(((Term) i.getSerializableExtra("term")).getmTermId());
        mCourse = mDBHelper.getCourse(((Course) i.getSerializableExtra("course")).getmCourseId());
        TextView mTitle = findViewById(R.id.course_title);
        final TextView mStart = findViewById(R.id.course_start_date_value);
        TextView mEnd = findViewById(R.id.course_end_date_value);
        TextView mStatus = findViewById(R.id.course_status_value);
        mTitle.setText(mCourse.getmTitle());
        mStart.setText(mCourse.getmStart());
        mEnd.setText(mCourse.getmEnd());
        mStatus.setText(mCourse.getmStatus());
        setMentorListView();
        //Course Start Notification........
        Switch courseStartAlert = findViewById(R.id.switch_start_alert);
        courseStartAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    String message = "New course Starts today";
                    long  date = Utility.getTimeInMSec(mCourse.getmStart());
                    enableNotification(message, date);
                    Toast.makeText(CourseDetailActivity.this, "Course start notification is set", Toast.LENGTH_SHORT).show();

                } else {
                    // The toggle is disabled
                }
            }
        });

        //Course End Notification......
        Switch courseEndAlert = findViewById(R.id.switch_end_alert);
        courseEndAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    String message = "course Ends today";
                    long  date = Utility.getTimeInMSec(mCourse.getmStart());
                    enableNotification(message, date);
                    Toast.makeText(CourseDetailActivity.this, "Course end notification is set", Toast.LENGTH_SHORT).show();

                } else {
                    // The toggle is disabled
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                return switchActivity(CourseListActivity.class, null);
            case R.id.menu_edit:
                return switchActivity(AddCourseActivity.class, item.getTitle().toString());
            case R.id.menu_delete:
                return deleteCourse();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void viewAssessmentsButtonClick(View view) {
        switchActivity(AssessmentListActivity.class, null);
    }

    public void viewNotesButtonClick(View view)
    {
        switchActivity(NoteActivity.class, null);
    }

    private void setMentorListView() {
        ArrayList<Mentor> mMentorList = mDBHelper.getMentors(mCourse.getmCourseId());
        ListView mListView = findViewById(R.id.list_view_mentors);
        ListAdapter mAdapter = new MentorListAdapter(this, mMentorList);
        mListView.setAdapter(mAdapter);
    }

    private boolean switchActivity(Class<?> cls, String type) {
        Intent i = new Intent(this, cls);
        if (cls.equals(AssessmentListActivity.class) ||
                cls.equals(NoteActivity.class)) {
            i.putExtra("course", (Serializable) mCourse);
        } else if (cls.equals(AddCourseActivity.class)) {
            i.putExtra("type", type);
            i.putExtra("course", (Serializable) mCourse);
        }
        i.putExtra("term", (Serializable) mTerm);
        startActivity(i);
        return true;
    }

    private boolean deleteCourse() {
        if (mDBHelper.deleteCourse(mCourse.getmCourseId())) {
            Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        }
        switchActivity(CourseListActivity.class, null);
        return true;
    }

    private boolean enableNotification (String message, Long time){
        Intent i = new Intent(CourseDetailActivity.this, AlertReceiver.class );
        i.putExtra("key", message);
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetailActivity.this, 0, i,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //Calendar c = Calendar.getInstance();
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, sender);
        return true;
    }
}
