
package com.isracorporations.iacademy.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.isracorporations.iacademy.camera.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    int PERMISSION_ALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (hasPermissions(this,PERMISSIONS)){
            startCamera();
        }else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void startCamera() {
   binding.imageView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {

         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         startActivityForResult(intent, 200);
     }
 });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 200){
                Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();
                Bitmap photo = (Bitmap)data.getExtras().get("data");
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG,1000,arrayOutputStream);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), photo,"title",null);

                Uri uri = Uri.parse(path);
                Intent intent = new Intent(this,Result.class);
                intent.setData(uri);
                startActivity(intent);


            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_ALL && permissions == PERMISSIONS){
            if(!hasPermissions(this,permissions)){
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