package com.example.wgutermmanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wgutermmanager.DBHelper;
import com.example.wgutermmanager.Models.Course;
import com.example.wgutermmanager.Models.Term;
import com.example.wgutermmanager.R;

import java.io.Serializable;
import java.util.ArrayList;

public class TermDetailActivity extends AppCompatActivity {

    private Term mTerm;
    private DBHelper mDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        setTitle("Term Details");
        mDBHelper = new DBHelper(this);
        Intent i = getIntent();
        mTerm = mDBHelper.getTerm(((Term) i.getSerializableExtra("term")).getmTermId());
        setTextViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                return switchActivity(AddTermActivity.class, item.getTitle().toString());
            case R.id.menu_delete:
                return deleteTerm();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void viewCoursesButtonClick(View view) {
        switchActivity(CourseListActivity.class, null);
    }

    private void setTextViews() {
        TextView mTitle = findViewById(R.id.term_title);
        TextView mStart = findViewById(R.id.term_start_date_value);
        TextView mEnd = findViewById(R.id.term_end_date_value);
        mTitle.setText(mTerm.getmTitle());
        mStart.setText(mTerm.getmStart());
        mEnd.setText(mTerm.getmEnd());
    }

    private boolean switchActivity(Class<?> cls, String type) {
        Intent i = new Intent(this, cls);
        if (cls.equals(CourseListActivity.class)) {
            i.putExtra("term", (Serializable) mTerm);
        } else if (cls.equals(AddTermActivity.class)) {
            i.putExtra("type", type);
            i.putExtra("term", (Serializable) mTerm);
        }
        startActivity(i);
        return true;
    }

    private boolean deleteTerm() {
        ArrayList<Course> courses = mDBHelper.getCourses(mTerm.getmTermId());
        if (courses.size() > 0) {
            Toast.makeText(this, "The term has courses assigned to it and cannot be deleted", Toast.LENGTH_SHORT).show();
        } else {
            if (mDBHelper.deleteTerm(mTerm.getmTermId())) {
                Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
            }
            switchActivity(TermListActivity.class, null);
        }
        return true;
    }
}
