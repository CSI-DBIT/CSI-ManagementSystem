package com.example.csi.mActivityManager;

import android.content.Intent;
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

public class Report extends AppCompatActivity implements PraposalAdapter.OnItemClickedListener{

    private RecyclerView rv;
    private PraposalAdapter mPraposalAdapter;
    private ArrayList<PraposalItem> mPraposalList;
    private RequestQueue mRequestQueue;
    private String server_url, eid;

    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getSupportActionBar().setTitle("Reports");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPraposalList = new ArrayList<>();
        rv=findViewById(R.id.recycler_view_report);

        rv.setLayoutManager(new LinearLayoutManager(Report.this));
        mRequestQueue = Volley.newRequestQueue(Report.this);
        parseJSON(); //This method is used to get list of Agendas from server

        rv.setAdapter(new PraposalAdapter(Report.this,mPraposalList));
    }

    public void parseJSON() {
        server_url = "http://tayyabali.in:9000/report/list";   //Main Server URL
        // server_url = "http://192.168.43.110:8081/creative/viewListEvents";

        mPraposalList.clear();

        StringRequest stringRequest =new StringRequest(Request.Method.GET,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.i("volleyABC" ,"got response    "+response);
//                Toast.makeText(Creative.this,response ,Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    TextView no_report_text = findViewById(R.id.no_report);
                    if(jsonArray.length() > 0) {
                        rv.setVisibility(View.VISIBLE);
                        no_report_text.setVisibility(View.GONE);
                    }
                    else {
                        no_report_text.setText("No pending Requests");
                    }
                    for(int i=0; i< jsonArray.length(); i++) {
                        JSONObject minutes = jsonArray.getJSONObject(i);

                        eid = minutes.getString("eid");
                        String date = minutes.getString("event_date");
                        Name = minutes.getString("name");
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
                    mPraposalAdapter = new PraposalAdapter(Report.this, mPraposalList);
                    rv.setAdapter(mPraposalAdapter);
                    mPraposalAdapter.setOnItemClickListener(Report.this);
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
                    Toast.makeText(Report.this, "Error:-" + statusCode, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                } catch(Exception e) {
                    Toast.makeText(Report.this, "Check Network",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRequestQueue.add(stringRequest);
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
    public void onItemClick(int position) {
        Intent intent = new Intent(Report.this,ReportDisplay.class);
        PraposalItem clickedItem = mPraposalList.get(position);
        String EventName = clickedItem.getmName();
        intent.putExtra("eName", EventName);
        startActivity(intent);
    }
}
