package com.example.csi.mActivityManager;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.R;
import com.example.csi.SharedPreferenceConfig;
import com.example.csi.mAdapter.PraposalAdapter;
import com.example.csi.mAdapter.PraposalItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class publcity_recycler extends AppCompatActivity implements  PraposalAdapter.OnItemClickedListener {

    public static String eid ="hello";
    public static String st=null;
    private SharedPreferenceConfig preferenceConfig;
    String urole1=null;
    private Button add_praposal;
    private RecyclerView rv;
    private PraposalAdapter mPraposalAdapter;
    private ArrayList<PraposalItem> mPraposalList;
    private RequestQueue mRequestQueue;
    private String server_url;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publcity_recycler);


//        Objects.requireNonNull(getSupportActionBar()).setTitle("Proposal");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        urole1=preferenceConfig.readRoleStatus();
        mPraposalList = new ArrayList<>();
        rv=findViewById(R.id.recycler_view_publicity);
        rv.setLayoutManager(new LinearLayoutManager(publcity_recycler.this));
        mRequestQueue = Volley.newRequestQueue(publcity_recycler.this);


        //        swipe();
        parseJSON(); //This method is used to get list of Agendas from serve
        rv.setAdapter(new PraposalAdapter(publcity_recycler.this,mPraposalList));

    }


    public void parseJSON() {
        server_url = "http://tayyabali.in:9000/creative/listcreative";   //Main Server URL
        // server_url = "http://192.168.43.110:8081/creative/viewListEvents";

        mPraposalList.clear();

        StringRequest stringRequest =new StringRequest(Request.Method.GET,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.i("volleyABC" ,"got response    "+response);
//                Toast.makeText(Creative.this,response ,Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i< jsonArray.length(); i++) {
                        JSONObject minutes = jsonArray.getJSONObject(i);

                        eid = minutes.getString("eid");
                        String date = minutes.getString("event_date");
                        String Name = minutes.getString("name");
//                        String status = minutes.getString("status");
                        String theme =minutes.getString("theme");
                        //String points = minutes.getString("minute");

                        //in the above variable date we are not getting date in DD:MM:YYYY
                        //so we are creating new variable date1 to get our desire format
                        String date1 = date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);

                        mPraposalList.add(new PraposalItem(eid,"Date: "+date1, Name,"C","Theme: "+ theme));
//                        JSONObject minutes = jsonArray.getJSONObject(i);
//
//                        String eid = minutes.getString("eid");
//                        String Name = minutes.getString("name");
////                        String status = minutes.getString("status");
//                        String theme = minutes.getString("theme");

                        //in the above variable date we are not getting date in DD:MM:YYYYY
                        //so we are creating new variable date1 to get our desire format
//                        String date1 = date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);

//                        mPraposalList.add(new PraposalItem(eid,null, Name, theme, "No extra"));

                    }
                    mPraposalAdapter = new PraposalAdapter(publcity_recycler.this, mPraposalList);
                    rv.setAdapter(mPraposalAdapter);
                    mPraposalAdapter.setOnItemClickListener(publcity_recycler.this);
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
                    Toast.makeText(publcity_recycler.this, "Error:-" + statusCode, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                } catch(Exception e) {
                    Toast.makeText(publcity_recycler.this, "Check Network",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {

        PraposalItem clickedItem = mPraposalList.get(position);
        Toast.makeText(publcity_recycler.this,clickedItem.getmEid() , Toast.LENGTH_SHORT).show();
        Intent creative_form = new Intent(publcity_recycler.this,Publicity.class);
        String id = clickedItem.getmEid();
        creative_form.putExtra("EID", id);
        Log.i("testing",id);
        startActivity(creative_form);

    }
}
