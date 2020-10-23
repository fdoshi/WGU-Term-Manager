package com.example.wgutermmanager.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wgutermmanager.Adapters.AddGoalDateAdapter;
import com.example.wgutermmanager.DBHelper;
import com.example.wgutermmanager.Models.Assessment;
import com.example.wgutermmanager.Models.Course;
import com.example.wgutermmanager.Models.GoalDate;
import com.example.wgutermmanager.Models.Term;
import com.example.wgutermmanager.R;
import com.example.wgutermmanager.Utility;

import java.io.Serializable;
import java.util.ArrayList;

public class AddAssessmentActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private Intent mIntent;
    private Course mCourse;
    private Term mTerm;
    private Assessment mAssessment;
    private ArrayList<GoalDate> mGoalDateList;
    private final Context context = this;

    private Spinner mSpinner;
    private EditText mTitleInput;
    private EditText mDueInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        mDBHelper = new DBHelper(this);
        mIntent = getIntent();
        mCourse = mDBHelper.getCourse(((Course) mIntent.getSerializableExtra("course")).getmCourseId());
        mTerm = mDBHelper.getTerm(((Term) mIntent.getSerializableExtra("term")).getmTermId());
        mAssessment = mIntent.getStringExtra("type").equals("ADD") ?
                null : mDBHelper.getAssessment(((Assessment) mIntent.getSerializableExtra("assessment")).getmAssessmentId());
        setGoalDateListView();
        Button mAddGoalDateButton = findViewById(R.id.button_add_goal_date);
        mAddGoalDateButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGoalDateDialog();
            }
        });

        String title = mIntent.getStringExtra("type").equals("ADD") ? "Add" : "Edit";
        setTitle(title + " Assessment");
        mSpinner = findViewById(R.id.type_spinner);
        mTitleInput = findViewById(R.id.title_input);
        mDueInput = findViewById(R.id.due_input);
        setSpinner();
        setInputs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                return saveAssessment();
            case R.id.menu_cancel:
                return switchActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean switchActivity() {
        String previous = mIntent.getStringExtra("type");
        Intent i;
        if (previous.equals("ADD")) {
            i = new Intent(this, AssessmentListActivity.class);
        } else {
            i = new Intent(this, AssessmentDetailActivity.class);
            i.putExtra("assessment", mAssessment);
        }
        i.putExtra("course", mCourse);
        i.putExtra("term", mTerm);
        startActivity(i);
        return true;
    }

    private void setInputs() {
        if (mIntent.getStringExtra("type").equals("EDIT")) {
            mTitleInput.setText(mAssessment.getmTitle());
            mDueInput.setText(mAssessment.getmDue());
        }
    }

    private void setGoalDateListView() {
        if (mIntent.getStringExtra("type").equals("ADD")) {
            mGoalDateList = new ArrayList<>();
        } else {
            mGoalDateList = mDBHelper.getGoalDates(mAssessment.getmAssessmentId());
        }
        ListView mListView = findViewById(R.id.list_view_goal_dates);
        ListAdapter mAdapter = new AddGoalDateAdapter(this, mGoalDateList);
        mListView.setAdapter(mAdapter);
    }

    private void addGoalDateDialog() {
        LayoutInflater li = LayoutInflater.from(context);
        View addGoalDateDialog = li.inflate(R.layout.dialog_goal_date, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setView(addGoalDateDialog);
        final EditText descriptionInput = addGoalDateDialog.findViewById(R.id.description);
        final EditText dateInput = addGoalDateDialog.findViewById(R.id.date);
        adb
                .setCancelable(false)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (descriptionInput.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(getBaseContext(), "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                                } else if (!Utility.checkDate(dateInput.getText().toString())) {
                                    Toast.makeText(getBaseContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                                } else {
                                    mGoalDateList.add(new GoalDate(
                                            descriptionInput.getText().toString(),
                                            dateInput.getText().toString()
                                    ));
                                }
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
        AlertDialog alertDialog = adb.create();
        alertDialog.show();
    }

    private void setSpinner() {
        ArrayList<String> a = new ArrayList<>();
        a.add("Objective");
        a.add("Performance");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, a);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        if (mAssessment != null) {
            int i = a.indexOf(mAssessment.getmType());
            mSpinner.setSelection(i);
        }
    }

    private boolean saveAssessment() {
        if (mTitleInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(getBaseContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!Utility.checkDate(mDueInput.getText().toString())) {
            Toast.makeText(getBaseContext(), "Invalid due date", Toast.LENGTH_SHORT).show();
        } else {
            if (mIntent.getStringExtra("type").equals("ADD")) {
                if (mDBHelper.addAssessment(
                        mTitleInput.getText().toString(),
                        mSpinner.getSelectedItem().toString(),
                        mDueInput.getText().toString(),
                        mCourse.getmCourseId(),
                        mGoalDateList
                )) {
                    Toast.makeText(getBaseContext(), "Add Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Add Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mDBHelper.updateAssessment(
                        mAssessment.getmAssessmentId(),
                        mTitleInput.getText().toString(),
                        mSpinner.getSelectedItem().toString(),
                        mDueInput.getText().toString(),
                        mGoalDateList
                )) {
                    Toast.makeText(getBaseContext(), "Edit Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Edit Failed", Toast.LENGTH_SHORT).show();
                }
            }
            switchActivity();
        }
        return true;
    }
}
