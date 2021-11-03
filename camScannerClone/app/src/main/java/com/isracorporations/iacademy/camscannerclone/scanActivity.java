package com.isracorporations.iacademy.camscannerclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class
scanActivity extends AppCompatActivity {
    ImageView imageView;
    Button edit;
    Context context;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        context = scanActivity.this;

        edit = (Button)findViewById(R.id.btnEdit);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageUri = getIntent().getData();
        imageView.setImageURI(imageUri);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DsPhotoEditorActivity.class);
                i.setData(imageUri);
                i.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "pics");
                startActivityForResult(i,45);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 45 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, arrayOutputStream);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", null);
                Uri newUri = Uri.parse(path);
                Intent i = new Intent(context, editedImage.class);
                i.setData(newUri);
                startActivity(i);

            }
        }
    }
