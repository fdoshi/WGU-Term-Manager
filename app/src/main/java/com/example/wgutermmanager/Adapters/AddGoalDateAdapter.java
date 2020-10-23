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

import com.example.wgutermmanager.Models.GoalDate;
import com.example.wgutermmanager.R;
import com.example.wgutermmanager.Utility;

import java.util.ArrayList;

public class AddGoalDateAdapter extends ArrayAdapter<GoalDate> {

    private ArrayList<GoalDate> mGoalDates;
    private Context mContext;

    public AddGoalDateAdapter(@NonNull Context context, ArrayList<GoalDate> goalDates) {
        super(context, R.layout.list_row_add_goal_date, goalDates);
        mGoalDates = goalDates;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_add_goal_date, parent, false);
        }
        GoalDate mSingleGoalDateItem = getItem(position);
        TextView mDescription = convertView.findViewById(R.id.description);
        TextView mDate = convertView.findViewById(R.id.date);
        mDescription.setText(mSingleGoalDateItem.getmDescription());
        mDate.setText(mSingleGoalDateItem.getmDate());
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
                mGoalDates.remove(p);
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
                View addGoalDateDialog = li.inflate(R.layout.dialog_goal_date, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
                adb.setView(addGoalDateDialog);
                final EditText descriptionInput = addGoalDateDialog.findViewById(R.id.description);
                final EditText dateInput = addGoalDateDialog.findViewById(R.id.date);
                descriptionInput.setText(mGoalDates.get(p).getmDescription());
                dateInput.setText(mGoalDates.get(p).getmDate());
                adb
                        .setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (descriptionInput.getText().toString().trim().isEmpty()) {
                                            Toast.makeText(mContext, "Fields cannot be blank", Toast.LENGTH_SHORT).show();
                                        } else if (!Utility.checkDate(dateInput.getText().toString())) {
                                            Toast.makeText(mContext, "Invalid date format", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mGoalDates.get(p).setmDescription(descriptionInput.getText().toString());
                                            mGoalDates.get(p).setmDate(dateInput.getText().toString());
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
