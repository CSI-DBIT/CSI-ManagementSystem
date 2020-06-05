package com.example.csi.mActivityManager;

import android.content.Context;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.Prompts.AddMinute;
import com.example.csi.Prompts.MainActivity;
import com.example.csi.R;
import com.example.csi.SharedPreferenceConfig;
import com.example.csi.mAdapter.PraposalAdapter;
import com.example.csi.mAdapter.PraposalItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class praposal_recycler extends AppCompatActivity implements  PraposalAdapter.OnItemClickedListener  {
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
        setContentView(R.layout.activity_praposal_recycler);
        getSupportActionBar().setTitle("Proposal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        urole1=preferenceConfig.readRoleStatus();
        mPraposalList = new ArrayList<>();
        rv=findViewById(R.id.recycler_view_praposal);
        Log.i("info123","p2");
        rv.setLayoutManager(new LinearLayoutManager(praposal_recycler.this));
        mRequestQueue = Volley.newRequestQueue(praposal_recycler.this);
        Log.i("info123","p3");
        swipe();
        parseJSON(); //This method is used to get list of Agendas from server
        Log.i("info123","p4");
        rv.setAdapter(new PraposalAdapter(praposal_recycler.this,mPraposalList));
        Log.i("info123","p5");



//        praposal add button

        add_praposal = (Button) findViewById(R.id.praposal_add);
        if(urole1.equals("Technical Head")) {
            add_praposal.setVisibility(View.VISIBLE);
        }
        add_praposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(praposal_recycler.this ,Proposal.class);
//                intent.putExtra("id",UID);
//                intent.putExtra(EXTRA_FLAG, FLAG);
                startActivity(intent);
            }
        });

//        praposal add button finishes here

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
        server_url = "http://tayyabali.in:9000/proposal/viewlistproposal/";   //Main Server URl
        mPraposalList.clear();

        StringRequest stringRequest =new StringRequest(Request.Method.GET,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.i("volleyABC" ,"got response    "+response);
                mPraposalList.clear();
//                Toast.makeText(praposal_recycler.this,response ,Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i< jsonArray.length(); i++) {
                            JSONObject minutes = jsonArray.getJSONObject(i);

                            String eid = minutes.getString("eid");
                            String Name = minutes.getString("name");
                            String status = minutes.getString("status");
                            String theme = minutes.getString("theme");
                            String date = minutes.getString("p_date");

                            //in the above variable date we are not getting date in DD:MM:YYYYY
                            //so we are creating new variable date1 to get our desire format
                           String date1 = date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);

                            if(urole1.equals("HOD") && (status.equals("1") || status.equals("2"))){ //for hod
                                    mPraposalList.add(new PraposalItem(eid,"Date: "+date1, Name, status,"Theme: "+ theme));

                            }
                            else if(urole1.equals("SBC") && (status.equals("1") || status.equals("0"))) {
                                mPraposalList.add(new PraposalItem(eid,"Date: "+date1, Name, status,"Theme: "+ theme));
                            }
                            else if(!urole1.equals("HOD") && !urole1.equals("SBC") ) {
                                mPraposalList.add(new PraposalItem(eid,"Date: "+date1, Name, status,"Theme: "+ theme));
                            }


                    }

                    mPraposalAdapter = new PraposalAdapter(praposal_recycler.this, mPraposalList);
                    rv.setAdapter(mPraposalAdapter);
                    mPraposalAdapter.setOnItemClickListener(praposal_recycler.this);

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
                    Toast.makeText(praposal_recycler.this, "Error:-" + statusCode, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                } catch(Exception e) {
                    Toast.makeText(praposal_recycler.this, "Check Network",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        PraposalItem clickedItem = mPraposalList.get(position);
         eid =clickedItem.getmEid();
         st = clickedItem.getmStatus();

        Intent proposal_desc = new Intent(praposal_recycler.this,proposal_desc.class);
        proposal_desc.putExtra(st,st);
        proposal_desc.putExtra(eid,eid);
        startActivity(proposal_desc);  //here sbc head can approve the praposal that info should show in this

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
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        parseJSON();
    }
}
