package com.example.wgutermmanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.wgutermmanager.Adapters.CourseListAdapter;
import com.example.wgutermmanager.DBHelper;
import com.example.wgutermmanager.Models.Course;
import com.example.wgutermmanager.Models.Term;
import com.example.wgutermmanager.R;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private Term mTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        setTitle("Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDBHelper = new DBHelper(this);
        Intent i = getIntent();
        mTerm = mDBHelper.getTerm(((Term) i.getSerializableExtra("term")).getmTermId());
        setCourseListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                return switchActivity(AddCourseActivity.class, item.getTitle().toString(), null);
            case android.R.id.home:
                return switchActivity(TermDetailActivity.class, null, null);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setCourseListView() {
        final ArrayList<Course> mCourseList = mDBHelper.getCourses(mTerm.getmTermId());
        ListAdapter mAdapter = new CourseListAdapter(this, mCourseList);
        ListView mListView = findViewById(R.id.list_view_courses);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Course mCourse = (Course) adapterView.getAdapter().getItem(i);
                        switchActivity(CourseDetailActivity.class, null, mCourse);
                    }
                }
        );
    }

    private boolean switchActivity(Class<?> cls, String type, Course course) {
        Intent i = new Intent(this, cls);
        if (cls.equals(AddCourseActivity.class)) {
            i.putExtra("type", type);
        } else if (cls.equals(CourseDetailActivity.class)) {
            i.putExtra("course", course);
        }
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }
}
