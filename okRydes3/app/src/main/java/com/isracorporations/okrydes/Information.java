
package com.isracorporations.okrydes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Information extends AppCompatActivity {
    EditText eName,eAddress,eCity,ePin,eEmail;

    Button reg;
    FirebaseAuth auth;


    
    FirebaseUser firebaseUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        auth = FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        eName = (EditText)findViewById(R.id.name);
        eEmail =(EditText)findViewById(R.id.email);
        eAddress=(EditText)findViewById(R.id.address);
        eCity=(EditText)findViewById(R.id.city);
        ePin = (EditText)findViewById(R.id.pinCode);
        reg =(Button)findViewById(R.id.register);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String  name = eName.getText().toString().trim();
                final String  email=eEmail.getText().toString().trim();
                final String  address=eAddress.getText().toString().trim();
                final String city=eCity.getText().toString().trim();
                final String  pin=ePin.getText().toString().trim();
                Map<String, Object> user = new HashMap<>();
                user.put("name",name);
                user.put("email",email);
                user.put("address",address);

                user.put("city",city);
                user.put("pin",pin);
                db.collection(uid)
                      .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                             startActivity(new Intent(Information.this,MapsActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });



            }
    });

    }

}