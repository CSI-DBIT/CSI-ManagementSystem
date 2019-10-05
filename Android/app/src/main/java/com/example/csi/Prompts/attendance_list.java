package com.example.csi.Prompts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.csi.R;

import static com.example.csi.mFragments.AttendanceSBC.EXTRA_CLASS;

public class attendance_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        Intent intent = getIntent();
        String sam = intent.getStringExtra(EXTRA_CLASS);

        Toast.makeText(this, "Your selected " + sam, Toast.LENGTH_SHORT).show();
    }
}
