package com.isracorporations.oketomart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isracorporations.oketomart.ui.home.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserInformation extends AppCompatActivity {
    FirebaseAuth auth;
    String uid,pin;
    EditText name,email;
    ProgressDialog dialog;
    Spinner pincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        name=findViewById(R.id.name_info);
        email= findViewById(R.id.email_info);
        pincode=(Spinner)findViewById(R.id.spinner_pin);
        ArrayAdapter<String> pinAdapter = new ArrayAdapter<String>(UserInformation.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.pin_code));
        pinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pincode.setAdapter(pinAdapter);
        auth=FirebaseAuth.getInstance();
        uid= auth.getCurrentUser().getUid();
        dialog= new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait.....");
        dialog.setCancelable(false);

        Button reg = findViewById(R.id.register_info);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(UserInformation.this,"Please Enter Name",Toast.LENGTH_LONG).show();
                }else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user_info").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    dialog.show();
                    Map<String, Object> info = new HashMap<>();
                    info.put("name",name.getText().toString());
                    info.put("email",email.getText().toString());
                    info.put("pin", pincode.getSelectedItem().toString());

                    databaseReference.updateChildren(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(UserInformation.this, MainActivity.class));
                            dialog.dismiss();
                        }
                    });

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(getIntent());
    }
}