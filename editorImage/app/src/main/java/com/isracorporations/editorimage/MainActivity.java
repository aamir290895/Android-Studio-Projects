package com.isracorporations.editorimage;

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
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.activity.DsPhotoEditorCropActivity;
import com.dsphotoeditor.sdk.activity.DsPhotoEditorStickerActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.isracorporations.editorimage.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

public class MainActivity extends AppCompatActivity {
    private static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    int PERMISSION_ALL = 1;
    Context context;
    File file;
    ActivityMainBinding binding;
    String currentPhotoPath;
    private  int REQ_IMAGES=20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = MainActivity.this;
        file = new File(Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg");



        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 200);
                }

        });
       binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,300);


            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_ALL && permissions == PERMISSIONS){
            if(!hasPermissions(context,permissions)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

     switch (requestCode) {
         case  300:

                    Uri inputUri = data.getData();
                    Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
                    dsPhotoEditorIntent.setData(inputUri);
                    dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "pics");
                    startActivityForResult(dsPhotoEditorIntent,100);

                    break;


          case 200:

                  Bundle extras = data.getExtras();
                  Bitmap imageBitmap = (Bitmap) extras.get("data");
                  Uri uri1 = getImageUri(imageBitmap);
                  Intent i = new Intent(this, DsPhotoEditorActivity.class);
                  i.setData(uri1);
                  i.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "pics");
                  startActivityForResult(i,45);

              break;



         case 100:
                Uri output = data.getData();

                Intent intent= new Intent(MainActivity.this ,save.class);
                intent.putExtra("uri",output);
                startActivity(intent);


         case 45:


             Uri output1 = data.getData();
             Intent intr= new Intent(MainActivity.this ,save.class);
             intr.putExtra("uri",output1);
             startActivity(intr);




             break;
         default:
             Toast.makeText(this,"something_wrong",Toast.LENGTH_LONG).show();

     }
               }

            }


        public Uri getImageUri ( Bitmap bitmap){

            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,arrayOutputStream);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"title",null);
            return Uri.parse(path);
        }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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
