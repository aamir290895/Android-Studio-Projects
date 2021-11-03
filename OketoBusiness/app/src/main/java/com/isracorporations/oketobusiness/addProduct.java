package com.isracorporations.oketobusiness;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class


addProduct extends AppCompatActivity {
    private  ImageView img;
    private EditText pName,pPrice,pCrossed,pQuantity,pWeight,pCostPrice,pDescription;
    Uri imagesUri;
    private static String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    int PERMISSION_ALL = 1;
    Context context;
    Task<Uri> taslUrl;
    ProgressDialog progressDialog;
    StorageReference storage;
    DatabaseReference database,forPin;
    Button save;
    String currentDate,currentTime,randomKey,imageUrl;
     String  name,price,crossed,quantity,weight,pinCode,costPrice,description;
     String extras,mainPath;
    String user;
    FirebaseAuth auth;
    TextView textView;
    Modal modal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        context= addProduct.this;
        textView = findViewById(R.id.tv_pin);
        if(!hasPermissions(context,PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        progressDialog = new ProgressDialog(context);
        img = findViewById(R.id.camera);
        pName =(EditText) findViewById(R.id.e1);
        pPrice = (EditText) findViewById(R.id.e2);
        pCrossed= (EditText) findViewById(R.id.e3);
        pQuantity = (EditText) findViewById(R.id.e4);
        pCostPrice=(EditText)findViewById(R.id.ex);
        pWeight=(EditText)findViewById(R.id.e5);
        pDescription = (EditText)findViewById(R.id.e_des);
        extras = getIntent().getExtras().getString("kit");
        mainPath  = getIntent().getStringExtra("group");
        auth = FirebaseAuth.getInstance();

        save = findViewById(R.id.save);
        user  = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storage= FirebaseStorage.getInstance().getReference().child("product");
        forPin = FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid()).child("s1");


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               gallery();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productInfo();

            }
        });
        forPin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modal = snapshot.getValue(Modal.class);
                pinCode = modal.getPin();
                textView.setText(pinCode);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void productInfo(){
         name = pName.getText().toString();
         crossed = pCrossed.getText().toString();
         price = pPrice.getText().toString();
         quantity = pQuantity.getText().toString();
         weight=pWeight.getText().toString();
         costPrice = pCostPrice.getText().toString();
         description= pDescription.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(context,"Fill details",Toast.LENGTH_LONG).show();

        }else if(TextUtils.isEmpty(crossed)){
            Toast.makeText(context,"Fill details",Toast.LENGTH_LONG).show();

        }else if(TextUtils.isEmpty(price)){
            Toast.makeText(context,"Fill details",Toast.LENGTH_LONG).show();

        }else if(TextUtils.isEmpty(quantity)){
            Toast.makeText(context,"Fill details",Toast.LENGTH_LONG).show();

        }else if(TextUtils.isEmpty(weight)){

            Toast.makeText(context,"Fill details",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(costPrice)) {
            Toast.makeText(context, "Fill details", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(description)){
            Toast.makeText(context, "Fill details", Toast.LENGTH_LONG).show();
        }
        else{
            storeProductInfo();
        }
    }
    private void gallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 45);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==45 && resultCode==RESULT_OK){
            imagesUri = data.getData();
            img.setImageURI(imagesUri);
        }

    }







    private void storeProductInfo(){
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait.....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,YYYY");
        currentDate= simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss a");
        currentTime= simpleTimeFormat.format(calendar.getTime());
        randomKey=currentDate +currentTime;
        StorageReference filePath = storage.child(imagesUri.getLastPathSegment()+randomKey);

        final UploadTask uploadTask = filePath.putFile(imagesUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message =e.toString();
                Toast.makeText(addProduct.this,"Error" +message ,Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                taslUrl = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }
                            imageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            imageUrl = task.getResult().toString();

                            save();
                        }
                    }
                });

            }
        });


    }


    private  void save(){

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("key", randomKey);
        hashMap.put("date", currentDate);
        hashMap.put("time", currentTime);
        hashMap.put("image", imageUrl);
        hashMap.put("price", price);
        hashMap.put("crossed", crossed);
        hashMap.put("weight", weight);
        hashMap.put("quantity", quantity);
        hashMap.put("costPrice",costPrice);
        hashMap.put("item", name);
        hashMap.put("group",mainPath);
        hashMap.put("subGroup" ,extras);
        hashMap.put("description",description);
        hashMap.put("pin" ,textView.getText().toString());
        database=FirebaseDatabase.getInstance().getReference().child(mainPath).child(extras).child(textView.getText().toString());

        database.child(randomKey).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(addProduct.this, Category.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                }
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