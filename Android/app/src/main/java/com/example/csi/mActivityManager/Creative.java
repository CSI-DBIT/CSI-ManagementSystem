package com.example.csi.mActivityManager;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.Prompts.DetailActivity;
import com.example.csi.R;
import com.example.csi.mAdapter.ExampleAdapter;
import com.example.csi.mAdapter.ExampleItem;
import com.example.csi.mAdapter.PraposalAdapter;
import com.example.csi.mAdapter.PraposalItem;
import com.example.csi.mFragments.MinuteManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Creative extends AppCompatActivity implements PraposalAdapter.OnItemClickedListener {

    private RecyclerView rv;
    private PraposalAdapter mPraposalAdapter;
    private ArrayList<PraposalItem> mPraposalList;
    private RequestQueue mRequestQueue;
    private String server_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creative);

        mPraposalList = new ArrayList<>();
        rv=findViewById(R.id.recycler_view_P);
        rv.setLayoutManager(new LinearLayoutManager(Creative.this));
        mRequestQueue = Volley.newRequestQueue(Creative.this);
        parseJSON(); //This method is used to get list of Agendas from server

        rv.setAdapter(new PraposalAdapter(Creative.this,mPraposalList));
        //mPraposalAdapter.setOnItemClickListener(Creative.this);

    }


    public void parseJSON() {
        server_url = "http://159.65.144.246:8081/creative/viewListEvents";   //Main Server URL
       // server_url = "http://192.168.43.110:8081/creative/viewListEvents";

        mPraposalList.clear();

        StringRequest stringRequest =new StringRequest(Request.Method.POST,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.i("volleyABC" ,"got response    "+response);
                Toast.makeText(Creative.this,response ,Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0; i< jsonArray.length(); i++) {
                        JSONObject minutes = jsonArray.getJSONObject(i);

                        String eid = minutes.getString("eid");
                        String date = minutes.getString("p_date");
                        String Name = minutes.getString("name");
                        Log.i("color status",Name);
                        String status = minutes.getString("status");
                        Log.i("color status",status);
                        //String points = minutes.getString("minute");

                        //in the above variable date we are not getting date in DD:MM:YYYYY
                        //so we are creating new variable date1 to get our desire format
                        String date1 = date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4);

                        mPraposalList.add(new PraposalItem(eid, date1, Name, status, "No extra"));

                    }

                    mPraposalAdapter = new PraposalAdapter(Creative.this, mPraposalList);
                    rv.setAdapter(mPraposalAdapter);
                    mPraposalAdapter.setOnItemClickListener(Creative.this);

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
                    Toast.makeText(Creative.this, "Error:-" + statusCode, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                } catch(Exception e) {
                    Toast.makeText(Creative.this, "Check Network",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRequestQueue.add(stringRequest);
    }


    @Override
    public void onItemClick(int position) {
        PraposalItem clickedItem = mPraposalList.get(position);
        Toast.makeText(Creative.this,clickedItem.getmEid() , Toast.LENGTH_SHORT).show();
        Intent creative_form = new Intent(Creative.this,Creative_form.class);
        startActivity(creative_form);


        //write here code when press back

    }
}
