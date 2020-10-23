package com.example.wgutermmanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wgutermmanager.Models.Term;
import com.example.wgutermmanager.R;

import java.util.ArrayList;

public class TermListAdapter extends ArrayAdapter<Term> {

    public TermListAdapter(@NonNull Context context, ArrayList<Term> terms) {
        super(context, R.layout.list_row_term, terms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_term, parent, false);
            Term mSingleTermItem = getItem(position);
            TextView mTextTerm = convertView.findViewById(R.id.text_term);
            mTextTerm.setText(mSingleTermItem.getmTitle());
        }
        return convertView;
    }
}
