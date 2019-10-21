package com.example.csi.mActivityManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.csi.Prompts.Manager;
import com.example.csi.R;
import com.example.csi.SharedPreferenceConfig;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Publicity extends AppCompatActivity {

    private SharedPreferenceConfig preferenceConfig;
    String urole1=null;
    LinearLayout pr_lay;
    Button edit_pr;
    String eid;
    TextView eventName, eventTheme, event_date, eventDescription, speaker, venue, fee_csi, fee_non_csi, prize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicity);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        urole1=preferenceConfig.readRoleStatus();
        edit_pr=findViewById(R.id.submit_pl);

        eventName = (TextView)findViewById(R.id.name_pl);
        eventTheme = (TextView)findViewById(R.id.theme_pl);
        event_date = (TextView)findViewById(R.id.ed_pl);
        speaker = (TextView) findViewById(R.id.speaker_pl);
        venue = (TextView) findViewById(R.id.venue_pl);
        fee_csi = (TextView) findViewById(R.id.fee_csi_pl);
        fee_non_csi = (TextView) findViewById(R.id.fee_non_csi_pl);
        prize = (TextView) findViewById(R.id.prize_pl);
        eventDescription = (TextView)findViewById(R.id.desc_pl);


        Bundle extras = getIntent().getExtras();
        if(extras == null) {

        } else {
            eid=extras.getString("EID");

        }

        if(urole1.equals("PR Head")){
            pr_lay=findViewById(R.id.pr_pl);
            pr_lay.setVisibility(View.VISIBLE);
        }
        edit_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Publicity.this,"Submitted",Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        getData();
    }

    public void getData(){

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eid", eid);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //creating jsonobject ends

        //checking data inserted into json object
        final String requestBody = jsonObject.toString();
        Log.i("volleyABC123", requestBody);

        //getting response from server starts
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://159.65.144.246:8081/creative/viewpropdetail",new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                Log.i("volleyABC4985" ,"got response    "+response);
                //Toast.makeText(Creative_form.this, "Logged IN", Toast.LENGTH_SHORT).show();



                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    // Log.i("tracking uid","main Activity "+UID);

                    eventName.setText(jsonObject1.getString("name"));
                    eventTheme.setText(jsonObject1.getString("theme"));

                    speaker.setText(jsonObject1.getString("speaker"));
                    venue.setText(jsonObject1.getString("venue"));
                    fee_csi.setText(jsonObject1.getString("reg_fee_c"));
                    fee_non_csi.setText(jsonObject1.getString("reg_fee_nc"));
                    prize.setText(jsonObject1.getString("prize"));
                    eventDescription.setText(jsonObject1.getString("description"));


                     String eventDate=jsonObject1.getString("event_date");
                     String date = eventDate.substring(8,10) + "/" + eventDate.substring(5,7) + "/" + eventDate.substring(0,4);
                    event_date.setText(date);

                    //Send data to Manager.java starts
                    // Call manager.java file i.e. Activity with navigation drawer activity
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        },new Response.ErrorListener()  {

            @Override
            public void onErrorResponse(VolleyError error) {

                try{
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(Publicity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show(); //This method is used to show pop-up on the screen if user gives wrong uid


                    error.printStackTrace();}
                catch (Exception e)
                {
                    Toast.makeText(Publicity.this,"Check Network",Toast.LENGTH_SHORT).show();}
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

    }

    public void sendData(){

    }

}
