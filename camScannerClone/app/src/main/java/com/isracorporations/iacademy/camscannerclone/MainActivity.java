package com.isracorporations.iacademy.camscannerclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    int PERMISSION_ALL = 1;
    Context context;
    Uri imagesUri;
    Button btn,gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context= MainActivity.this;
        btn = (Button)findViewById(R.id.button);
        gallery = (Button)findViewById(R.id.button2);
        if(hasPermissions(context,PERMISSIONS)){
            action();
        }else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void action() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startCamera();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent,45);
            }
        });
    }

    private void startCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"new images");
        values.put(MediaStore.Images.Media.DESCRIPTION,"from camera");
        imagesUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT,imagesUri);
        startActivityForResult(camIntent,20);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==20 && resultCode==RESULT_OK){
            Intent scan = new Intent(context,scanActivity.class);
            scan.setData(imagesUri);
            startActivity(scan);
        }
        if(requestCode==45 && resultCode==RESULT_OK){
            ArrayList<Uri> arrayList = new ArrayList<>();

            ClipData clipData = data.getClipData();
            // clipData decides weather select single or multi images
            if(clipData != null){
                for(int i =0; i <clipData.getItemCount();i++){
                    Uri multi = clipData.getItemAt(i).getUri();
                    arrayList.add(multi);
                    Intent intent = new Intent(context,multiPagesPdf.class);
                    intent.putParcelableArrayListExtra("multipleImage", arrayList);

                    startActivity(intent);

                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_ALL && permissions == PERMISSIONS){
            if(!hasPermissions(context,permissions)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        }
    }
    public static boolean hasPermissions(Context context, String[] permissions) {
        PERMISSIONS = permissions;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }
}