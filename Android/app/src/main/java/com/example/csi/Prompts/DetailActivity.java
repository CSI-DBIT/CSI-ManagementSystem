package com.example.csi.Prompts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.csi.R;

import static com.example.csi.mFragments.MinuteManager.EXTRA_AGENDA;
import static com.example.csi.mFragments.MinuteManager.EXTRA_CREATOR;
import static com.example.csi.mFragments.MinuteManager.EXTRA_DATE;
import static com.example.csi.mFragments.MinuteManager.EXTRA_POINTS;
import static com.example.csi.mFragments.MinuteManager.EXTRA_TIME;


public class DetailActivity extends Fragment {
    TextView mAgenda, mDate, mTime, mCreator, mPoints;
    String agenda, date, time, creator, points;

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

        Bundle bundle = getArguments();

        agenda = bundle.getString(EXTRA_AGENDA);
        date = bundle.getString(EXTRA_DATE);
        time = bundle.getString(EXTRA_TIME);
        creator = bundle.getString(EXTRA_CREATOR);
        points = bundle.getString(EXTRA_POINTS);

        mAgenda.setText("Agenda: "+agenda);
        mDate.setText("Date: "+date);
        mTime.setText("Time: "+time);
        mCreator.setText("Creator: "+creator);
        mPoints.setText("Points: "+points);

        return rootView;
    }


}
