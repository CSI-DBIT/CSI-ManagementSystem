package com.example.csi.Prompts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csi.R;

public class role_intent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_intent);

        TextView t =(TextView) findViewById(R.id.uid_final);
        t.setText(getIntent().getStringExtra(MainActivity.EXTRA_UID));

        TextView t1 = (TextView) findViewById(R.id.uname_final);
        t1.setText(getIntent().getStringExtra(MainActivity.EXTRA_UNAME));

        Toast.makeText(this,"Succefully LogIn",Toast.LENGTH_SHORT).show();
    }
}
