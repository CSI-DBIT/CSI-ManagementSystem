package com.example.csi.Prompts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.csi.R;

import java.util.ArrayList;

import static com.example.csi.mFragments.MinuteManager.EXTRA_AGENDA;
import static com.example.csi.mFragments.MinuteManager.EXTRA_CREATOR;
import static com.example.csi.mFragments.MinuteManager.EXTRA_DATE;
import static com.example.csi.mFragments.MinuteManager.EXTRA_PERSON;
import static com.example.csi.mFragments.MinuteManager.EXTRA_POINTS;
import static com.example.csi.mFragments.MinuteManager.EXTRA_TASK;
import static com.example.csi.mFragments.MinuteManager.EXTRA_TIME;


public class DetailActivity extends Fragment {
    TextView mAgenda, mDate, mTime, mCreator, mPoints;
    String agenda, date, time, creator, points;
    ArrayList<String> task, person;
    TableLayout tableLayout;

    //In this method collect data from Minute Manager and display that data

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View rootView = inflater.inflate(R.layout.detail_layout,container,false);

        mAgenda = rootView.findViewById(R.id.final_agenda);
        mDate = rootView.findViewById(R.id.final_date);
        mTime = rootView.findViewById(R.id.final_time);
        mCreator = rootView.findViewById(R.id.final_creator);
        mPoints = rootView.findViewById(R.id.final_points);
        tableLayout = rootView.findViewById(R.id.display_table);

        Bundle bundle = getArguments();

        agenda = bundle.getString(EXTRA_AGENDA);
        date = bundle.getString(EXTRA_DATE);
        time = bundle.getString(EXTRA_TIME);
        creator = bundle.getString(EXTRA_CREATOR);
        points = bundle.getString(EXTRA_POINTS);
        task = bundle.getStringArrayList(EXTRA_TASK);
        person = bundle.getStringArrayList(EXTRA_PERSON);
        Log.i("sankey123", task.toString() + " " + person.toString());

        Log.i("sankey", task.toString() + " " + person.toString());

        mAgenda.setText("Agenda: "+agenda);
        mDate.setText("Date: "+date);
        mTime.setText("Time: "+time);
        mCreator.setText("Creator: "+creator);
        mPoints.setText("Points: "+points);

        for (int i=0; i<task.size(); i++) {
            TableRow tableRow = new TableRow(getContext());
            TextView tv1 = new TextView(getContext());
            TextView tv2 = new TextView(getContext());

            tv1.setText(task.get(i));
            tv1.setGravity(Gravity.CENTER);
            tv1.setBackgroundColor(getResources().getColor(R.color.white));
            tv1.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv1.setTextSize(17);
           // tv1.setBackground(getResources().getDrawable(R.drawable.tableborder)); //this will apply border

            TableRow.LayoutParams param = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            param.setMargins(1, 0, 1, 1);
            tv1.setLayoutParams(param);

            tv2.setText(person.get(i));
            tv2.setGravity(Gravity.CENTER);
            tv2.setBackgroundColor(getResources().getColor(R.color.white));
            tv2.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv2.setTextSize(17);

            TableRow.LayoutParams param1 = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            param1.setMargins(1, 0, 1, 1);
            tv2.setLayoutParams(param1);

            tableRow.addView(tv1);
            tableRow.addView(tv2);

            tableLayout.addView(tableRow);
        }

        return rootView;
    }


}
