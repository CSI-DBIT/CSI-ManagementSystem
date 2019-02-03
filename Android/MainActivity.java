package com.example.loginpage_f1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    EditText Userid, Password;
    TextView textView, textView2, eror;
    Button Login;

    String id, pasword;

    RequestQueue req;

    String server_url="http://fossilinsects.myspecies.info/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Userid = (EditText) findViewById(R.id.userid);
        Password = (EditText) findViewById(R.id.password) ;
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        eror = (TextView) findViewById(R.id.eror);
        Login = (Button) findViewById(R.id.Login_button);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid = Userid.getText().toString();
                String pstring = Password.getText().toString();

                if(uid.length()==10 && (21 > pstring.length() && pstring.length()>=8)) {

                    req = Volley.newRequestQueue(MainActivity.this);

                    StringRequest strreq  = new StringRequest(Request.Method.POST, server_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    eror.setText("Response is: "+ response.substring(0,500));
                                    //Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                                    Intent role_intent = new Intent(MainActivity.this, role_intent.class);
                                    startActivity(role_intent);
                                    req.stop();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            eror.setText("That didn't work!");
                            String t="Connection Error!!";
                            String body = "Hello Programmer";
                            final String statusCode = String.valueOf(error.networkResponse.statusCode);

                            Toast.makeText(MainActivity.this,t,Toast.LENGTH_SHORT).show();

                            eror.setText(statusCode);
                            error.printStackTrace();
                            req.stop();
                        }
                    });
                    req.add(strreq);
                    sendjsonRequest();
                }

                else {
                    //String t="Invalid input!!";
                    //Toast.makeText(MainActivity.this,t,Toast.LENGTH_SHORT).show();

                    Toast.makeText(MainActivity.this,getString(R.string.invalid_input_toast),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendjsonRequest() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, server_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            id = response.getString("Userid");
                            pasword = response.getString("Password");
                        }catch (UnsupportedEncodingException ueee) {
                            Toast.makeText(MainActivity.this, "OMG", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        req.add(jsonObjectRequest);
    }
}
//purpose, feature and function