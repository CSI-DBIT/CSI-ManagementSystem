package com.example.csi.mFragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.Prompts.ProfileEdit;
import com.example.csi.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Profile extends Fragment {

    String server_url;   //Main Server URL
    //String server_url="http://192.168.43.84:8080/profile";
    //string to store position as we are not showing it in any textview
    String position_s=" ", UID, UProfile;
    View rootView;
    ImageView imageButton;

    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        final SwipeRefreshLayout swipeRefreshLayout1 = rootView.findViewById(R.id.refresher1);
        swipeRefreshLayout1.setRefreshing(true);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout1.setRefreshing(false);

                int min = 65;
                int max = 95;

                get_data();
            }
        },1000);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        rootView = inflater.inflate(R.layout.activity_profile,container,false);
        server_url = rootView.getResources().getString(R.string.server_url) + "/profile/";
        getActivity().setTitle("My Profile");
        Bundle bundle = getArguments();
        UID = this.getArguments().getString("id");
        UProfile = this.getArguments().getString("profilePic");
        swipe();
        imageButton = rootView.findViewById(R.id.profile_photo);
        loadImageUrl(UProfile);

        Button edit_button = rootView.findViewById(R.id.edit_button);

        //decalring varriables
        TextView id = rootView.findViewById(R.id.id);
        //id will get from called intent
        // id.setText(getIntent().getStringExtra("id from respective intent"));
        id.setText(UID);

        get_data();//fetch data from server

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("volleyABC", "onClick: reached here");

                TextView id = rootView.findViewById(R.id.id);
                TextView name = rootView.findViewById(R.id.profile_name);
                TextView email = rootView.findViewById(R.id.email);
                TextView phn = rootView.findViewById(R.id.phn);
                TextView yr = rootView.findViewById(R.id.year);
                TextView roln = rootView.findViewById(R.id.rollNo);
                TextView batch = rootView.findViewById(R.id.batch);
                TextView  branch= rootView.findViewById(R.id.branch);

                Intent edit_profile =new Intent(getActivity(), ProfileEdit.class);

                //paasing data to edit intent so only required data will be changed else everything will remain same
                edit_profile.putExtra("id",id.getText().toString());
                edit_profile.putExtra("name",name.getText().toString());
                edit_profile.putExtra("role",position_s);
                edit_profile.putExtra("email",email.getText().toString());
                edit_profile.putExtra("phone",phn.getText().toString());
                edit_profile.putExtra("year",yr.getText().toString());
                edit_profile.putExtra("branch",branch.getText().toString());
                edit_profile.putExtra("rollno",roln.getText().toString());
                edit_profile.putExtra("batch",batch.getText().toString());

                edit_profile.putExtra("profilePic", UProfile);

                startActivity(edit_profile);
                //finish();
            }
        });

        return rootView;
    }

    private void loadImageUrl(String url) {
        Picasso.with(getContext()).load(url).placeholder(R.mipmap.ic_launcher)
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

    void get_data() {
        JSONObject jsonObject = new JSONObject();
        TextView  id= rootView.findViewById(R.id.id);
        String id_s =  id.getText().toString();

        try {
            jsonObject.put("id",id_s); //actual value shud be id_s
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();
        Log.i("volleyABC ", requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("volleyABC response", response);
                //Toast.makeText(MainActivity.this,response, Toast.LENGTH_SHORT).show();
                set_data(response);//set data in textiles
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    //String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(getActivity(),"Invalid Username or Password",Toast.LENGTH_SHORT).show();//it will not occur as authenticating at start
                    error.printStackTrace();}
                catch (Exception e)
                {
                    Log.i("volleyABC" ,"exception");
                    Toast.makeText(getActivity(),"Check Network",Toast.LENGTH_SHORT).show();} //occur if connection not get estabilished
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
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    void set_data(String data) {
        Log.i("volleyABC", "set_data called"+data);
        TextView id = rootView.findViewById(R.id.id);
        TextView name = rootView.findViewById(R.id.profile_name);
        TextView email = rootView.findViewById(R.id.email);
        TextView phn = rootView.findViewById(R.id.phn);
        TextView yr = rootView.findViewById(R.id.year);
        TextView roln = rootView.findViewById(R.id.rollNo);
        TextView batch = rootView.findViewById(R.id.batch);
        TextView  branch = rootView.findViewById(R.id.branch);
        TextView membershipLeft = rootView.findViewById(R.id.membership);
        JSONObject fetchedData ;
        try {
            fetchedData= new JSONObject(data);

            id.setText(fetchedData.getString("id"));
            name.setText(fetchedData.getString("name"));
            position_s= fetchedData.getString("role");
            Log.i("volleyABC", "position value in main"+position_s);
            email.setText(fetchedData.getString("email"));
            phn.setText(fetchedData.getString("phone"));
            yr.setText(fetchedData.getString("year"));
            branch.setText(fetchedData.getString("branch"));
            roln.setText(fetchedData.getString("rollno"));
            batch.setText(fetchedData.getString("batch"));
            membershipLeft.setText(fetchedData.getString("membership_left"));
            Log.i("volleyABC", "set_data: createdjason object all");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void swipe() {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresher1);
        //swipeRefreshLayout.setColorSchemeResources(R.color.Red,R.color.OrangeRed,R.color.Yellow,R.color.GreenYellow,R.color.BlueViolet);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        int min = 65;
                        int max = 95;

                        get_data();
                    }
                },1000);
            }
        });
    }
}
