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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.example.wgutermmanager.Adapters.NoteListAdapter;
import com.example.wgutermmanager.DBHelper;
import com.example.wgutermmanager.Models.Course;
import com.example.wgutermmanager.Models.Note;
import com.example.wgutermmanager.Models.Term;
import com.example.wgutermmanager.R;

import java.io.Serializable;
import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    private Course mCourse;
    private Term mTerm;
    private DBHelper mDBHelper;
    private EditText mNoteInput;
    private ArrayList<Note> mNoteList;
    private NoteListAdapter mAdapter;
    private final Context mContext = this;
    private ShareActionProvider mSap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle("Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        mDBHelper = new DBHelper(this);
        mCourse = mDBHelper.getCourse(((Course) i.getSerializableExtra("course")).getmCourseId());
        mTerm = mDBHelper.getTerm(((Term) i.getSerializableExtra("term")).getmTermId());
        mNoteInput = findViewById(R.id.note_input);
        mNoteList = mDBHelper.getNotes(mCourse.getmCourseId());
        mAdapter = new NoteListAdapter(this, mNoteList);
        setNotesListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                return switchActivity(CourseDetailActivity.class);
            case R.id.menu_share:
                return shareNotes();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean switchActivity(Class<?> cls) {
        Intent i = new Intent(this, cls);
        i.putExtra("course", (Serializable) mCourse);
        i.putExtra("term", (Serializable) mTerm);
        startActivity(i);
        return true;
    }

    public void addNoteClick(View view) {
        if (mNoteInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note field is empty", Toast.LENGTH_SHORT).show();
        } else {
            mDBHelper.addNote(mCourse.getmCourseId(), mNoteInput.getText().toString());
        }
    }

    private void setNotesListView() {
        ListView mListView = findViewById(R.id.list_view_notes);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        editNoteDialog(i);
                    }
                }
        );
    }

    private void editNoteDialog(final int p) {
        LayoutInflater li = LayoutInflater.from(mContext);
        View addMentorDialog = li.inflate(R.layout.dialog_note, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
        adb.setView(addMentorDialog);
        final EditText noteInput = addMentorDialog.findViewById(R.id.input_note);
        noteInput.setText(mNoteList.get(p).getmNote());
        adb
                .setCancelable(false)
                .setPositiveButton("SAVE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (noteInput.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(mContext, "Field cannot be blank", Toast.LENGTH_SHORT).show();
                                } else {
                                    mDBHelper.updateNote(mNoteList.get(p).getmNoteId(), noteInput.getText().toString());
                                    updateListView();
                                }
                            }
                        })
                .setNeutralButton("DELETE",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDBHelper.deleteNote(mNoteList.get(p).getmNoteId());
                                updateListView();
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

    private void updateListView() {
        mNoteList.clear();
        mNoteList.addAll(mDBHelper.getNotes(mCourse.getmCourseId()));
        mAdapter.notifyDataSetChanged();
    }

    private boolean shareNotes() {
        StringBuilder extraText = new StringBuilder("");
        for (Note note : mNoteList) {
            extraText.append(note.getmNote());
            extraText.append("\n");
        }
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Course Notes");
        i.putExtra(Intent.EXTRA_TEXT, extraText.toString());
        startActivity(Intent.createChooser(i, "Share via"));
        return true;
    }
}
