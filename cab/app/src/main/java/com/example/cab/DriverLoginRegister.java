
package com.example.cab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginRegister extends AppCompatActivity {
    private Button driverLogin,register;
    private EditText email,password,phone;
    EditText otp;
    private FirebaseAuth mAuth;


    private FirebaseAuth.AuthStateListener firebaseAuthListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);
        driverLogin =(Button)findViewById(R.id.logy);
        email =(EditText)findViewById(R.id.emailDriver);
        password=(EditText)findViewById(R.id.passDriver);
        register = (Button)findViewById(R.id.btn4);
        mAuth = FirebaseAuth.getInstance();
        phone = (EditText)findViewById(R.id.phone);
        otp = (EditText)findViewById(R.id.otp);
        String phn = phone.getHint().toString();
        String motp = otp.getHint().toString();

        



        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(DriverLoginRegister.this,DriversMap.class);
                    startActivity(intent);
                    finish();
                    return;
                }else{
                    Toast.makeText(DriverLoginRegister.this,"lol",Toast.LENGTH_SHORT).show();
                }
            }
        };



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mEmail = email.getText().toString();
                final String mPassword = password.getText().toString();
                mAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(DriverLoginRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(DriverLoginRegister.this,"error",Toast.LENGTH_SHORT).show();
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
                mAuth.signInWithEmailAndPassword(nEmail,nPassword).addOnCompleteListener(DriverLoginRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(DriverLoginRegister.this, "error", Toast.LENGTH_SHORT).show();
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