package com.example.csi.mActivityManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.R;
import com.example.csi.mAdapter.PraposalAdapter;
import com.example.csi.mAdapter.PraposalItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Technical extends AppCompatActivity implements  PraposalAdapter.OnItemClickedListener {

    private RecyclerView rv;
    private PraposalAdapter mPraposalAdapter;
    private ArrayList<PraposalItem> mPraposalList;
    private RequestQueue mRequestQueue;
    private String server_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical);

        mPraposalList = new ArrayList<>();
        rv=findViewById(R.id.recycler_view_T);
        rv.setLayoutManager(new LinearLayoutManager(Technical.this));
        mRequestQueue = Volley.newRequestQueue(Technical.this);
        parseJSON(); //This method is used to get list of Agendas from server

        rv.setAdapter(new PraposalAdapter(Technical.this,mPraposalList));
    }

    public void parseJSON() {
        server_url = "http://159.65.144.246:8081/creative/listcreative";   //Main Server URL
        //server_url = "http://192.168.43.110:8081/creative/viewListEvents";

        mPraposalList.clear();

        StringRequest stringRequest =new StringRequest(Request.Method.GET,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.i("volleyABC" ,"got response    "+response);
//                Toast.makeText(Technical.this,response ,Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i< jsonArray.length(); i++) {
                        JSONObject minutes = jsonArray.getJSONObject(i);

                        String eid = minutes.getString("eid");
                        String date = minutes.getString("event_date");
                        String Name = minutes.getString("name");
//                        String status = minutes.getString("status");
                        String theme =minutes.getString("theme");
                        //String points = minutes.getString("minute");

                        //in the above variable date we are not getting date in DD:MM:YYYYY
                        //so we are creating new variable date1 to get our desire format
                        String date1 = date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);

                        mPraposalList.add(new PraposalItem(eid,"Date: "+date1, Name,"T","Theme: "+ theme));

                    }

                    mPraposalAdapter = new PraposalAdapter(Technical.this, mPraposalList);
                    rv.setAdapter(mPraposalAdapter);
                    mPraposalAdapter.setOnItemClickListener(Technical.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("volleyABC" ,"Got error in connecting server");
                try {
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.i("volleyABC", Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(Technical.this, "Error:-" + statusCode, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                } catch(Exception e) {
                    Toast.makeText(Technical.this, "Check Network",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        PraposalItem clickedItem = mPraposalList.get(position);
        Toast.makeText(Technical.this,clickedItem.getmEid() , Toast.LENGTH_SHORT).show();
        Intent technical_form = new Intent(Technical.this,Technical_form.class);
        startActivity(technical_form);

    }
}
