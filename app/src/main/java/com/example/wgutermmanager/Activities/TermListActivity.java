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

import com.example.wgutermmanager.Adapters.TermListAdapter;
import com.example.wgutermmanager.DBHelper;
import com.example.wgutermmanager.Models.Term;
import com.example.wgutermmanager.R;

import java.io.Serializable;
import java.util.ArrayList;

public class TermListActivity extends AppCompatActivity {

    public static final String TAG = "TermListActivity";

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        setTitle("Terms");
        mDBHelper = new DBHelper(this);
        setTermListView();
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
                return switchActivity(AddTermActivity.class, item.getTitle().toString(), null);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setTermListView() {
        final ArrayList<Term> mTermList = mDBHelper.getTerms();
        ListAdapter mAdapter = new TermListAdapter(this, mTermList);
        ListView mListView = findViewById(R.id.list_view_terms);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Term mTerm = (Term) adapterView.getAdapter().getItem(i);
                        switchActivity(TermDetailActivity.class, null, mTerm);
                    }
                }
        );
    }

    private boolean switchActivity(Class<?> cls, String type, Term term) {
        Intent i = new Intent(this, cls);
        if (cls.equals(AddTermActivity.class)) {
            i.putExtra("type", type);
        } else {
            i.putExtra("term",  term);
        }
        startActivity(i);
        return true;
    }


}
