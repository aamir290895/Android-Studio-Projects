package com.isracorporations.oketodelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
     FirebaseAuth auth;
     ProgressDialog dialog;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            startActivity(new Intent(MainActivity.this,MapsActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        auth= FirebaseAuth.getInstance();
        dialog= new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        EditText userName = (EditText)findViewById(R.id.userName);
        EditText pass = (EditText)findViewById(R.id.password_login);

        Button login = (Button)findViewById(R.id.sendOtp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String i1 = userName.getText().toString();
               String i2 = pass.getText().toString();
                dialog.show();

                auth.signInWithEmailAndPassword(i1,i2)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startActivity(new Intent(MainActivity.this,MapsActivity.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }
    }
