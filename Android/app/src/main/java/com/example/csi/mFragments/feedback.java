package com.example.csi.mFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.csi.SharedPreferenceConfig;
import com.example.csi.mReqAdapter.RequestListItem;

import android.content.SharedPreferences;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class feedback extends Fragment {
    View rootView;
    String server_url = "http://tayyabali.in:9000/feedback";
    //    private SharedPreferenceConfig preferenceConfig;
//    private SharedPreferences mpref;
    private String usrid, usrname = "";
    EditText feedback_text;
    TextView name_text_v;
    Button save_feedback;
    String feedback = null;
    JSONObject jsonObject =new JSONObject();
    private RequestQueue mRequestQueue;
    private ArrayList<RequestListItem> mRequestList;



    public static feedback newInstance() {
        return new feedback();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_feedback, container, false);
        getActivity().setTitle("Feedback");


//        Bundle bundle = getArguments();
        usrid = this.getArguments().getString("id");
        usrname = this.getArguments().getString("name");
        Log.i("volleyABC", "to feedback section" + usrid + usrname);
//        Toast.makeText(getActivity(), "Feedback section" + usrid + usrname, Toast.LENGTH_SHORT).show();

        feedback_text = rootView.findViewById(R.id.text_feedback);
        name_text_v = rootView.findViewById(R.id.name_feedback);
        save_feedback = rootView.findViewById(R.id.feedback_save);
        mRequestList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        save_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback = feedback_text.getText().toString();
//                Toast.makeText(getActivity(), "Feedback section save clicked" + feedback, Toast.LENGTH_SHORT).show();
                setJason();
                send_data();
                feedback_text.setText("");

            }
        });

        name_text_v.setText(usrname);


//        SharedPreferenceConfig preferenceConfig;
//        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
//        mpref=getSharedPreferences(pref_name,MODE_PRIVATE);
//        String stored_usrid=mpref.getString("username","");
//

        return rootView;
    }

    public void setJason() {

        if (feedback == null) {
            Toast.makeText(getActivity(), "Enter Date", Toast.LENGTH_SHORT).show();
        } else {

            try {
                jsonObject.put("id", usrid); //value from bundle
                jsonObject.put("name", usrname);
                jsonObject.put("feedback", feedback);

                Log.i("info123", String.valueOf(jsonObject));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    public  void send_data(){
        final String requestBody = jsonObject.toString();
        Log.i("volleyABC123",requestBody);

        //getting response from server starts

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("volleyABC", "got response    " + response);
                Toast.makeText(getActivity(), "Thank you for feedback", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.i("volleyABC", Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(getActivity(), "Error:-" + statusCode, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                } catch(Exception e) {
                    Toast.makeText(getActivity(), "Check Network",Toast.LENGTH_SHORT).show();
                }
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

        mRequestQueue.add(stringRequest);
    }
}
