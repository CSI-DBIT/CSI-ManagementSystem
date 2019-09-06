package com.example.csi.mActivityManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class proposal_desc extends AppCompatActivity {
    String eid;
    Button ap =findViewById(R.id.approve_pd);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_desc);

        eid = getIntent().getStringExtra(praposal_recycler.eid);
//        Toast.makeText(proposal_desc.this,eid , Toast.LENGTH_SHORT).show();
        get_data();



    }


    void get_data() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("eid",eid); //actual value shud be id_s
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();
        Log.i("volleyABC ", requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://159.65.144.246:8081/proposal/viewproposal", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("volleyABC response", response);
//                Toast.makeText(proposal_desc.this,response, Toast.LENGTH_SHORT).show();
                try {
                    set(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    //String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(proposal_desc.this,"Invalid Username or Password",Toast.LENGTH_SHORT).show();//it will not occur as authenticating at start
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


    }

}
