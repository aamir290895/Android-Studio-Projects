
package com.isracorporations.oketobusiness;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Information extends AppCompatActivity {
    EditText eName,eEmail;

    Button reg;
    FirebaseAuth auth;


    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    Spinner pinCode;
    DatabaseReference db,forPin ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        auth = FirebaseAuth.getInstance();
        firebaseUser= auth.getCurrentUser();
        reg= findViewById(R.id.register);
        progressDialog = new ProgressDialog(Information.this);
        eName = (EditText)findViewById(R.id.name);
        eEmail =(EditText)findViewById(R.id.email);
        pinCode=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> pinAdapter = new ArrayAdapter<String>(Information.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.pin_code1));
        pinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pinCode.setAdapter(pinAdapter);

        db= FirebaseDatabase.getInstance().getReference().child("user_info").child(auth.getCurrentUser().getUid());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait.....");
        progressDialog.setCancelable(false);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String  name = eName.getText().toString().trim();
                final String  email=eEmail.getText().toString().trim();

                Map<String, Object> user = new HashMap<>();
                user.put("name",name);
                user.put("email",email);

                user.put("pin",pinCode.getSelectedItem().toString());
                db.child("s1").updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(Information.this,Index.class));
                        progressDialog.dismiss();
                    }
                });




            }
    });

    }

}