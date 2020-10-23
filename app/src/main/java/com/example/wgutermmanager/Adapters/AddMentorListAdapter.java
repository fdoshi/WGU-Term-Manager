package com.example.wgutermmanager.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wgutermmanager.Models.Mentor;
import com.example.wgutermmanager.R;

import java.util.ArrayList;

public class AddMentorListAdapter extends ArrayAdapter<Mentor> {
    private ArrayList<Mentor> mMentors;
    private Context mContext;

    public AddMentorListAdapter(@NonNull Context context, ArrayList<Mentor> mentors) {
        super(context, R.layout.list_row_add_mentor, mentors);
        mMentors = mentors;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_add_mentor, parent, false);
        }
        Mentor mSingleMentorItem = getItem(position);
        TextView mName = convertView.findViewById(R.id.mentor_list_name);
        TextView mPhone = convertView.findViewById(R.id.mentor_list_phone);
        TextView mEmail = convertView.findViewById(R.id.mentor_list_email);
        mName.setText(mSingleMentorItem.getmName());
        mPhone.setText(mSingleMentorItem.getmPhone());
        mEmail.setText(mSingleMentorItem.getmEmail());
        Button mEditButton = convertView.findViewById(R.id.button_edit);
        Button mDeleteButton = convertView.findViewById(R.id.button_delete);
        mEditButton.setOnClickListener(editButtonClickListener(position));
        mDeleteButton.setOnClickListener(deleteButtonClickListener(position));
        return convertView;
    }

    private View.OnClickListener deleteButtonClickListener(final int p) {
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMentors.remove(p);
                notifyDataSetChanged();
            }
        };
        return l;
    }

    private View.OnClickListener editButtonClickListener(final int p) {
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(mContext);
                View addMentorDialog = li.inflate(R.layout.dialog_mentor, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
                adb.setView(addMentorDialog);
                final EditText nameInput = addMentorDialog.findViewById(R.id.input_name);
                final EditText phoneInput = addMentorDialog.findViewById(R.id.input_phone);
                final EditText emailInput = addMentorDialog.findViewById(R.id.input_email);
                nameInput.setText(mMentors.get(p).getmName());
                phoneInput.setText(mMentors.get(p).getmPhone());
                emailInput.setText(mMentors.get(p).getmEmail());
                adb
                        .setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (nameInput.getText().toString().trim().isEmpty() ||
                                                phoneInput.getText().toString().trim().isEmpty() ||
                                                emailInput.getText().toString().trim().isEmpty()) {
                                            Toast.makeText(mContext, "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mMentors.get(p).setmName(nameInput.getText().toString());
                                            mMentors.get(p).setmPhone(phoneInput.getText().toString());
                                            mMentors.get(p).setmEmail(emailInput.getText().toString());
                                        }
                                        notifyDataSetChanged();
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
        };
        return l;
    }
}
