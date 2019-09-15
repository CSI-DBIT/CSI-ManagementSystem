package com.example.csi.mActivityManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.csi.Gallery.Activities.DisplayImage;
import com.example.csi.Gallery.ImageFilePath;
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

    public String mediaType = "Image";
    Button uploadImage, uploadVideo;
    Uri selectedImage;
    OkHttpClient client;
    RequestBody request_body;
    ArrayList<RequestBody> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creative_form);

        uploadImage = (Button) findViewById(R.id.uploadImage);
        uploadVideo = (Button) findViewById(R.id.uploadVideo);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaType="Image";
                Toast.makeText(Creative_form.this, mediaType, Toast.LENGTH_SHORT).show();
                UploadPosters();
            }
        });

        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaType="Video";
                Toast.makeText(Creative_form.this, mediaType, Toast.LENGTH_SHORT).show();
                UploadVideos();
            }
        });
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
    }

    private String getMimeType(String path) {

        String extention = path.substring(path.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extention);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
    }
}
