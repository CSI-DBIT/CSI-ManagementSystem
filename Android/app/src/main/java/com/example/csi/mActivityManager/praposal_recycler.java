package com.example.csi.mActivityManager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csi.Prompts.AddMinute;
import com.example.csi.R;

public class praposal_recycler extends AppCompatActivity {
    private Button add_praposal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praposal_recycler);

//        add_praposal = (Button) findViewById(R.id.praposal_add);
//        add_praposal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(praposal_recycler.this ,Proposal.class);
////                intent.putExtra("id",UID);
////                intent.putExtra(EXTRA_FLAG, FLAG);
//                startActivity(intent);
//            }
//        });
    }
}
