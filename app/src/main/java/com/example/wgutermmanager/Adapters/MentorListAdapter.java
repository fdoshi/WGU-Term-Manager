package com.example.wgutermmanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wgutermmanager.Models.Mentor;
import com.example.wgutermmanager.R;

import java.util.ArrayList;

public class MentorListAdapter extends ArrayAdapter<Mentor> {
    public MentorListAdapter(@NonNull Context context, ArrayList<Mentor> mentors) {
        super(context, R.layout.list_row_mentor, mentors);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_mentor, parent, false);
            Mentor mSingleMentorItem = getItem(position);
            TextView mName = convertView.findViewById(R.id.mentor_list_name);
            TextView mPhone = convertView.findViewById(R.id.mentor_list_phone);
            TextView mEmail = convertView.findViewById(R.id.mentor_list_email);
            mName.setText(mSingleMentorItem.getmName());
            mPhone.setText(mSingleMentorItem.getmPhone());
            mEmail.setText(mSingleMentorItem.getmEmail());
        }
        return convertView;
    }
}
