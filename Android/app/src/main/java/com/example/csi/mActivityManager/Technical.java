package com.example.csi.mActivityManager;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical);

        getSupportActionBar().setTitle("Technical");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipe();

        mPraposalList = new ArrayList<>();
        rv=findViewById(R.id.recycler_view_T);
        rv.setLayoutManager(new LinearLayoutManager(Technical.this));
        mRequestQueue = Volley.newRequestQueue(Technical.this);
        parseJSON(); //This method is used to get list of Agendas from server

        rv.setAdapter(new PraposalAdapter(Technical.this,mPraposalList));
    }

    void swipe() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresher_P);
        //swipeRefreshLayout.setColorSchemeResources(R.color.Red,R.color.OrangeRed,R.color.Yellow,R.color.GreenYellow,R.color.BlueViolet);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        int min = 65;
                        int max = 95;

                        parseJSON();
                    }
                }, 1000);
            }
        });
    }

    public void parseJSON() {
        server_url = getApplicationContext().getResources().getString(R.string.server_url) + "/creative/listcreative";   //Main Server URL
        //server_url = "http://192.168.43.110:8081/creative/viewListEvents";

        mPraposalList.clear();

        StringRequest stringRequest =new StringRequest(Request.Method.GET,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.i("volleyABC" ,"got response    "+response);
//                Toast.makeText(Technical.this,response ,Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    TextView no_tech_text = findViewById(R.id.no_tech);
                    if(jsonArray.length() > 0) {
                        rv.setVisibility(View.VISIBLE);

                        no_tech_text.setVisibility(View.GONE);
                    }
                    else {
                        no_tech_text.setText("Nothing to display");
                    }

                    for(int i=0; i< jsonArray.length(); i++) {
                        JSONObject minutes = jsonArray.getJSONObject(i);

                        String eid = minutes.getString("eid");
                        String date = minutes.getString("event_date");
                        String Name = minutes.getString("name");
                        //String status = minutes.getString("status");
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
        //Toast.makeText(Technical.this,clickedItem.getmEid() , Toast.LENGTH_SHORT).show();
        Intent technical_form = new Intent(Technical.this,Technical_form.class);
        String id = clickedItem.getmEid();
        technical_form.putExtra("EID", id);
        startActivity(technical_form);

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
