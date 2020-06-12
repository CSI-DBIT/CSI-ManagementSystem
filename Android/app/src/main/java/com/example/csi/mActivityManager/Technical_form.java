package com.example.csi.mActivityManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.R;
import com.example.csi.SharedPreferenceConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Technical_form extends AppCompatActivity {
    private String urole1,eid;
    private SharedPreferenceConfig preferenceConfig;
    private TextView name , theme , e_date,speaker,csi_f,ncsi_f,worth_prize , description;
    CheckBox question , internet , software;
    EditText comments;
    LinearLayout comments_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_form);
        getSupportActionBar().setTitle("Technical");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        urole1=preferenceConfig.readRoleStatus();

        name =findViewById(R.id.name_tf);
        theme  =findViewById(R.id.theme_tf);
        e_date =findViewById(R.id.ed_tf);
        speaker =findViewById(R.id.speaker_tf);
        csi_f =findViewById(R.id.fee_csi_tf);
        ncsi_f =findViewById(R.id.fee_non_csi_tf);
        worth_prize =findViewById(R.id.prize_tf);
        description =findViewById(R.id.desc_pd_tf);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {

        } else {
            eid=extras.getString("EID");

        }

        question = findViewById(R.id.question);
        internet = findViewById(R.id.internet);
        software = findViewById(R.id.software);
        comments = findViewById(R.id.comment_t);
        comments_layout=findViewById(R.id.tf_comment_layout);

        Button update = findViewById(R.id.updateTech);
        if(urole1.equals("Technical Head" )){
            update.setVisibility(View.VISIBLE);

        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (update.getText().equals("Edit")) {
                    update.setText("Update");
                    question.setEnabled(true);
                    internet.setEnabled(true);
                    software.setEnabled(true);
                    comments_layout.setVisibility(View.VISIBLE);

                }
                else {
                    update.setText("Edit");
                    question.setEnabled(false);
                    internet.setEnabled(false);
                    software.setEnabled(false);
                    comments_layout.setVisibility(View.GONE);

                }
            }
        });
        volley_get();

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

    public void volley_get(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eid", eid);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();
        Log.i("volleyABC ", requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://tayyabali.in:9000/technical/viewEvents", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                ret[0]=response;
                Log.i("volleyABC4985" ,"got response    "+response);
                //Toast.makeText(Creative_form.this, "Logged IN", Toast.LENGTH_SHORT).show();
                Log.i("volleyABC4985" ,"got response    "+response);
                //Toast.makeText(Creative_form.this, "Logged IN", Toast.LENGTH_SHORT).show();
                Log.i("volleyABC" ,"reposnsde"+response);
                if(response != null){
                    try {
                        JSONObject jsonObject1 = new JSONObject(response);
                        // Log.i("tracking uid","main Activity "+UID);
                        name.setText(jsonObject1.getString("name"));
                        theme.setText(jsonObject1.getString("theme"));
                        speaker.setText(jsonObject1.getString("speaker"));
                        csi_f.setText(jsonObject1.getString("reg_fee_c"));
                        ncsi_f.setText(jsonObject1.getString("reg_fee_nc"));
                        worth_prize.setText(jsonObject1.getString("prize"));
                        description.setText(jsonObject1.getString("description"));
                        if((int)jsonObject1.get("qs_set")==1){
                          question.setChecked(true);
                        }else{
                            question.setChecked(false);
                        }
                        if((int)jsonObject1.get("internet")==1){
                          internet.setChecked(true);
                        }else{
                            internet.setChecked(false);
                        }
                        if((int)jsonObject1.get("software_install")==1){
                          software.setChecked(true);
                        }else{
                            software.setChecked(false);
                        }

                        String eventDate=jsonObject1.getString("event_date");
                        if(eventDate!=null)
                        eventDate = eventDate.substring(8,10) + "/" + eventDate.substring(5,7) + "/" + eventDate.substring(0,4);
                        e_date.setText(eventDate);
                        getSupportActionBar().setTitle(jsonObject1.getString("name"));
                        //Send data to Manager.java starts
                        // Call manager.java file i.e. Activity with navigation drawer activity
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        },new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {

                try{
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(Technical_form.this, "Invalid Credentials", Toast.LENGTH_SHORT).show(); //This method is used to show pop-up on the screen if user gives wrong uid
                    error.printStackTrace();}
                catch (Exception e)
                {
                    Toast.makeText(Technical_form.this,"Check Network",Toast.LENGTH_SHORT).show();

                }

            }
        }){
            //sending JSONOBJECT String to server starts
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
        //sending JSONOBJECT String to server ends

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest); // get response from server
//        return ret[0];


    }
}
