package com.example.csi.Gallery.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.Gallery.DisplayImageAdapter.GalleryImageAdapter;
import com.example.csi.Gallery.ImageFilePath;
import com.example.csi.Gallery.Interfaces.IRecyclerViewClickListener;
import com.example.csi.R;

import org.json.JSONArray;
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

public class DisplayImage extends AppCompatActivity {

    String PARENT_PATH = "";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton Fab;
    ArrayList<String> imagesUrl;
    GalleryImageAdapter galleryImageAdapter;
    private RequestQueue mRequestQueue;

    //updated variables use to upload images only
    ProgressDialog progress;
    Uri selectedImage;
    OkHttpClient client;
    RequestBody request_body;
    ArrayList<RequestBody> images;

    @Override
    protected void onStart() {
        super.onStart();

        initURL3();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        getSupportActionBar().setTitle("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        PARENT_PATH = i.getStringExtra("YEAR");
        Log.i("path",PARENT_PATH);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Fab = (FloatingActionButton) findViewById(R.id.fab);
        mRequestQueue = Volley.newRequestQueue(this);
        imagesUrl = new ArrayList<>();

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), Upload.class);
                //intent.putExtra("PATH", PARENT_PATH);
                //startActivity(intent);
                UploadImages();
            }
        });

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        final IRecyclerViewClickListener listener = new IRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getApplicationContext(), FullScreenActivity.class);
                i.putExtra("IMAGES",imagesUrl);
                i.putExtra("POSITION",position);
                startActivity(i);
            }
        };

        galleryImageAdapter = new GalleryImageAdapter(this, imagesUrl, listener);
        recyclerView.setAdapter(galleryImageAdapter);

        initURL3();

        Log.i("imagesUrl", String.valueOf(imagesUrl));

    }

    public void initURL3() {

        //String url = "http://192.168.43.84:8080/view";
        String url = "http://159.65.144.246:8082/view";    //Main Server URL
        //String url = "http://192.168.42.156:8080/view";
        //creating jsonobject starts
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("path", PARENT_PATH);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //creating jsonobject ends

        //checking data inserted into json object
        final String requestBody = jsonObject.toString();
        Log.i("volleyABC", requestBody);

        //getting response from server starts
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                Log.i("volleyABC" ,"got response    "+response);
                Toast.makeText(DisplayImage.this, "Got Event List", Toast.LENGTH_SHORT).show();
                imagesUrl.clear();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    Log.i("json length", String.valueOf(jsonArray.length()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String event =  jsonArray.getString(i);
                        Log.i("event" ,"event " + i + " :- " + jsonArray.getString(i));
                        event = event.replace("localhost","192.168.43.84");
                        //event = event.replace("localhost","192.168.42.156");
                        Log.i("events " + i, event);
                        imagesUrl.add(event);
                    }
                    Log.i("imagesUrlPre", String.valueOf(imagesUrl));
                    galleryImageAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(galleryImageAdapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()  {

            @Override
            public void onErrorResponse(VolleyError error) {

                try{
                    Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                    Toast.makeText(DisplayImage.this, "Invalid", Toast.LENGTH_SHORT).show(); //This method is used to show pop-up on the screen if user gives wrong uid


                    error.printStackTrace();}
                catch (Exception e)
                {
                    Toast.makeText(DisplayImage.this,"Check Network",Toast.LENGTH_SHORT).show();}
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

        mRequestQueue.add(stringRequest);
    }

    private void UploadImages() {
        //progress = new ProgressDialog(GalleryActivity.this);

        //button = findViewById(R.id.pick_image);
        //tv = findViewById(R.id.textView);

        //calling requestMethod
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }

        enable_button();
    }

    private void enable_button() {

        //button.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View v) {

        //open
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"), 1);

        //  }
        //});

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            enable_button();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        progress.dismiss();
//    }

    //Created by Sanku... 03/07/2019... 10:30 AM.... Upload Single image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            //String url = "http://192.168.43.84:8080/path";
            String url = "http://159.65.144.246:8082/path";    //Main Server URL
            //String url = "http://192.168.42.156:8080/path";
            //creating jsonobject starts
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("path", PARENT_PATH);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            //creating jsonobject ends

            //checking data inserted into json object
            final String requestBody = jsonObject.toString();
            Log.i("volleyABC", requestBody);

            //getting response from server starts
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,url,new com.android.volley.Response.Listener<String>(){
                @Override
                public void onResponse(String response) {

                    Log.i("volleyABCPATH" ,"got response    "+response);
                    Toast.makeText(DisplayImage.this, "Got Event List", Toast.LENGTH_SHORT).show();

                    afterActivityResult(data);
                }
            },new com.android.volley.Response.ErrorListener()  {

                @Override
                public void onErrorResponse(VolleyError error) {

                    try{
                        Log.i("volleyABC" ,Integer.toString(error.networkResponse.statusCode));
                        Toast.makeText(DisplayImage.this, "Invalid", Toast.LENGTH_SHORT).show(); //This method is used to show pop-up on the screen if user gives wrong uid


                        error.printStackTrace();}
                    catch (Exception e)
                    {
                        Toast.makeText(DisplayImage.this,"Check Network",Toast.LENGTH_SHORT).show();}
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

            RequestQueue mRequestQueue = Volley.newRequestQueue(this);
            mRequestQueue.add(stringRequest);

        }
    }

    public void afterActivityResult(Intent data) {
//        progress.setTitle("Uploading");
//        progress.setMessage("Please wait...");
//        progress.show();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if(data.getClipData() != null) {

                        int Count = data.getClipData().getItemCount();
                        Log.i("Total Count", String.valueOf(Count));
                        Log.i("data", String.valueOf(data));

                        MultipartBody.Builder buildernew = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM);

                        for (int i = 0; i < Count; i++) {
                            Log.i("checkingLoop", "This is loop " + i);
                            selectedImage = data.getClipData().getItemAt(i).getUri();

                            Log.i("sanket", String.valueOf(selectedImage));

                            String realPath = ImageFilePath.getPath(DisplayImage.this, data.getClipData().getItemAt(i).getUri());
                            Log.i("finalPathReal", realPath);

                            File f = new File(realPath);
                            String content_type = getMimeType(realPath);
                            //String content_type = "image/*";
                            Log.i("content_type", "CT :- " + content_type);

                            client = new OkHttpClient();
                            RequestBody file_body = RequestBody.create(MediaType.parse("image/*"), f);
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
                                //.url("http://192.168.43.84:8080/upload")
                                .url("http://159.65.144.246:8082/upload")    //Main Server URL)
                                //.url("http://192.168.42.156:8080/upload")
                                .post(requestBody)
                                .build();

                        Log.i("request", String.valueOf(request));

                        try {
                            okhttp3.Response response = client.newCall(request).execute();
                            Log.i("response on upload", "Response" + response);
                            initURL3();

                            if (!response.isSuccessful()) {
                                throw new IOException("Error : " + response);
                            }

                            //progress.dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.i("MultiPart", "Something went wrong");
                        }
                    }
                    else {

                        selectedImage = data.getData();

                        Log.i("sanket", String.valueOf(selectedImage));

                        String realPath = ImageFilePath.getPath(DisplayImage.this, data.getData());
                        Log.i("finalPathReal",realPath);

                        File f = new File(realPath);
                        String content_type = getMimeType(realPath);
                        //String content_type = "image/*";
                        Log.i("content_type2", "CT :- " + content_type);

                        OkHttpClient client = new OkHttpClient.Builder()
                                .connectTimeout(100, TimeUnit.SECONDS)
                                .writeTimeout(100, TimeUnit.SECONDS)
                                .readTimeout(300, TimeUnit.SECONDS)
                                .build();

                        RequestBody file_body = RequestBody.create(MediaType.parse("image/*"), f);
                        Log.i("file_body", String.valueOf(file_body));
                        Log.i("file_path substring", realPath.substring(realPath.lastIndexOf("/") + 1));

                        RequestBody request_body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                //.addFormDataPart("type",content_type)
                                .addFormDataPart("file", realPath.substring(realPath.lastIndexOf("/") + 1), file_body)
                                .build();

                        okhttp3.Request request = new okhttp3.Request.Builder()
                                //.url("http://192.168.43.84:8080/upload")
                                .url("http://159.65.144.246:8082/upload")
                                //.url("http://192.168.42.156:8080/upload")
                                .post(request_body)
                                .build();

                        Log.i("request", String.valueOf(request));

                        try {
                            okhttp3.Response response = client.newCall(request).execute();
                            Log.i("response", "Response" + response);
                            initURL3();

                            if (!response.isSuccessful()) {
                                throw new IOException("Error : " + response);
                            }

                            //progress.dismiss();

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

    private String getMimeType(String path) {

        String extention = path.substring(path.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extention);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
    }

    public void initURL(){

        imagesUrl.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        imagesUrl.add("https://www.radiantmediaplayer.com/media/bbb-360p.mp4");
        imagesUrl.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        imagesUrl.add("https://i.redd.it/j6myfqglup501.jpg");
        imagesUrl.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        imagesUrl.add("https://i.redd.it/k98uzl68eh501.jpg");
        imagesUrl.add("https://i.redd.it/glin0nwndo501.jpg");
        imagesUrl.add("https://i.redd.it/obx4zydshg601.jpg");
        imagesUrl.add("https://i.imgur.com/ZcLLrkY.jpg");
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
