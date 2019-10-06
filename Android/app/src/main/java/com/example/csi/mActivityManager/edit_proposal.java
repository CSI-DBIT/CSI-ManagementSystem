package com.example.csi.mActivityManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
import com.example.csi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class edit_proposal extends AppCompatActivity {

    EditText e_name,e_theme,e_desc,e_edate,e_cb,e_pb,e_gb;
    Button edit;
    String eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_proposal);
        getSupportActionBar().setTitle("Edit Proposal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        e_name = findViewById(R.id.name);
        e_theme = findViewById(R.id.theme);
        e_desc = findViewById(R.id.description);
        e_edate = findViewById(R.id.edate);
         e_cb = findViewById(R.id.cbudget);
         e_pb = findViewById(R.id.pbudget);
        e_gb = findViewById(R.id.gbudget);
        edit=findViewById(R.id.edit_button);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {

        } else {
            String data = extras.getString("data");
            eid=extras.getString("eid");
            Log.i("volleyABC response", eid+data);

//            Toast.makeText(edit_proposal.this, data,Toast.LENGTH_SHORT).show();
            try {
                set(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });


    }
    void set(String response) throws JSONException {


        JSONObject res = new JSONObject(response);


        e_name.setText(res.getString("name"));
        e_theme.setText(res.getString("theme"));
        String date=res.getString("event_date");
        date = date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);
        e_edate.setText(date);
        e_desc.setText(res.getString("description"));
        e_cb.setText(res.getString("creative_budget"));
        e_pb.setText(res.getString("publicity_budget"));
        e_gb.setText(res.getString("guest_budget"));


    }

    void sendData() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("eid",eid);
            jsonObject.put("name",e_name.getText().toString());
            jsonObject.put("event_date", e_edate.getText().toString());
            jsonObject.put("description", e_desc.getText().toString());
            jsonObject.put("creative_budget", e_cb.getText().toString());
            jsonObject.put("publicity_budget", e_pb.getText().toString());
            jsonObject.put("guest_budget", e_gb.getText().toString());

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();
        Log.i("volleyABC ", requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://159.65.144.246:8081/proposal/editproposal", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("volleyABC response", response);
                Toast.makeText(edit_proposal.this,"Edited",Toast.LENGTH_SHORT).show();//it will not occur as authenticating at start
                finish();

//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    //String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(edit_proposal.this,error.networkResponse.statusCode,Toast.LENGTH_SHORT).show();//it will not occur as authenticating at start
                    error.printStackTrace();}
                catch (Exception e)
                {
                    Log.i("volleyABC" ,"exception");
                    Toast.makeText(edit_proposal.this,"Check Network",Toast.LENGTH_SHORT).show();} //occur if connection not get estabilished
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
        RequestQueue requestQueue= Volley.newRequestQueue(edit_proposal.this);
        requestQueue.add(stringRequest);
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
