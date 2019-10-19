package com.example.csi.mActivityManager;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
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
import com.example.csi.Gallery.Activities.DisplayImage;
import com.example.csi.Gallery.ImageFilePath;
import com.example.csi.Prompts.MainActivity;
import com.example.csi.Prompts.Manager;
import com.example.csi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Creative_form extends AppCompatActivity {

    public String mediaType = "Image", eid;
    public String server_url = "http://159.65.144.246:8081/creative/viewpropdetail";
    String name, theme, eventDate, description, creativeBudget, date1;
    String dSpeaker, dVenue, dFeeCSI, dFeeNonCSI, dPrize, dPublicityBudget, dGuestBudget;

    TextView eventName, eventTheme, event_date, eventDescription, creative_budget;
    TextView speaker, venue, fee_csi, fee_non_csi, prize, publicity_budget, guest_budget;

    Button uploadImage, uploadVideo;
    Uri selectedImage;
    OkHttpClient client;
    RequestBody request_body;
    ArrayList<RequestBody> images;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creative_form);

        Log.i("sanket testing", "entered");
        //Toast.makeText(this, "creative form", Toast.LENGTH_SHORT).show();
        eventName = (TextView)findViewById(R.id.name);
        eventTheme = (TextView)findViewById(R.id.theme);
        event_date = (TextView)findViewById(R.id.ed);
        speaker = (TextView) findViewById(R.id.speaker);
        venue = (TextView) findViewById(R.id.venue);
        fee_csi = (TextView) findViewById(R.id.fee_csi);
        fee_non_csi = (TextView) findViewById(R.id.fee_non_csi);
        prize = (TextView) findViewById(R.id.prize);
        eventDescription = (TextView)findViewById(R.id.desc_pd);
        creative_budget = (TextView)findViewById(R.id.cb);
        publicity_budget = (TextView) findViewById(R.id.pb);
        guest_budget = (TextView) findViewById(R.id.gb);

        Intent intent = getIntent();
        eid = intent.getStringExtra(Creative.EXTRA_EID);
        Log.i("eid",eid);

        insertSrv();

        progress = new ProgressDialog(Creative_form.this);

        getSupportActionBar().setTitle("Creative Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        uploadImage = (Button) findViewById(R.id.uploadImage);
        uploadVideo = (Button) findViewById(R.id.uploadVideo);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaType="Image";
                //Toast.makeText(Creative_form.this, mediaType, Toast.LENGTH_SHORT).show();
                UploadPosters();
            }
        });

        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaType="Video";
                //Toast.makeText(Creative_form.this, mediaType, Toast.LENGTH_SHORT).show();
                UploadVideos();
            }
        });
    }

    private void insertSrv()
    {
        //creating jsonobject starts
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eid", eid);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //creating jsonobject ends

        //checking data inserted into json object
        final String requestBody = jsonObject.toString();
        Log.i("volleyABC123", requestBody);

        //getting response from server starts
        StringRequest stringRequest = new StringRequest(Request.Method.POST,server_url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                Log.i("volleyABC4985" ,"got response    "+response);
                //Toast.makeText(Creative_form.this, "Logged IN", Toast.LENGTH_SHORT).show();

                Intent manager = new Intent(Creative_form.this, Manager.class);

                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    // Log.i("tracking uid","main Activity "+UID);
                    name = jsonObject1.getString("name");
                    theme = jsonObject1.getString("theme");
                    eventDate = jsonObject1.getString("event_date");
                    dSpeaker = jsonObject1.getString("speaker");
                    dVenue = jsonObject1.getString("venue");
                    dFeeCSI = jsonObject1.getString("reg_fee_c");
                    dFeeNonCSI = jsonObject1.getString("reg_fee_nc");
                    dPrize = jsonObject1.getString("prize");
                    description = jsonObject1.getString("description");
                    creativeBudget = jsonObject1.getString("creative_budget");
                    dPublicityBudget = jsonObject1.getString("publicity_budget");
                    dGuestBudget = jsonObject1.getString("guest_budget");
                    Log.i("sexa", name);

                    date1 = eventDate.substring(8,10) + "/" + eventDate.substring(5,7) + "/" + eventDate.substring(0,4);

                    //Send data to Manager.java starts
                    // Call manager.java file i.e. Activity with navigation drawer activity
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                eventName.setText(name);
                eventTheme.setText(theme);
                event_date.setText(date1);
                speaker.setText(dSpeaker);
                venue.setText(dVenue);
                fee_csi.setText(dFeeCSI);
                fee_non_csi.setText(dFeeNonCSI);
                prize.setText(dPrize);
                eventDescription.setText(description);
                creative_budget.setText(creativeBudget);
                publicity_budget.setText(dPublicityBudget);
                guest_budget.setText(dGuestBudget);
            }
        },new Response.ErrorListener()  {

            @Override
            public void onErrorResponse(VolleyError error) {

                try{
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(Creative_form.this, "Invalid Credentials", Toast.LENGTH_SHORT).show(); //This method is used to show pop-up on the screen if user gives wrong uid


                    error.printStackTrace();}
                catch (Exception e)
                {
                    Toast.makeText(Creative_form.this,"Check Network",Toast.LENGTH_SHORT).show();}
            }
        }){
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

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest); // get response from server
    }



    private void UploadPosters() {

        //calling requestMethod
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

        enable_poster_button();
    }

    private void UploadVideos() {

        //calling requestMethod
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

        enable_video_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            if(mediaType.equals("image")) {
                enable_poster_button();
            }
            else {
                enable_video_button();
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
    }

    private void enable_poster_button() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Poster"), 1);

    }

    private void enable_video_button() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("video/*");
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Video"), 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            progress.setTitle("Uploading");
            progress.setMessage("Please wait...");
            progress.show();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        if (data.getClipData() != null) {

                            int Count = data.getClipData().getItemCount();
                            Log.i("Total Count", String.valueOf(Count));
                            Log.i("data", String.valueOf(data));

                            MultipartBody.Builder buildernew = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM);

                            for (int i = 0; i < Count; i++) {
                                Log.i("checkingLoop", "This is loop " + i);
                                selectedImage = data.getClipData().getItemAt(i).getUri();

                                Log.i("sanket", String.valueOf(selectedImage));

                                String realPath = ImageFilePath.getPath(Creative_form.this, data.getClipData().getItemAt(i).getUri());
                                Log.i("finalPathReal", realPath);

                                File f = new File(realPath);
                                String content_type = getMimeType(realPath);
                                //String content_type = "image/*";
                                Log.i("content_type", "CT :- " + content_type);

                                client = new OkHttpClient();
                                RequestBody file_body;
                                if(mediaType.equals("image")) {
                                    file_body = RequestBody.create(MediaType.parse("image/*"), f);
                                }
                                else {
                                    file_body = RequestBody.create(MediaType.parse("video/*"), f);
                                }
                                Log.i("file_body", String.valueOf(file_body));
                                Log.i("file_path substring", realPath.substring(realPath.lastIndexOf("/") + 1));

                                images = new ArrayList<>();
                                images.add(request_body);

                                buildernew.addFormDataPart("file", realPath.substring(realPath.lastIndexOf("/")), file_body);
                            }

                            RequestBody requestBody = buildernew.build();

                            request_body = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    //.addFormDataPart("type",content_type)
                                    .addFormDataPart("", images.toString())
                                    //.addFormDataPart("file", realPath.substring(realPath.lastIndexOf("/") + 1), file_body)
                                    .build();
                            Log.i("requesting body", request_body.toString());

                            okhttp3.Request request = new okhttp3.Request.Builder()
                                    //.url("http://192.168.43.84:8080/pic")
                                    .url("http://159.65.144.246:8081/creative/upload")    //Main Server URL)
                                    //.url("http://192.168.42.156:8080/upload")
                                    .post(requestBody)
                                    .build();

                            Log.i("request", String.valueOf(request));

                            try {
                                okhttp3.Response response = client.newCall(request).execute();
                                Log.i("response on upload", "Response" + response);
                                //Toast.makeText(Creative_form.this, "Images Uploaded Succefully", Toast.LENGTH_SHORT).show();

                                if (!response.isSuccessful()) {
                                    throw new IOException("Error : " + response);
                                }

                                //progress.dismiss();

                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.i("MultiPart", "Something went wrong");
                            }
                        } else {

                            selectedImage = data.getData();

                            Log.i("sanket", String.valueOf(selectedImage));

                            String realPath = ImageFilePath.getPath(Creative_form.this, data.getData());
                            Log.i("finalPathReal", realPath);

                            File f = new File(realPath);
                            String content_type = getMimeType(realPath);
                            //String content_type = "image/*";
                            Log.i("content_type2", "CT :- " + content_type);

                            OkHttpClient client = new OkHttpClient.Builder()
                                    .connectTimeout(100, TimeUnit.SECONDS)
                                    .writeTimeout(100, TimeUnit.SECONDS)
                                    .readTimeout(300, TimeUnit.SECONDS)
                                    .build();

                            RequestBody file_body;
                            if(mediaType.equals("image")) {
                                file_body = RequestBody.create(MediaType.parse("image/*"), f);
                            }
                            else {
                                file_body = RequestBody.create(MediaType.parse("video/*"), f);
                            }
                            Log.i("file_body", String.valueOf(file_body));
                            Log.i("file_path substring", realPath.substring(realPath.lastIndexOf("/") + 1));

                            RequestBody request_body = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    //.addFormDataPart("type",content_type)
                                    .addFormDataPart("file", realPath.substring(realPath.lastIndexOf("/") + 1), file_body)
                                    .build();

                            okhttp3.Request request = new okhttp3.Request.Builder()
                                    //.url("http://192.168.43.84:8080/pic")
                                    .url("http://159.65.144.246:8081/creative/upload")
                                    //.url("http://192.168.42.156:8080/upload")
                                    .post(request_body)
                                    .build();

                            Log.i("request", String.valueOf(request));

                            try {
                                okhttp3.Response response = client.newCall(request).execute();
                                Log.i("response", "Response" + response);
                                //Toast.makeText(Creative_form.this, mediaType + " Uploaded Successfully", Toast.LENGTH_SHORT).show();

                                if (!response.isSuccessful()) {
                                    throw new IOException("Error : " + response);
                                }

                                progress.dismiss();

                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.i("MultiPart", "Something went wrong", e);
                            }
                        }
                    }
                }
            });
            t.start();
        }
    }

    private String getMimeType(String path) {

        String extention = path.substring(path.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extention);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
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
