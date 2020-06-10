package com.example.csi.mFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.csi.Gallery.Activities.DisplayEventName;
import com.example.csi.Prompts.MainActivity;
import com.example.csi.Prompts.Manager;
import com.example.csi.R;
import com.example.csi.SharedPreferenceConfig;
import com.example.csi.ViewPagerAdapter;
import com.example.csi.mActivityManager.Creative;
import com.example.csi.mActivityManager.Proposal;
import com.example.csi.mActivityManager.Publicity;
import com.example.csi.mActivityManager.Report;
import com.example.csi.mActivityManager.Technical;
import com.example.csi.mActivityManager.praposal_recycler;
import com.example.csi.mActivityManager.publcity_recycler;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class ActivityManager extends Fragment  {

    View rootView;
    GridLayout mainGrid;
    //below 4 objects are used for image slider
    ViewPager viewPager;
    LinearLayout sliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;

    public  static ActivityManager newInstance()
    {
        return new ActivityManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.manager_activity,container,false);
//        ScrollView sv = new ScrollView(this);
//        sv.addView(rootView);
        getActivity().setTitle("Activity Manager");
        mainGrid = (GridLayout) rootView.findViewById(R.id.mainGrid);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        sliderDotsPanel = (LinearLayout) rootView.findViewById(R.id.SliderDots);

        imageSlider();

        //set Event
        setSingleEvent(mainGrid);



        return rootView;



    }

    private void imageSlider() {


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());

        viewPager.setAdapter(viewPagerAdapter);

        dotsCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i =0; i < dotsCount; i++){

            dots[i] = new ImageView(getActivity());
                dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotsPanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i< dotsCount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(), 2000, 4000);

    }

    public class MyTimeTask extends TimerTask {

        @Override
        public void run() {

           Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else if(viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    } else if(viewPager.getCurrentItem() == 3){
                        viewPager.setCurrentItem(4);
                    } else if(viewPager.getCurrentItem() == 4){
                        viewPager.setCurrentItem(5);
                    } else if(viewPager.getCurrentItem() == 5){
                        viewPager.setCurrentItem(6);
                    } else if(viewPager.getCurrentItem() == 6){
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for(int i=0; i<mainGrid.getChildCount(); i++){
            //You can see, all child item is CardView, so we just object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(), "Clicked at index "+ finalI, Toast.LENGTH_SHORT).show();
                    switch (finalI){
                        case 0:
                            startActivity(new Intent(getActivity(), praposal_recycler.class));
                                break;
                        case 1: startActivity(new Intent(getActivity(), Creative.class));
                            break;
                        case 2: startActivity(new Intent(getActivity(), publcity_recycler.class));
                            break;
                        case 3: startActivity(new Intent(getActivity(), Technical.class));
                            break;
                        case 4: startActivity(new Intent(getActivity(), DisplayEventName.class));
                            break;
                        case 5: startActivity(new Intent(getActivity(), Report.class));
                            break;
                    }
                }
            });
        }
    }

    public String toString() {
        return "Activity Manager";
    }
}
