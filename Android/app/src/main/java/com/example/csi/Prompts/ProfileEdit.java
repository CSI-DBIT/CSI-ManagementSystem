package com.example.csi.Prompts;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileEdit extends AppCompatActivity {

    String server_url="http://tayyabali.in:9000/profile/edit";    //Main Server URL
    //String server_url="http://192.168.43.84:8080/profile/edit";
    String position_s, UProfile;

    ImageView imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        imageButton = findViewById(R.id.profile_photo_E);

        //declaring varriables
        Button save_button = findViewById(R.id.save_button);
        final TextView id = findViewById(R.id.id_E);
        final EditText name = findViewById(R.id.profile_name_E);
        final EditText email = findViewById(R.id.email_E);
        final EditText phn = findViewById(R.id.phn_E);
        final RadioGroup yr = findViewById(R.id.year_E);
        final RadioGroup branch = findViewById(R.id.branch_E);
        final EditText rol = findViewById(R.id.rollNo_E);
        final RadioGroup batch = findViewById(R.id.batch_E);

        //getting data from profile
        UProfile = getIntent().getStringExtra("profilePic");

        id.setText(getIntent().getStringExtra("id"));
        name.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));
        phn.setText(getIntent().getStringExtra("phone"));
        rol.setText(getIntent().getStringExtra("rollno"));
        position_s = getIntent().getStringExtra("role");

        String Year = getIntent().getStringExtra("year");
        switch (Year) {
            case "FE":
                RadioButton FE = findViewById(R.id.radio_FE);
                FE.setChecked(true);
                break;
            case "SE":
                RadioButton SE = findViewById(R.id.radio_SE);
                SE.setChecked(true);
                break;
            case "TE":
                RadioButton TE = findViewById(R.id.radio_TE);
                TE.setChecked(true);
                break;
            default:
                RadioButton BE = findViewById(R.id.radio_BE);
                BE.setChecked(true);
                break;
        }

        String Branch = getIntent().getStringExtra("branch");
        switch (Branch) {
            case "IT":
                RadioButton IT = findViewById(R.id.radio_IT);
                IT.setChecked(true);
                break;
            case "COMPS":
                RadioButton Comps = findViewById(R.id.radio_comps);
                Comps.setChecked(true);
                break;
            case "MECH":
                RadioButton MECH = findViewById(R.id.radio_mech);
                MECH.setChecked(true);
                break;
            default:
                RadioButton EXTC = findViewById(R.id.radio_extc);
                EXTC.setChecked(true);
                break;
        }

        String Batch = getIntent().getStringExtra("batch");
        switch (Batch) {
            case "A":
                RadioButton batch_a = findViewById(R.id.radio_A);
                batch_a.setChecked(true);
                break;
            case "B":
                RadioButton batch_b = findViewById(R.id.radio_B);
                batch_b.setChecked(true);
                break;
            case "C":
                RadioButton batch_c = findViewById(R.id.radio_C);
                batch_c.setChecked(true);
                break;
            default:
                RadioButton batch_d = findViewById(R.id.radio_D);
                batch_d.setChecked(true);
                break;
        }
//        Log.i("check data incoming", getIntent().getStringExtra("year") + " " + getIntent().getStringExtra("branch") + " " + getIntent().getStringExtra("batch"));
        Log.i("volleyABC", "position value" + position_s + getIntent().getStringExtra("role"));

        loadImageUrl(UProfile);

        save_button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                //Intent profile_after_edit = new Intent(ProfileEdit.this, MainActivity.class);

                String id_s = id.getText().toString();
                String name_s = name.getText().toString();
                String email_s = email.getText().toString();
                if (!isEmailValid(email_s)) {
                    // email.setError("Invalid Email Address");
                    Log.i("emailerror", "wrong email" + email_s);
                    email_s = getIntent().getStringExtra("email");
                    Toast.makeText(ProfileEdit.this, "wrong email continueing with existing email", Toast.LENGTH_SHORT).show();
                    Log.i("emailerror", "posted email" + email_s);
                }
                String phn_s = phn.getText().toString();
                String rol_s = rol.getText().toString();
                String yr_s;
                String branch_s;
                String batch_s;

                RadioButton yr_b = findViewById(yr.getCheckedRadioButtonId());
                RadioButton br_b = findViewById(branch.getCheckedRadioButtonId());
                RadioButton batch_b = findViewById(batch.getCheckedRadioButtonId());

                if (yr_b != null) yr_s = yr_b.getText().toString();
                else yr_s = getIntent().getStringExtra("year");
                if (br_b != null) branch_s = br_b.getText().toString();
                else branch_s = getIntent().getStringExtra("branch");
                if (batch_b != null) batch_s = batch_b.getText().toString();
                else batch_s = getIntent().getStringExtra("batch");

                post_edited_info(id_s, name_s, email_s, phn_s, yr_s, branch_s, rol_s, batch_s);//posting data on server
                //profile_after_edit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(profile_after_edit);
                //ProfileEdit.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, Profile.newInstance()).commit();
                //finish();

            }
        });

    }

    private void loadImageUrl(String url) {
        Picasso.with(getApplicationContext()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageButton, new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    void post_edited_info(String id_s, String name_s, String email_s, String  phn_s, String yr_s , String branch_s, String rol_s, final String batch_s)
    {
        Log.i("volleyABC", "Reached in get info");

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id_s);
            jsonObject.put("name",name_s);
            jsonObject.put("role",position_s); //actual value will get from loginpage
            jsonObject.put("email",email_s);
            jsonObject.put("phone",phn_s);
            jsonObject.put("year",yr_s);
            jsonObject.put("branch",branch_s);
            jsonObject.put("rollno",rol_s);
            jsonObject.put("batch",batch_s);
            Log.i("volleyABC", "Created jason");
        }
        catch (JSONException e) {
            e.printStackTrace();
            Log.i("volleyABC", "error in jason creation");
        }

        final String requestBody = jsonObject.toString();
        Log.i("volleyABC", requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("volleyABC", "onResponse:edit reached ");
                Toast.makeText(ProfileEdit.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                //Intent profile_after_edit =new Intent(ProfileEdit.this, MainActivity.class);

                //startActivity(profile_after_edit);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    //String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.i("volleyABC" ,"edit"+Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(ProfileEdit.this,"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                    error.printStackTrace();}
                catch (Exception e)
                {
                    Log.i("volleyABC" ,"edit exception");
                    Toast.makeText(ProfileEdit.this,"Check Network",Toast.LENGTH_SHORT).show();}

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
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //Below Method is used to close the app after using Back Press
    /*
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
    }*/
}
