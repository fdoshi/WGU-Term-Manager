package com.example.wgutermmanager.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wgutermmanager.Adapters.GoalDateListAdapter;
import com.example.wgutermmanager.AlertReceiver;
import com.example.wgutermmanager.DBHelper;
import com.example.wgutermmanager.Models.Assessment;
import com.example.wgutermmanager.Models.Course;
import com.example.wgutermmanager.Models.GoalDate;
import com.example.wgutermmanager.Models.Term;
import com.example.wgutermmanager.R;
import com.example.wgutermmanager.Utility;

import java.io.Serializable;
import java.util.ArrayList;

public class AssessmentDetailActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private Assessment mAssessment;
    private Term mTerm;
    private Course mCourse;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Assessment Details");
        mDBHelper = new DBHelper(this);
        mIntent = getIntent();
        mAssessment = mDBHelper.getAssessment(((Assessment) mIntent.getSerializableExtra("assessment")).getmAssessmentId());
        mTerm = mDBHelper.getTerm(((Term) mIntent.getSerializableExtra("term")).getmTermId());
        mCourse = mDBHelper.getCourse(((Course) mIntent.getSerializableExtra("course")).getmCourseId());
        setTextViews();
        setGoalDateListView();
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
                return switchActivity(AssessmentListActivity.class, null);
            case R.id.menu_edit:
                return switchActivity(AddAssessmentActivity.class, item.getTitle().toString());
            case R.id.menu_delete:
                return deleteAssessment();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean switchActivity(Class<?> cls, String type) {
        Intent i = new Intent(this, cls);
        if (cls.equals(AddAssessmentActivity.class)) {
            i.putExtra("type", type);
            i.putExtra("assessment", mAssessment);
        }
        i.putExtra("term", mTerm);
        i.putExtra("course", mCourse);
        startActivity(i);
        return true;
    }

    private void setTextViews() {
        TextView mTitle = findViewById(R.id.assessment_title);
        TextView mType = findViewById(R.id.assessment_type_value);
        TextView mDue = findViewById(R.id.assessment_due_date_value);
        mTitle.setText(mAssessment.getmTitle());
        mType.setText(mAssessment.getmType());
        mDue.setText(mAssessment.getmDue());
    }

    private boolean deleteAssessment() {
        if (mDBHelper.deleteAssessment(mAssessment.getmAssessmentId())) {
            Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        }
        switchActivity(AssessmentListActivity.class, null);
        return true;
    }

    private void setGoalDateListView() {
        ArrayList<GoalDate> mGoalDateList = mDBHelper.getGoalDates(mAssessment.getmAssessmentId());
        final ListView mListView = findViewById(R.id.list_view_goal_dates);
        ListAdapter mAdapter = new GoalDateListAdapter(this, mGoalDateList);
        mListView.setAdapter(mAdapter);
        Switch mAlert = findViewById(R.id.goal_switch);
        mAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    String message = "New goal starts today.";
                    long  date = Utility.getTimeInMSec(mCourse.getmStart());
                    enableNotification(message, date);
                    Toast.makeText(AssessmentDetailActivity.this, "New goal notification is set", Toast.LENGTH_SHORT).show();

                } else {
                    // The toggle is disabled
                }
            }
        });


    }

    private boolean enableNotification (String message, Long time){
        Intent i = new Intent(AssessmentDetailActivity.this, AlertReceiver.class );
        i.putExtra("key", message);
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, 0, i,0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //Calendar c = Calendar.getInstance();
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, sender);
        return true;
    }
}
