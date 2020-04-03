package com.example.csi.mActivityManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.Prompts.MainActivity;
import com.example.csi.R;
import com.example.csi.SharedPreferenceConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class proposal_desc extends AppCompatActivity {

    String extra="";
    EditText comment_e;
    TextView comment_t;

    private SharedPreferenceConfig preferenceConfig;
    String eid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_desc);
        getSupportActionBar().setTitle("Proposal Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button ap =findViewById(R.id.approve_pd);
        Button rej =findViewById(R.id.reject_pd);
        Button edit = findViewById(R.id.edit_pd);
         comment_e = findViewById(R.id.comment_e);
         comment_t = findViewById(R.id.comment_t);


        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        String urole1=preferenceConfig.readRoleStatus();
//      Toast.makeText(proposal_desc.this,urole1,Toast.LENGTH_SHORT).show();

        eid = getIntent().getStringExtra(praposal_recycler.eid);
        String st = getIntent().getStringExtra(praposal_recycler.st);

        Log.i("volleyABC" ,"123");
//        Toast.makeText(proposal_desc.this,eid , Toast.LENGTH_SHORT).show();
        get_data("http://tayyabali.in:9090/proposal/viewproposal","0","0");


        if(urole1.equals("HOD") && st.equals("1")){
            ap.setVisibility(View.VISIBLE);
            rej.setVisibility(View.VISIBLE);
            comment_e.setVisibility(View.VISIBLE);
        }
        else if(urole1.equals("SBC") && st.equals("0")){
            ap.setVisibility(View.VISIBLE);
            rej.setVisibility(View.VISIBLE);
            comment_e.setVisibility(View.VISIBLE);
        }
        else if(urole1.equals("Technical Head")){
            edit.setVisibility(View.VISIBLE);
            comment_t.setVisibility(View.VISIBLE);
        }

        ap.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      if(urole1.equals("HOD")) {
                                          customDialog("The Proposal will be Submitted","2");

//                                          //if sbc then 1 if hod 2
                                      }
                                      else if(urole1.equals("SBC")) customDialog("The Proposal will be Forwarded to HOD","1");
//                                          //if sbc then 1 if hod 2

                                  }
                              });


        rej.setOnClickListener(v -> {
                    if(urole1.equals("HOD")) customDialog("The Proposal will be Removed","-2");
//                        //if sbc then 1 if hod 2
                    else if(urole1.equals("SBC")) customDialog("The Proposal will be Removed","-2");
//                        //if sbc then 1 if hod 2
//            finish();
        }
        );

        edit.setOnClickListener(v -> {
            Intent edit_proposal = new Intent(proposal_desc.this,edit_proposal.class);
            edit_proposal.putExtra("data",extra);
            edit_proposal.putExtra("eid",eid);
            startActivity(edit_proposal);
        }
        );
    }
    void get_data(String url , String flag , String status) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("eid",eid); //actual value shud be id_s
            if(flag.equals("1")){
                jsonObject.put("status",status);
                jsonObject.put("comment",comment_e.getText().toString());

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();
        Log.i("volleyABC ", requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("volleyABC response", response);
//                Toast.makeText(proposal_desc.this,response, Toast.LENGTH_SHORT).show();
                if(flag.equals("0")){
                try {
                    extra=response;
                    set(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }}

                else {
                    Toast.makeText(proposal_desc.this,"Response Recorded", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    //String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(proposal_desc.this,error.networkResponse.statusCode,Toast.LENGTH_SHORT).show();//it will not occur as authenticating at start
                    error.printStackTrace();}
                catch (Exception e)
                {
                    Log.i("volleyABC" ,"exception");
                    Toast.makeText(proposal_desc.this,"Check Network",Toast.LENGTH_SHORT).show();} //occur if connection not get estabilished
            }

        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(proposal_desc.this);
        requestQueue.add(stringRequest);
    }

    void set(String response) throws JSONException {
        TextView n = findViewById(R.id.name_pd);
        TextView t = findViewById(R.id.theme_pd);
        TextView d = findViewById(R.id.desc_pd);
        TextView ed = findViewById(R.id.ed_pd);
        TextView c = findViewById(R.id.cb_pd);
        TextView p = findViewById(R.id.pb_pd);
        TextView g = findViewById(R.id.gb_pd);

        JSONObject res = new JSONObject(response);

        n.setText(res.getString("name"));
        t.setText(res.getString("theme"));
        String date=res.getString("event_date");
        date = date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);
        ed.setText(date);
        d.setText(res.getString("description"));
        c.setText(res.getString("creative_budget"));
        p.setText(res.getString("publicity_budget"));
        g.setText(res.getString("guest_budget"));
        comment_t.setText(res.getString("comment"));


    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        get_data("http://tayyabali.in:9090/proposal/viewproposal","0","0");
    }

    public void customDialog(String message , String st){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(proposal_desc.this);
        //builderSingle.setIcon(R.drawable.ic_notification);
        builderSingle.setTitle("NOTE");
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        get_data("http://tayyabali.in:9090/proposal/status","1",st );

                        finish();

                    }
                });

        builderSingle.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // TODO Auto-generated method sub
        int id= item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
