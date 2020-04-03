package com.example.csi.Prompts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
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
import com.example.csi.mAdapter.ExampleItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.example.csi.mFragments.AttendanceSBC.EXTRA_CLASS;

public class attendance_list extends AppCompatActivity {

    String sam;
    String server_url = "http://tayyabali.in:9090/attendance/view";
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        getSupportActionBar().setTitle("Attendance List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tableLayout = findViewById(R.id.display_table);

        Intent intent = getIntent();
        sam = intent.getStringExtra(EXTRA_CLASS);

        //Toast.makeText(this, "Your selected " + sam, Toast.LENGTH_SHORT).show();

        parseJSON();
    }

    private void parseJSON() {
        //creating jsonobject starts
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("year", sam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //creating jsonobject ends

        //checking data inserted into json object
        final String requestBody = jsonObject.toString();
        Log.i("volleyABC", requestBody);

        //getting response from server starts
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("volleyABC", "got response    " + response);
                //Toast.makeText(attendance_list.this, "Logged IN", Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject students = jsonArray.getJSONObject(i);

                        TableRow tableRow = new TableRow(attendance_list.this);
                        TextView tv1 = new TextView(attendance_list.this);
                        TextView tv2 = new TextView(attendance_list.this);
                        TextView tv3 = new TextView(attendance_list.this);

                        tv1.setText(students.getString("Name"));
                        tv1.setGravity(Gravity.CENTER);
                        tv1.setBackgroundColor(getResources().getColor(R.color.white));
                        tv1.setTextColor(getResources().getColor(R.color.colorPrimary));

                        TableRow.LayoutParams param = new TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );
                        param.setMargins(1, 0, 1, 1);
                        tv1.setLayoutParams(param);

                        tv2.setText(students.getString("total"));
                        tv2.setGravity(Gravity.CENTER);
                        tv2.setBackgroundColor(getResources().getColor(R.color.white));
                        tv2.setTextColor(getResources().getColor(R.color.colorPrimary));

                        TableRow.LayoutParams param1 = new TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );
                        param1.setMargins(1, 0, 1, 1);
                        tv2.setLayoutParams(param1);

                        tv3.setText(students.getString("percent"));
                        tv3.setGravity(Gravity.CENTER);
                        tv3.setBackgroundColor(getResources().getColor(R.color.white));
                        tv3.setTextColor(getResources().getColor(R.color.colorPrimary));
                        TableRow.LayoutParams param2 = new TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );
                        param2.setMargins(1, 0, 1, 1);
                        tv3.setLayoutParams(param2);

                        tableRow.addView(tv1);
                        tableRow.addView(tv2);
                        tableRow.addView(tv3);

                        tableLayout.addView(tableRow);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    Log.i("volleyABC", Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(attendance_list.this, "Invalid Credentials", Toast.LENGTH_SHORT).show(); //This method is used to show pop-up on the screen if user gives wrong uid


                    error.printStackTrace();
                } catch (Exception e) {
                    Toast.makeText(attendance_list.this, "Check Network", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest); // get response from server
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
