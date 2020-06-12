package com.example.csi.mActivityManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    String urole1,eid;
    private SharedPreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_form);
        getSupportActionBar().setTitle("Technical");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        urole1=preferenceConfig.readRoleStatus();

        Bundle extras = getIntent().getExtras();
        if(extras == null) {

        } else {
            eid=extras.getString("EID");

        }

        CheckBox question = findViewById(R.id.question);
        CheckBox internet = findViewById(R.id.internet);
        CheckBox software = findViewById(R.id.software);
        EditText comments = findViewById(R.id.comment_t);
        comments.setEnabled(false);

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
                    comments.setEnabled(true);
                }
                else {
                    update.setText("Edit");
                    question.setEnabled(false);
                    internet.setEnabled(false);
                    software.setEnabled(false);
                    comments.setEnabled(false);
                }
            }
        });

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

//                        eventName.setText(jsonObject1.getString("name"));



                        String eventDate=jsonObject1.getString("event_date");
                        String date = eventDate.substring(8,10) + "/" + eventDate.substring(5,7) + "/" + eventDate.substring(0,4);
//                        event_date.setText(date);
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
