package com.example.csi.Prompts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class AddMinute extends AppCompatActivity {

    AutoCompleteTextView mCreateAgenda;
    Button mAddMinute, mAddTask;
    String Agenda, Points, Creator, server_url;
    EditText  mCreatePoints, mTask;
    Spinner spinner;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("i07","Entered1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_minute);
        getSupportActionBar().setTitle("Add Minute");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Creator = intent.getStringExtra("id"); //getting User ID from MinuteManager

        server_url=getApplicationContext().getResources().getString(R.string.server_url) + "/minutes/create";  //Main Server URL
        //server_url="http://192.168.43.84:8080/minutes/create";

        spinner = findViewById(R.id.csi_members);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.csi_members_name, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        //spinnerAdapter.add("Sanket Deshmukh");
        //spinnerAdapter.add("Afif Shaikh");
        //spinnerAdapter.notifyDataSetChanged();

        mAddTask = findViewById(R.id.add_task);
        mCreatePoints = findViewById(R.id.create_points);
        mTask = findViewById(R.id.task);

        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow mainRow = findViewById(R.id.row1);
                mainRow.setVisibility(View.VISIBLE);
                TableRow tablerow;
                tableLayout = findViewById(R.id.table);
                TextView tv1, tv2;

                tablerow = new TableRow(AddMinute.this);
                tablerow.setClickable(true);
                /*tablerow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editbtn.setVisibility(View.VISIBLE);

                        final TextView sample1 = (TextView) tablerow.getChildAt(0);
                        final TextView sample2 = (TextView) tablerow.getChildAt(1);

                        et1.setText(sample1.getText());
                        et2.setText(sample2.getText());

                        editbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sample1.setText(et1.getText());
                                sample2.setText(et2.getText());

                                et1.setText("");
                                et2.setText("");

                                editbtn.setVisibility(View.GONE);
                            }
                        });
                        //Toast.makeText(MainActivity.this, sample.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/

                tv1 = new TextView(AddMinute.this);
                tv2 = new TextView(AddMinute.this);

                String sam = mTask.getText().toString();
                mTask.setText("");
                tv1.setText(sam);

                tv1.setGravity(Gravity.CENTER);
                tv1.setBackgroundColor(getResources().getColor(R.color.white));
                tv1.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tableborder, 0, 0, 0);

                TableRow.LayoutParams param = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        0.1f
                );
                param.setMargins(1, 0, 1, 1);
                tv1.setLayoutParams(param);

                sam = spinner.getSelectedItem().toString();
                spinner.setSelection(0);
                tv2.setText(sam);

                tv2.setGravity(Gravity.CENTER);
                tv2.setBackgroundColor(getResources().getColor(R.color.white));
                tv2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tableborder, 0, 0, 0);
                tv2.setTextColor(getResources().getColor(R.color.colorPrimary));

                TableRow.LayoutParams param1 = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        0.1f
                );
                param1.setMargins(0, 0, 1, 1);
                tv2.setLayoutParams(param1);

                tablerow.addView(tv1);
                tablerow.addView(tv2);

                tableLayout.addView(tablerow);
                //tablerow = (TableRow) tableLayout.getChildAt(1);
                //tablerow.setClickable(true);
                Toast.makeText(AddMinute.this, (CharSequence) spinner.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }
        });

        mCreateAgenda = findViewById(R.id.create_agenda);
        mCreatePoints = findViewById(R.id.create_points);
        mCreatePoints.setMaxLines(10);
        mCreatePoints.setVerticalScrollBarEnabled(true);
        mCreatePoints.setMovementMethod(new ScrollingMovementMethod());
        mAddMinute = findViewById(R.id.add_minute);

        mAddMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("i234","Add Minute");
                Agenda = mCreateAgenda.getText().toString();
                Points = mCreatePoints.getText().toString();

                //createMinuteTesting();
                createNewMinute(); //sending new created minute to server

                finish();
            }
        });
    }

    public void createNewMinute() {
        Log.i("i234","Create Minute");
        JSONArray jsonArray = new JSONArray();

        TableRow mainRow = findViewById(R.id.row1);
        if(mainRow.getVisibility() == View.VISIBLE) {
            for (int i = 1; i < tableLayout.getChildCount(); i++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
                //Toast.makeText(this, tableRow.toString(), Toast.LENGTH_SHORT).show();

                TextView textView1 = (TextView) tableRow.getChildAt(0);
                TextView textView2 = (TextView) tableRow.getChildAt(1);

                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("task", textView1.getText());
                    jsonObject1.put("person", textView2.getText());

                    jsonArray.put(jsonObject1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            Toast.makeText(AddMinute.this, "Please Assign at least 1 Task", Toast.LENGTH_SHORT).show();
        }

        final JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("minutes",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JSONObject jsonObject = new JSONObject();
        try {
            Log.i("i234","Send JSON");
            jsonObject.put("id",Creator);
            jsonObject.put("agenda", Agenda);
            jsonObject.put("points", Points);
            jsonObject.put("work", jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();
        Log.i("i234",requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    Log.i("i234" ,"got response    "+response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()  {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("volleyABC" ,"Got error in connecting server");
                try{
                String statusCode = String.valueOf(error.networkResponse.statusCode);
                Log.i("i234" ,Integer.toString(error.networkResponse.statusCode));
                Toast.makeText(AddMinute.this,"Error:-"+statusCode,Toast.LENGTH_SHORT).show();
                error.printStackTrace();}
                catch (Exception e)
                {
                    Log.i("i234" ,"Error uploading data");
                }

            }
        }) {

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
        RequestQueue requestQueue= Volley.newRequestQueue(this);
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
