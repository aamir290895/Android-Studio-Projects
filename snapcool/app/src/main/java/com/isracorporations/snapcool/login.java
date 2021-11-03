package com.isracorporations.snapcool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class
login extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseAuth mAuth;
    private Button driverLogin,register;
    private EditText email,password;
    private FirebaseFirestore documentReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        driverLogin =(Button)findViewById(R.id.login);
        documentReference = FirebaseFirestore.getInstance();
        email =(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        register = (Button)findViewById(R.id.reg);
        User user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        mAuth = FirebaseAuth.getInstance();





        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(login.this,start.class);
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
                            documentReference.collection("users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
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