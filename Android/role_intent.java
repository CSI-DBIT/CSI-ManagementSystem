package com.example.loginpage_f1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class role_intent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_intent);

        Toast.makeText(this,getString(R.string.login_succes_toast),Toast.LENGTH_SHORT).show();
    }
}
