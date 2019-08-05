package com.example.csi.Prompts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AddMinute extends AppCompatActivity {

    AutoCompleteTextView mCreateAgenda;
    Button mAddMinute;
    String Agenda, Points, Creator, server_url;
    EditText  mCreatePoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("i07","Entered1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_minute);

        Intent intent = getIntent();
        Creator = intent.getStringExtra("id"); //getting User ID from MinuteManager

        server_url="http://159.65.144.246:8081/minutes/create";  //Main Server URL
        //server_url="http://192.168.43.84:8080/minutes/create";

        mCreateAgenda = findViewById(R.id.create_agenda);
        mCreatePoints = findViewById(R.id.create_points);
        mCreatePoints.setMaxLines(10);
        mCreatePoints.setVerticalScrollBarEnabled(true);
        mCreatePoints.setMovementMethod(new ScrollingMovementMethod());
        mAddMinute = findViewById(R.id.add_minute);

        mAddMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("i234","Add Minute");
                Agenda = mCreateAgenda.getText().toString();
                Points = mCreatePoints.getText().toString();

                createNewMinute(); //sending new created minute to server

                finish();
            }
        });
    }

    public void createNewMinute() {
        Log.i("i234","Create Minute");
        final JSONObject jsonObject = new JSONObject();
        try {
            Log.i("i234","Send JSON");
            jsonObject.put("id",Creator);
            jsonObject.put("agenda", Agenda);
            jsonObject.put("points", Points);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String Temp = jsonObject.toString();
        Log.i("i234",Temp);
        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.i("i234" ,"got response    "+response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("volleyABC" ,"Got error in connecting server");
                try{
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                Log.i("i234" ,Integer.toString(error.networkResponse.statusCode));
                Toast.makeText(AddMinute.this,"Error:-"+statusCode,Toast.LENGTH_SHORT).show();
                error.printStackTrace();}
                catch (Exception e)
                {
                    Log.i("i234" ,"Error uploading data");
                }

            }
        }) {

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
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
