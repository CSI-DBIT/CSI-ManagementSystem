package com.example.csi.Gallery.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.Gallery.EventNameAdapter.EventItem;
import com.example.csi.Gallery.EventNameAdapter.EventNameAdapter;
import com.example.csi.Gallery.ExampleDialogue;
import com.example.csi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DisplayEventName extends AppCompatActivity implements EventNameAdapter.OnItemClickListener, ExampleDialogue.ExampleDialogListener {


    public static final String EXTRA_EVENT = "FullPath";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EventNameAdapter mEventNameAdapter;
    private ArrayList<EventItem> mEventList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_name);
        getSupportActionBar().setTitle("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        mEventList = new ArrayList<>();
        mEventList.clear();
        mEventList.add(new EventItem("+\nAdd New File"));

        mEventNameAdapter = new EventNameAdapter(DisplayEventName.this, mEventList);
        mRecyclerView.setAdapter(mEventNameAdapter);

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        //String url = "http://192.168.43.84:8080/event";
        String url = "http://159.65.144.246:8090/event";    //Main Server URL
        //String url = "http://192.168.42.156:8080/event";
        //creating jsonobject starts
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("path","");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //creating jsonobject ends

        //checking data inserted into json object
        final String requestBody = jsonObject.toString();
        Log.i("volleyABC", requestBody);

        //getting response from server starts
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                mEventList.clear();

                Log.i("volleyABC" ,"got response    "+response);
                Toast.makeText(DisplayEventName.this, "Got Event List", Toast.LENGTH_SHORT).show();

                //Intent manager = new Intent(MainActivity.this, Manager.class);
                //String UROLE="", USERNAME="", ProfileURL="";

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    Log.i("json length", String.valueOf(jsonArray.length()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String event =  jsonArray.getString(i);
                        Log.i("event" ,"event " + i + " :- " + jsonArray.getString(i));
                        mEventList.add(new EventItem(event));
                    }
                    mEventList.add(new EventItem("+\nAdd New File"));

                    mEventNameAdapter.notifyDataSetChanged();
                    //mEventNameAdapter = new EventNameAdapter(MainActivity.this, mEventList);
                    mRecyclerView.setAdapter(mEventNameAdapter);
                    mEventNameAdapter.setOnItemClickListener(DisplayEventName.this);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()  {

            @Override
            public void onErrorResponse(VolleyError error) {

                try{
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(DisplayEventName.this, "Invalid", Toast.LENGTH_SHORT).show(); //This method is used to show pop-up on the screen if user gives wrong uid


                    error.printStackTrace();}
                catch (Exception e)
                {
                    Toast.makeText(DisplayEventName.this,"Check Network",Toast.LENGTH_SHORT).show();}
            }
        }){
            //sending JSONOBJECT String to server starts
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
        //sending JSONOBJECT String to server ends

        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        if (position == mEventList.size()-1) {
            //Toast.makeText(this, "New Event", Toast.LENGTH_SHORT).show();
            openDialog();
        }
        else {
            Intent year = new Intent(this, DisplayYear.class);
            EventItem clickedItem = mEventList.get(position);

            year.putExtra(EXTRA_EVENT, clickedItem.getEventName() + "/");
            Toast.makeText(DisplayEventName.this, clickedItem.getEventName() + "/", Toast.LENGTH_SHORT).show();
            startActivity(year);
        }
    }

    public void openDialog() {
        ExampleDialogue exampleDialogue = new ExampleDialogue();
        exampleDialogue.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String EventName) {
        Toast.makeText(this, EventName, Toast.LENGTH_SHORT).show();
        createDirectory(EventName);
    }

    private void createDirectory(final String Directory) {

        //String url = "http://192.168.43.84:8080/mkdir";
        String url = "http://159.65.144.246:8090/mkdir";    //Main Server URL
        //String url = "http://192.168.42.156:8080/mkdir";

        //creating jsonobject starts
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("path", "");
            jsonObject.put("fname", Directory);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //creating jsonobject ends

        //checking data inserted into json object
        final String requestBody = jsonObject.toString();
        Log.i("volleyABC", requestBody);

        //getting response from server starts
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                Log.i("got response", response);
                try {
                    mEventList.clear();
                    JSONArray jsonArray = new JSONArray(response);

                    Log.i("json length", String.valueOf(jsonArray.length()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String event =  jsonArray.getString(i);
                        Log.i("event" ,"event " + i + " :- " + jsonArray.getString(i));
                        mEventList.add(new EventItem(event));
                    }
                    mEventList.add(new EventItem("+\nAdd New File"));

                    mEventNameAdapter = new EventNameAdapter(DisplayEventName.this, mEventList);
                    mRecyclerView.setAdapter(mEventNameAdapter);
                    mEventNameAdapter.setOnItemClickListener(DisplayEventName.this);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()  {

            @Override
            public void onErrorResponse(VolleyError error) {

                try{
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(DisplayEventName.this, "Something went wrong", Toast.LENGTH_SHORT).show(); //This method is used to show pop-up on the screen if user gives wrong uid


                    error.printStackTrace();}
                catch (Exception e)
                {
                    Toast.makeText(DisplayEventName.this,"Check Network",Toast.LENGTH_SHORT).show();}
            }
        }){
            //sending JSONOBJECT String to server starts
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
        //sending JSONOBJECT String to server ends

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
}
