package com.example.csi;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class SharedPreferenceConfig {

    private SharedPreferences sharedPreferences;
    private Context context;
    EditText userid, password;



    public SharedPreferenceConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preference), Context.MODE_PRIVATE);

    }

    public void writeLoginStatus(boolean status, String uid, String pwd, String role, String UserName, String ProfileUrl){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userid",uid);
        editor.putString("password",pwd);
        editor.putString("role",role);//update
        editor.putString("userName",UserName);
        editor.putString("profileURL",ProfileUrl);
        editor.apply();
        //editor.putBoolean(context.getResources().getString(R.string.login_status_preference), status);
       // editor.commit();
    }

    public String readLoginStatus(){
        //boolean status = false;

        String name = sharedPreferences.getString("userid","");
        String password = sharedPreferences.getString("password","");

        if(name!="" && password!=""){
            return name;
        }
        else
            return "";
        //status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference), false);
        //return status;
    }

    public String readRoleStatus(){
        //boolean status = false;

            return sharedPreferences.getString("role","");
        //status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference), false);
        //return status;
    }

    public String readNameStatus(){

        return sharedPreferences.getString("userName","");
    }

    public String readUrlStatus(){

        return sharedPreferences.getString("profileURL","");
    }
}
