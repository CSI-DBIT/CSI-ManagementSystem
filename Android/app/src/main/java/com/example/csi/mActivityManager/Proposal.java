package com.example.csi.mActivityManager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.csi.mFragments.datePickerFrag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Proposal extends AppCompatActivity {
    String date = null;
    String edate = null;
    EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal);

        description = findViewById(R.id.pdescription);
        description.setMaxLines(5);
        description.setVerticalScrollBarEnabled(true);
        description.setMovementMethod(new ScrollingMovementMethod());
        getSupportActionBar().setTitle("Create Proposal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button submit = findViewById(R.id.submit_praposal);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterSubmit();
            }
        });

        Button dateOfmeeting = findViewById(R.id.dateOfMeeting);
        dateOfmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFrag nf = new datePickerFrag();
                nf.setCallBack(onDate);
                nf.show(getSupportFragmentManager(),"datepicker");

            }
        });

        Button dateOfevent = findViewById(R.id.dateOfevent);
        dateOfevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFrag nf = new datePickerFrag();
                nf.setCallBack(onEDate);
                nf.show(getSupportFragmentManager(),"datepicker");

            }
        });


        final Button addF = findViewById(R.id.addField);
        // final TextView done = findViewById(R.id.done);

        final Integer[] othopen = {0,0,0};
        addF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(othopen[0].equals(0))
                {
                    LinearLayout othrL1 = findViewById(R.id.otherL1);
                    othrL1.setVisibility(View.VISIBLE);
                    othopen[0] =1;
                }
                else if(othopen[0].equals(1) && othopen[1].equals(0))
                {
                    LinearLayout othrL2 = findViewById(R.id.otherL2);
                    othrL2.setVisibility(View.VISIBLE);
                    othopen[1] =1;
                }
                else if(othopen[0].equals(1) && othopen[1].equals(1) && othopen[2].equals(0) )
                {
                    LinearLayout othrL3 = findViewById(R.id.otherL3);
                    othrL3.setVisibility(View.VISIBLE);
                    othopen[2] =1;
                    addF.setVisibility(View.GONE);
                    // done.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            date = String.valueOf(year) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(dayOfMonth);

            //TextView outputDate = rootView.findViewById(R.id.date);
            // outputDate.setText(date);
            Log.i("info1234", date);
            TextView show_date_P_m = findViewById(R.id.date_P_m);
            show_date_P_m.setText(date);
            sendDate(date);
        }
    };


    DatePickerDialog.OnDateSetListener onEDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            edate = String.valueOf(year) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(dayOfMonth);

            //TextView outputDate = rootView.findViewById(R.id.date);
            // outputDate.setText(date);
            Log.i("info1234", edate+"event");
            TextView edate_s = findViewById(R.id.showdate);
            edate_s.setText((String) edate);
            edate_s.setVisibility(View.VISIBLE);
        }
    };

    public void sendDate( String date)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("date",date);
            Log.i("info123",jsonObject.toString());
            sendData("http://159.65.144.246:8081/proposal/viewagenda",jsonObject,0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setSpinner (String response)
    {

        Spinner spinner = findViewById(R.id.agendaSpinner);
        ArrayAdapter<String> arrayAdapter1;
        List<String> arr = new ArrayList<String>();

        if (response!=null) {
            arr.add("SELECT");
            try {

                JSONObject jsonObject1 = new JSONObject(response);

                Log.i("info123", response);

                JSONArray jagenda = jsonObject1.getJSONArray("agenda");


                for (int i = 0; i < jagenda.length(); i++)
                    arr.add(jagenda.getString(i));

                // Log.i("info123" , Arrays.toString(arr));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("info123", "eroor in extractingf");

            }
        }

        arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arr);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter1);


    }



    public void sendData(String server_url , JSONObject jsonObject , final int flag)
    {
        final String requestBody = jsonObject.toString();
        Log.i("info123", requestBody);

        StringRequest stringRequest =new StringRequest(Request.Method.POST,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                Log.i("info123" ,"got response    "+response);

                if(flag==0){

                    if(response.length()==13){Toast.makeText(Proposal.this,"choose other date",Toast.LENGTH_SHORT).show(); setSpinner(null);}
                    else setSpinner(response);
                } //send date

                else{Toast.makeText(Proposal.this,"Submitted new proposal",Toast.LENGTH_SHORT).show();} //send praposal

            }
        },new Response.ErrorListener()  {

            @Override
            public void onErrorResponse(VolleyError error) {

                try{

                    Log.i("info123" ,Integer.toString(error.networkResponse.statusCode));
                    error.printStackTrace();}
                catch (Exception e)
                {
                    Toast.makeText(Proposal.this,"Check Network",Toast.LENGTH_SHORT).show();}

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
        RequestQueue requestQueue= Volley.newRequestQueue(Proposal.this.getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void afterSubmit()
    {
        String preview ="",preSub="";

        EditText pname= findViewById(R.id.praposal_name);
        String pnames = pname.getText().toString();

        EditText ptheme= findViewById(R.id.ptheme);
        String pthemes = ptheme.getText().toString();

        EditText pdesc= findViewById(R.id.pdescription);
        String pdescs = pdesc.getText().toString();

        EditText pcb= findViewById(R.id.creativebudget);
        String pcbs = pcb.getText().toString();


        EditText ppb= findViewById(R.id.publicitybdget);
        String ppbs = ppb.getText().toString();

        EditText pguest= findViewById(R.id.guestp);
        String pguests = pguest.getText().toString();

//        21sep
        EditText speaker_e= findViewById(R.id.speaker_p);
        String speaker_s = speaker_e.getText().toString();

        EditText venue_e= findViewById(R.id.venue_p);
        String  venue_s= venue_e.getText().toString();

        EditText csi_f = findViewById(R.id.fee_csi);
        String csi_s = csi_f.getText().toString();

        EditText ncsi_f= findViewById(R.id.fee_non_csi);
        String  ncsi_s= ncsi_f.getText().toString();

        EditText prize_e= findViewById(R.id.prize_p);
        String  prize_s= prize_e.getText().toString();
//        21sep

        EditText poth1= findViewById(R.id.other1B);
        String poths1 = poth1.getText().toString();
        EditText pothF1= findViewById(R.id.other1F);
        String pothFs1 = pothF1.getText().toString();

        EditText poth2= findViewById(R.id.other2B);
        String poths2 = poth2.getText().toString();
        EditText pothF2= findViewById(R.id.other2F);
        String pothFs2 = pothF2.getText().toString();

        EditText poth3= findViewById(R.id.other3B);
        String poths3 = poth3.getText().toString();
        EditText pothF3= findViewById(R.id.other3F);
        String pothFs3 = pothF3.getText().toString();

        Spinner agenda = findViewById(R.id.agendaSpinner);

        String agendas=null;
        if (agenda.getSelectedItem()!=null)
            agendas=agenda.getSelectedItem().toString();
        Log.i("info123","Passed here");

        if(pnames.length() <1){Toast.makeText(Proposal.this,"Enter Name ",Toast.LENGTH_SHORT).show();}
        else if(pthemes.length() <1){Toast.makeText(Proposal.this,"Enter Theme",Toast.LENGTH_SHORT).show();}
        else if(edate.length() <1){Toast.makeText(Proposal.this,"Enter Event date",Toast.LENGTH_SHORT).show();}
        else if(speaker_s.length() <1){Toast.makeText(Proposal.this,"Enter Speaker's Detail",Toast.LENGTH_SHORT).show();}
        else if(venue_s.length() <1){Toast.makeText(Proposal.this,"Enter Venue",Toast.LENGTH_SHORT).show();}
        else if(csi_s.length() <1){Toast.makeText(Proposal.this,"Enter CSI Members Fee",Toast.LENGTH_SHORT).show();}
        else if(ncsi_s.length() <1){Toast.makeText(Proposal.this,"Enter Non-CSI Members Fee",Toast.LENGTH_SHORT).show();}
        else if(prize_s.length() <1){Toast.makeText(Proposal.this,"Enter Prize Money",Toast.LENGTH_SHORT).show();}
        else if(pdescs.length() <1){Toast.makeText(Proposal.this,"Enter Description",Toast.LENGTH_SHORT).show();}
        else if(pcbs.length() <1){Toast.makeText(Proposal.this,"Enter Creative Budget",Toast.LENGTH_SHORT).show();}
        else if(ppbs.length() <1){Toast.makeText(Proposal.this,"Enter Publicity Budget ",Toast.LENGTH_SHORT).show();}
        else if(pguests.length() <1){Toast.makeText(Proposal.this,"Enter guests ",Toast.LENGTH_SHORT).show();}

        else if ((poths1.length() >0 && pothFs1.length() ==0) || (poths1.length() ==0 && pothFs1.length() >0)){Toast.makeText(Proposal.this,"Enter 1st other field",Toast.LENGTH_SHORT).show();}

        else if ((poths2.length() >0 && pothFs2.length() ==0)|| (poths2.length() ==0 && pothFs2.length() >0)){Toast.makeText(Proposal.this,"Enter 2nd other field",Toast.LENGTH_SHORT).show();}

        else if ((poths3.length() >0 && pothFs3.length() ==0)||(poths3.length() ==0 && pothFs3.length() >0)){Toast.makeText(Proposal.this,"Enter 3rd other field",Toast.LENGTH_SHORT).show();}

        else if(agendas==null || agendas=="SELECT"){Toast.makeText(Proposal.this,"Enter agenda",Toast.LENGTH_SHORT).show();}
        else{

            JSONObject jsub = new JSONObject();
            if (poths1.length() >0 && pothFs1.length() >0) {
                try {
                    jsub.put(pothFs1,poths1);
                    preSub+="\nOtherField1  "+pothFs1+" : "+poths1;
                    Log.i("i123",jsub.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (poths2.length() >0 && pothFs2.length() >0) {try {
                jsub.put(pothFs2,poths2);
                preSub+="\nOtherField2  "+pothFs2+" : "+poths2;
                Log.i("i123",jsub.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }}
            if (poths3.length() >0 && pothFs3.length() >0) {try {
                jsub.put(pothFs3,poths3);
                preSub+="\nOtherField3  "+pothFs3+" : "+poths3;
                Log.i("i123",jsub.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }}

            JSONObject jsonobject = new JSONObject();
            try {
                jsonobject.put("name",pnames);
                preview+="Name : "+pnames;
//                21sep


                preview+="\nSpeaker : "+speaker_s;jsonobject.put("speaker",speaker_s);

                preview+="\nVenue : "+venue_s;jsonobject.put("venue",venue_s);

                preview+="\nRegistration Fee \n CSI Member : "+csi_s;jsonobject.put("reg_fee",csi_s);

                preview+="\nNon-CSI member : "+ncsi_s;jsonobject.put("reg_fee_n",ncsi_s);

                preview+="\nWorth Prize : "+prize_s;jsonobject.put("prize",prize_s);


                jsonobject.put("theme",pthemes);
                preview+="\nTheme : "+pthemes;
                jsonobject.put("e_date",edate);
                preview+="\nEvent date : "+edate;
                jsonobject.put("description",pdescs);
                preview+="\nDescription : "+pdescs;
                jsonobject.put("agenda",agendas);
                preview+="\nAgenda : "+agendas;
                jsonobject.put("date",date);
                preview+="\nDate : "+date;
                jsonobject.put("cb",pcbs);
                preview+="\nCreative Budget : "+pcbs;
                jsonobject.put("pb",ppbs);
                preview+="\nPublicity Budget : "+ppbs;
                jsonobject.put("gb",pguests);
                preview+="\nGuests : "+pguests;
                Log.i("i123",jsonobject.toString());
                jsonobject.put("ob",jsub);
                preview+="\nOther Field : "+preSub;
                Log.i("i123",jsonobject.toString());

                customDialog(preview,jsonobject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }


    public void customDialog(String message , final JSONObject jsonobject){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(Proposal.this);
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

                        sendData("http://159.65.144.246:8081/proposal/createproposal",jsonobject,1);
                        finish();

                    }
                });

        builderSingle.show();
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
