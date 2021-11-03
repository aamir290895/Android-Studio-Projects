package com.isracorporations.ihubemploymentnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class login extends AppCompatActivity {
    private Button driverLogin,register;
    private EditText email,password;

    private FirebaseAuth mAuth;


    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            driverLogin =(Button)findViewById(R.id.login);
            email =(EditText)findViewById(R.id.emailDriver);
            password=(EditText)findViewById(R.id.passDriver);
            register = (Button)findViewById(R.id.register);
            mAuth = FirebaseAuth.getInstance();





            firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null){
                        Intent intent = new Intent(login.this,News.class);
                        startActivity(intent);
                        finish();
                        return;
                    }else{
                        Toast.makeText(login.this,"lol",Toast.LENGTH_SHORT).show();
                    }
                }
            };



            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String mEmail = email.getText().toString();
                    final String mPassword = password.getText().toString();
                    mAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(login.this,"error",Toast.LENGTH_SHORT).show();
                            }else {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user = FirebaseDatabase.getInstance().getReference();
                                current_user.setValue(true);
                            }
                        }
                    });
                }
            });

            driverLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String nEmail = email.getText().toString();
                    final String nPassword = password.getText().toString();
                    mAuth.signInWithEmailAndPassword(nEmail,nPassword).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(login.this, "error", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            });
        }

            @Override
            protected void onStart() {
            super.onStart();
            mAuth.addAuthStateListener(firebaseAuthListener);
        }

            @Override
            protected void onStop() {
            super.onStop();
            mAuth.removeAuthStateListener(firebaseAuthListener);


        }
        }

