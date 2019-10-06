package com.example.csi.Gallery.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.csi.Gallery.CircularViewPagerHandler;
import com.example.csi.Gallery.DisplayImageAdapter.FullSizeAdapter;
import com.example.csi.R;

import java.util.ArrayList;

public class FullScreenActivity extends AppCompatActivity {

    ViewPager viewPager;
    String[] images;
    ArrayList<String> imagesUrl;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        Log.i("FullScreenActivity","Entered in full screen activity");

        if (savedInstanceState == null)
        {
            Intent i = getIntent();
            //images = i.getStringArrayExtra("IMAGES");
            imagesUrl = i.getStringArrayListExtra("IMAGES");
            position = i.getIntExtra("POSITION", 0);
        }
        Log.i("imagesUrl", String.valueOf(imagesUrl));

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        FullSizeAdapter fullSizeAdapter = new FullSizeAdapter(this, imagesUrl);
        viewPager.setAdapter(fullSizeAdapter);
        //viewPager.addOnPageChangeListener(new CircularViewPagerHandler(viewPager));
        //viewPager.setOnPageChangeListener(new CircularViewPagerHandler(viewPager));
        viewPager.setCurrentItem(position, true);


    }
}
