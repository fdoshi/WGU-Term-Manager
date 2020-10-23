package com.example.wgutermmanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.wgutermmanager.Models.GoalDate;
import com.example.wgutermmanager.R;
import com.example.wgutermmanager.Utility;

import java.util.ArrayList;

public class GoalDateListAdapter extends ArrayAdapter<GoalDate> {

    public GoalDateListAdapter(@NonNull Context context, ArrayList<GoalDate> goalDates) {
        super(context, R.layout.list_row_goal_date, goalDates);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_goal_date, parent, false);
            GoalDate mGoalDateSingleItem = getItem(position);
            TextView mDescription = convertView.findViewById(R.id.goal_date_list_description);
            TextView mDate = convertView.findViewById(R.id.goal_date_list_date);
            mDescription.setText(mGoalDateSingleItem.getmDescription());
            mDate.setText(mGoalDateSingleItem.getmDate());

        }
        return convertView;
    }
}
