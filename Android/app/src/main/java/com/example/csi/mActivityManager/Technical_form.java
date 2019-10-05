package com.example.csi.mActivityManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.csi.R;

public class Technical_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_form);
        CheckBox question = findViewById(R.id.question);
        CheckBox internet = findViewById(R.id.internet);
        CheckBox software = findViewById(R.id.software);

        Button update = findViewById(R.id.updateTech);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (update.getText().equals("Edit")) {
                    update.setText("Update");
                    question.setEnabled(true);
                    internet.setEnabled(true);
                    software.setEnabled(true);
                }
                else {
                    update.setText("Edit");
                    question.setEnabled(false);
                    internet.setEnabled(false);
                    software.setEnabled(false);
                }
            }
        });

    }
}
