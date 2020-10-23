package com.example.wgutermmanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wgutermmanager.Models.Note;
import com.example.wgutermmanager.R;

import java.util.ArrayList;

public class NoteListAdapter extends ArrayAdapter<Note> {

    public NoteListAdapter(@NonNull Context context, ArrayList<Note> notes) {
        super(context, R.layout.list_row_note, notes);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_note, parent, false);
        }
        Note mSingleNoteItem = getItem(position);
        TextView mNote = convertView.findViewById(R.id.text_note);
        mNote.setText(mSingleNoteItem.getmNote());
        return convertView;
    }
}
