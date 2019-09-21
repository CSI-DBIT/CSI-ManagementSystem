package com.example.csi.Prompts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csi.R;
import com.example.csi.SharedPreferenceConfig;
import com.example.csi.mFragments.About_us;
import com.example.csi.mFragments.ActivityManager;
import com.example.csi.mFragments.Attendance;
import com.example.csi.mFragments.AttendancePR;
import com.example.csi.mFragments.Developers;
import com.example.csi.mFragments.MinuteManager;
import com.example.csi.mFragments.Profile;
import com.squareup.picasso.Picasso;


//This contains java code for navigation drawer activity

public class Manager extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferenceConfig preferenceConfig; //....6/6/2019
    public static final String EXTRA_UROLE = "com.example.csimanagementsystem.EXTRA_UROLE";

    String uid, uname, urole, uProfile;

    TextView UNAME, UID, UROLE;
    View mView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_activity_manager);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());  //....6/6/2019

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get data sent by Mainactivity.java startsact
        Intent intent = getIntent();
        uid = intent.getStringExtra(MainActivity.EXTRA_UID);
        Log.i("tracking uid","manager when received "+uid);

        uname = intent.getStringExtra(MainActivity.EXTRA_UNAME);
        urole = intent.getStringExtra(MainActivity.EXTRA_UROLE);
        uProfile = intent.getStringExtra(MainActivity.EXTRA_URL);
        //get data sent by Mainactivity.java ends

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.server_url_button);
        //we are not using this floating button as of now
        //This button is in app_bar_manager.xml
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mView = navigationView.getHeaderView(0); //created an object for HeaderView in navigationView

        UID = (TextView)mView.findViewById(R.id.uid_final);
        UNAME = (TextView)mView.findViewById(R.id.uname_final);
        UROLE = (TextView)mView.findViewById(R.id.uroll_final);

        UID.setText(uid); //Setting data of user into the header of navigation view
        UNAME.setText(uname);
        UROLE.setText(urole);

        navigationView.setNavigationItemSelectedListener(this);

        imageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        loadImageUrl(uProfile);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.containerID, ActivityManager.newInstance()).addToBackStack(null).commit();
        }
    }

    private void loadImageUrl(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    //below mathod id used to close the drawer... if its is open while pressing backpress key
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                getSupportFragmentManager().popBackStack();
            }
            else {
                //finish();
                finishAffinity();
                //super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            Toast.makeText(Manager.this,String.valueOf(getSupportFragmentManager().getBackStackEntryCount()),Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("id",uid);
            bundle.putString("profilePic",uProfile);
            Log.i("tracking uid","in manager sending to profile "+uid);


            Profile profile = new Profile();
            profile.setArguments(bundle);
            if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                getSupportFragmentManager().popBackStack();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.containerID, profile).addToBackStack(null).commit();
        } else if (id == R.id.nav_activity_manager) {
            Toast.makeText(Manager.this,String.valueOf(getSupportFragmentManager().getBackStackEntryCount()),Toast.LENGTH_SHORT).show();
            if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                getSupportFragmentManager().popBackStack();
            }
            else if(getSupportFragmentManager().getBackStackEntryCount() != 1){
                getSupportFragmentManager().beginTransaction().replace(R.id.containerID, ActivityManager.newInstance()).addToBackStack(null).commit();
            }
        } else if (id == R.id.nav_minute_manager) {
            Toast.makeText(Manager.this,String.valueOf(getSupportFragmentManager().getBackStackEntryCount()),Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            //we are sending id to the MinuteManager.java
            //from minute manager this id will be sent to AddMinute.java
            bundle.putString("id",uid);
            Log.i("tracking uid","in manager sending to minute "+uid);

            MinuteManager minuteManager = new MinuteManager();
            minuteManager.setArguments(bundle);

            //if we are passing some data to fragment we will use above 4 statements
            //and use name of the object created (in 3rd line) in below line

            //if u r not passing anything then use MinuteManager.newInstance() instead of object created (i.e. minuteManager)
            if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                getSupportFragmentManager().popBackStack();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.containerID, minuteManager).addToBackStack(null).commit();

        } else if (id == R.id.nav_attendance) {
            Toast.makeText(Manager.this,String.valueOf(getSupportFragmentManager().getBackStackEntryCount()),Toast.LENGTH_SHORT).show();
            if(urole.equals("PR Head") || urole.equals("SBC"))
            {
                if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                    getSupportFragmentManager().popBackStack();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.containerID, AttendancePR.newInstance()).addToBackStack(null).commit();
            }
            else
            {
                Bundle bundle = new Bundle();
                bundle.putString("id",uid);
                Log.i("tracking uid","in manager sending to attendence "+uid);

                Attendance attendance = new Attendance();
                attendance.setArguments(bundle);
                if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                    getSupportFragmentManager().popBackStack();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.containerID, attendance).addToBackStack(null).commit();
            }
            //Toast.makeText(Manager.this,"Not yet implemented",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_developers) {
            Developers developers = new Developers();

            if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                getSupportFragmentManager().popBackStack();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.containerID, developers).addToBackStack(null).commit();
            //Toast.makeText(Manager.this,String.valueOf(getSupportFragmentManager().getBackStackEntryCount()),Toast.LENGTH_SHORT).show();
            //Toast.makeText(Manager.this,"Not yet implemented",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_Feedback) {
            Toast.makeText(Manager.this,String.valueOf(getSupportFragmentManager().getBackStackEntryCount()),Toast.LENGTH_SHORT).show();
            Toast.makeText(Manager.this,"Not yet implemented",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_about_us) {
            About_us about_us = new About_us();

            if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                getSupportFragmentManager().popBackStack();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.containerID, about_us).addToBackStack(null).commit();
            //Toast.makeText(Manager.this,String.valueOf(getSupportFragmentManager().getBackStackEntryCount()),Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, preferenceConfig.readLoginStatus(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(Manager.this,"Not yet implemented",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.log_out) {
            //....6/6/2019
            preferenceConfig.writeLoginStatus(false,"","","","","");
            startActivity(new Intent(this, MainActivity.class));
            finish();
            //....6/6/2019
            Toast.makeText(Manager.this,"Log Out",Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
