package com.example.csi.mActivityManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.csi.R;
import com.squareup.picasso.Picasso;

public class CreativePosterFull extends AppCompatActivity {

    String posterUrl;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creative_poster_full);

        Intent intent = getIntent();
        posterUrl = intent.getStringExtra("poster_url");

        imageView = findViewById(R.id.image);
        loadImageUrl(posterUrl);
//        Toast.makeText(this, posterUrl, Toast.LENGTH_SHORT).show();
    }

    private void loadImageUrl(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

    }
}