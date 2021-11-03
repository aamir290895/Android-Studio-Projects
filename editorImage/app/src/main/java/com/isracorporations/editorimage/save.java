package com.isracorporations.editorimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class save extends AppCompatActivity {
    private String currentPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Button save = findViewById(R.id.button3);
        Bundle extras = getIntent().getExtras();
        Uri myUri = Uri.parse(String.valueOf(extras.get("uri")));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String fileName ="photo";
              File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
              try{
                  File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                  Uri imageUri =   FileProvider.getUriForFile(save.this,"com.example.android.fileprovider",imageFile);
                  currentPhoto = imageFile.getAbsolutePath();
                  Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                  startActivityForResult(intent,12);
              }catch (IOException e){
                  e.printStackTrace();
              }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 12  && resultCode==RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhoto);

        }
    }
}
