package com.example.csi.mFragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class Attendance extends Fragment {

    View rootView;
    JSONObject jsonObject = new JSONObject();
    String checkboxData=null;
    String date=null;
    String server_url; //Main Server URL
    //String server_url="http://192.168.43.84:8080/request";
    String rsn="";
    String slots="";
    String UID="";

    public static Attendance newInstance() {
        return new Attendance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_request_attendance,container,false);
        server_url = rootView.getResources().getString(R.string.server_url) + "/attendance/request";
        getActivity().setTitle("Attendance");
        Bundle bundle = getArguments();
        UID = this.getArguments().getString("id");
        Log.i("tracking uid","in manager sending to profile "+UID);

        Button datePicker= rootView.findViewById(R.id.dateBtn);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFrag dpf = new datePickerFrag().newInstance();
               dpf.setCallBack(onDate);
                dpf.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });

        Button timepicker = rootView.findViewById(R.id.timeBtn);
        final TextView timeslot = rootView.findViewById(R.id.selectedtime);

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout l = rootView.findViewById(R.id.checkBox);
                l.setVisibility(View.VISIBLE);
            }
        });
        EditText reason = rootView.findViewById(R.id.reason);
        reason.setMaxLines(5);
        reason.setVerticalScrollBarEnabled(true);
        reason.setMovementMethod(new ScrollingMovementMethod());

        Button timeslotOK = rootView.findViewById(R.id.CheckBoxOK);
        timeslotOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout l = rootView.findViewById(R.id.checkBox);
                l.setVisibility(View.GONE);

                checkboxData="";

                CheckBox c1 = rootView.findViewById(R.id.checkBox1);
                CheckBox c2 = rootView.findViewById(R.id.checkBox2);
                CheckBox c3 = rootView.findViewById(R.id.checkBox3);
                CheckBox c4 = rootView.findViewById(R.id.checkBox4);
                CheckBox c5 = rootView.findViewById(R.id.checkBox5);
                CheckBox c6 = rootView.findViewById(R.id.checkBox6);
                CheckBox c7 = rootView.findViewById(R.id.checkBox7);

                if(c1.isChecked()){
                    checkboxData=checkboxData+"1";
                    slots=slots+"9.00AM-10.00AM ,";
                }else {
                    checkboxData=checkboxData+"0";
                }
                if(c2.isChecked()){
                    checkboxData=checkboxData+"1";
                    slots=slots+"10.00AM-11.00AM ,";
                }else {
                    checkboxData=checkboxData+"0";
                }
                if(c3.isChecked()){
                    checkboxData=checkboxData+"1";
                    slots=slots+"11.15AM-12.15PM ,";
                }else {
                    checkboxData=checkboxData+"0";
                }
                if(c4.isChecked()){
                    checkboxData=checkboxData+"1";
                    slots=slots+"12.15PM-1.15PM ,";
                }else {
                    checkboxData=checkboxData+"0";
                }
                if(c5.isChecked()){
                    checkboxData=checkboxData+"1";
                    slots=slots+"2.00PM-3.00PM ,";
                }else {
                    checkboxData=checkboxData+"0";
                }
                if(c6.isChecked()){
                    checkboxData=checkboxData+"1";
                    slots=slots+"3.00PM-4.00PM ,";
                }else {
                    checkboxData=checkboxData+"0";
                }
                if(c7.isChecked()){
                    checkboxData=checkboxData+"1";
                    slots=slots+"4.00PM-5.00PM ,";
                }else {
                    checkboxData=checkboxData+"0";
                }
                Log.i("info123", checkboxData);

            }
        });

        Button submit = rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reason = rootView.findViewById(R.id.reason);
                rsn = reason.getText().toString();

                if(date==null)  {
                    Toast.makeText(getActivity(),"Enter Date",Toast.LENGTH_SHORT).show(); }
                else if(checkboxData==null)  {Toast.makeText(getActivity(),"Enter Timeslots",Toast.LENGTH_SHORT).show();}
                else  if (rsn.length()==0) {Toast.makeText(getActivity(),"Enter Reason",Toast.LENGTH_SHORT).show();}

                else  customDialog("Date:  "+date+"\n"+"Slots: "+slots+"\n"+"Reason: "+rsn+"\n");



            }
        });

        return rootView;
    }

    public String toString() {
        return "Attendance";
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


           date= String.valueOf(year) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(dayOfMonth);

            TextView outputDate = rootView.findViewById(R.id.date);
            outputDate.setText(date);
            Log.i("info123",date);
        }
    };

    public  int  setJason()
    {
        int flag=1;

        if(date==null)  {Toast.makeText(getActivity(),"Enter Date",Toast.LENGTH_SHORT).show(); return  2;}
        else if(checkboxData==null)  {Toast.makeText(getActivity(),"Enter Timeslots",Toast.LENGTH_SHORT).show(); return 2;}
        else  if (rsn.length()==0) {Toast.makeText(getActivity(),"Enter Reason",Toast.LENGTH_SHORT).show(); return 2;}

        try {
            jsonObject.put("id",UID); //value from bundle
            jsonObject.put("date",date);

            Log.i("info123", String.valueOf(jsonObject));

            Log.i("info123", checkboxData);

            for(int i=0;i<7;i++)
            {
                String value="";
                value=value+checkboxData.charAt(i);
                jsonObject.put("s"+(i+1)+"",value);

                Log.i("info123", String.valueOf(jsonObject));
            }

            jsonObject.put("reason",rsn);
            Log.i("info123", String.valueOf(jsonObject));

        } catch (JSONException e) {
            flag=0;
            e.printStackTrace();
        }
        return flag;
    }

    public void sendrequest(){

        final String requestBody = jsonObject.toString();
        Log.i("info123", requestBody);

        StringRequest stringRequest =new StringRequest(Request.Method.POST,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                Log.i("info123" ,"got response    "+response);
                Toast.makeText(getActivity(),"Submitted ",Toast.LENGTH_SHORT).show();
                //on ok response take back to respective page
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();

            }
        },new Response.ErrorListener()  {

            @Override
            public void onErrorResponse(VolleyError error) {

                try{

                    Log.i("info123" ,Integer.toString(error.networkResponse.statusCode));
                    error.printStackTrace();}
                catch (Exception e)
                {
                    Toast.makeText(getActivity(),"Check Network",Toast.LENGTH_SHORT).show();}

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
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void customDialog(String message){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(getActivity());
        //builderSingle.setIcon(R.drawable.ic_notification);
        builderSingle.setTitle("Preview");
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(setJason()==1)
                        {
                            sendrequest();
                        }

                        Log.i("info123", String.valueOf(jsonObject));
                    }
                });

        builderSingle.show();
    }
}