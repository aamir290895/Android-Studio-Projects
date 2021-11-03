package com.isracorporations.okrydes_driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private String VerficationId,phn;
    PhoneAuthCredential credential;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpView = findViewById(R.id.otp_view);
        auth = FirebaseAuth.getInstance();
        Button button = (Button)findViewById(R.id.login);
        phn = getIntent().getStringExtra("abc");
        sendOtp();


        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                credential = PhoneAuthProvider.getCredential(VerficationId,otp);
                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Intent i = new Intent(Otp.this,MapsActivity.class);
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

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {


    }
    private void sendOtp(){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phn)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(Otp.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        signInWithPhoneAuthCredential(credential);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        VerficationId = s;
                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

}