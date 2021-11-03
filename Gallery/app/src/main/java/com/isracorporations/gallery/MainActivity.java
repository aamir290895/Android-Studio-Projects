
package com.isracorporations.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private ImageView imageView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    Context context;
    private static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    int PERMISSION_ALL = 1;






    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context =MainActivity.this;
        imageView = (ImageView) findViewById(R.id.imageView);
        recyclerView = (RecyclerView) findViewById(R.id.folderRecycler);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        if(hasPermissions(context,PERMISSIONS)){
            gallery();
        }else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        }



    }

    private ArrayList<File> findImage(File file) {
//        String imageUrls[] = {
//                "url1",
//                "url2",
//                "url3",

//                "url4"};

        ArrayList imageUrlList = new ArrayList<>();
        File[] imageFile = file.listFiles();
        for (File singleImage:imageFile) {
            if (singleImage.isDirectory() && !singleImage.isHidden()){
                imageUrlList.addAll(findImage(singleImage));
            }else {
                if (singleImage.getName().endsWith(".jpg")|| singleImage.getName().endsWith("png")){
                    imageUrlList.add(singleImage);
                }
            }
        }
//        for (int i = 0; i < imageUrls.length; i++) {
//            imageUrl imageUrl = new imageUrl();
//            imageUrl.setImageUrl(imageUrls[i]);
//            imageUrlList.add(imageUrl);
//        }
        Log.d("MainActivity", "List count: " + imageUrlList.size());
        return imageUrlList;
    }
//
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_ALL && permissions == PERMISSIONS){
            if(!hasPermissions(context,permissions)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }else {
                gallery();
            }
        }
    }

    private void gallery() {
        ArrayList<File> myImageFile = findImage(Environment.getExternalStorageDirectory());
        DataAdapter dataAdapter = new DataAdapter(getApplicationContext(),myImageFile);
        recyclerView.setAdapter(dataAdapter);


    }

    public static boolean hasPermissions(Context context,String[] permissions) {
        PERMISSIONS = permissions;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

}





