package com.isracorporations.okrydes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class verification extends AppCompatActivity {



    EditText phn;
    Button ent;
    CountryCodePicker ccp;


    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        phn = (EditText)findViewById(R.id.editTextMobile);
        ent =(Button)findViewById(R.id.buttonContinue);
        ccp =(CountryCodePicker)findViewById(R.id.ccp);

        ccp.registerCarrierNumberEditText(phn);


//        ccp.registerCarrierNumberEditText(phn);





        ent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(verification.this, otp.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus());
                startActivity(intent);

            }
        });


    }

}