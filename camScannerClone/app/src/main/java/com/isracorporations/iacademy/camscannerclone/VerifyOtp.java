package com.isracorporations.iacademy.camscannerclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyOtp extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    FirebaseAuth auth;
    private String testVerificationCode,phone;
    ProgressDialog dialog;
    EditText enterOtp;
    int RESOLVE_HINT = 105;
    CredentialsClient credentialsClient;
    MySMSBroadcastReceiver mySMSBroadcastReceiver;

    @Override
    protected void onStart() {
        super.onStart();
         sendOtp();
         broadCast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mySMSBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        enterOtp =(EditText)findViewById(R.id.enter_otp);
        auth = FirebaseAuth.getInstance();
        auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);

        Button button = (Button)findViewById(R.id.login);
        dialog= new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("send otp....");
        dialog.setCancelable(false);
        phone = getIntent().getStringExtra("abc");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               verify();
               dialog.show();
            }
        });
        ImageView textView= findViewById(R.id.resend_otp);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp();
                Toast.makeText(VerifyOtp.this,"otp send",Toast.LENGTH_LONG).show();
            }
        });


        Task<Void> task = SmsRetriever.getClient(VerifyOtp.this).startSmsUserConsent(phone);
    }



    private void broadCast(){
        mySMSBroadcastReceiver = new MySMSBroadcastReceiver();
        mySMSBroadcastReceiver.smsBroadcastReceiverListener = new MySMSBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent,RESOLVE_HINT);
            }

            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter= new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(mySMSBroadcastReceiver,intentFilter);

    }

    private void verify() {
        PhoneAuthCredential  credential = PhoneAuthProvider.getCredential(testVerificationCode,enterOtp.getText().toString());
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               dialog.dismiss();
               startActivity(new Intent(VerifyOtp.this,UserInformation.class));
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                String credential = data.getParcelableExtra(Credential.EXTRA_KEY);

                getOtp(credential);
            }
        }
    }

    private void getOtp(String credential){
        Pattern pattern = Pattern.compile("(\\d{6})");

        Matcher matcher = pattern.matcher(credential);
        String otp = "";
        while (matcher.find()) {
            otp = matcher.group(0);
        }
        enterOtp.setText(otp);
    }
    private void sendOtp(){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(VerifyOtp.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        dialog.dismiss();

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(VerifyOtp.this,"Code not Send" + phone,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        testVerificationCode = s;
                        Toast.makeText(VerifyOtp.this,"Code Sended",Toast.LENGTH_LONG).show();

                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}