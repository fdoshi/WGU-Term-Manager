package com.example.wgutermmanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wgutermmanager.Models.Assessment;
import com.example.wgutermmanager.R;

import java.util.ArrayList;

public class AssessmentListAdapter extends ArrayAdapter<Assessment> {

    public AssessmentListAdapter(@NonNull Context context, ArrayList<Assessment> assessments) {
        super(context, R.layout.list_row_assessment, assessments);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_assessment, parent, false);
            Assessment mSingleAssessmentItem = getItem(position);
            TextView mTextAssessment = convertView.findViewById(R.id.text_assessment);
            mTextAssessment.setText(mSingleAssessmentItem.getmTitle());
        }
        return convertView;
    }
}
