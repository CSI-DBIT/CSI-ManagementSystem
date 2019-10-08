package com.example.csi.mActivityManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Publicity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicity);
    }

    public void getData(){

    }

    public void sendData(){

    }

    public String volley(JSONObject jsonObject){

        final String requestBody = jsonObject.toString();
        Log.i("volleyABC ", requestBody);
        final String[] ret = new String[1];
        ret[0]=null;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://159.65.144.246:8081/proposal/editproposal", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("volleyABC response", response);
                Toast.makeText(Publicity.this,"Edited",Toast.LENGTH_SHORT).show();
                ret[0] =response;
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    //String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(Publicity.this,error.networkResponse.statusCode,Toast.LENGTH_SHORT).show();
                    error.printStackTrace();}
                catch (Exception e)
                {
                    Log.i("volleyABC" ,"exception");
                    Toast.makeText(Publicity.this,"Check Network",Toast.LENGTH_SHORT).show();}
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
        RequestQueue requestQueue= Volley.newRequestQueue(Publicity.this);
        requestQueue.add(stringRequest);
        return ret[0];

    }
}
