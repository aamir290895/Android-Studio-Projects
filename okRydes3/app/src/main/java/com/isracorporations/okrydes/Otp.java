package com.isracorporations.okrydes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {
    private OtpView otpView;
    FirebaseAuth auth;
    private String testVerificationCode,phone;
    PhoneAuthCredential credential;
    ProgressDialog dialog;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpView = findViewById(R.id.otp_view);
        auth = FirebaseAuth.getInstance();
        Button button = (Button)findViewById(R.id.login);
//        phn = getIntent().getStringExtra("abc");
        dialog= new ProgressDialog(this);
        dialog.setMessage("send otp....");
        dialog.setCancelable(false);
        phone = getIntent().getStringExtra("abc");
        sendOtp();

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                credential = PhoneAuthProvider.getCredential(testVerificationCode,otp);
                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Intent i = new Intent(Otp.this,Information.class);
                        startActivity(i);
                    }
                });

            }

        });





        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        sendOtp();
    }
});

        }


private void sendOtp(){
    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(Otp.this)
            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                      dialog.dismiss();

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    testVerificationCode = s;


                }
            }).build();
    PhoneAuthProvider.verifyPhoneNumber(options);

}

}

