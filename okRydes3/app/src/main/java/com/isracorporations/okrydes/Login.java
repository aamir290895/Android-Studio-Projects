package com.isracorporations.okrydes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {
    CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        EditText phone = (EditText)findViewById(R.id.phoneNum);
        Button reqOtp = (Button)findViewById(R.id.sendOtp);
        ccp =(CountryCodePicker)findViewById(R.id.ccp);

        ccp.registerCarrierNumberEditText(phone);
        if(user != null){
            Intent i = new Intent(Login.this, MapsActivity.class);
            startActivity(i);

        }

        reqOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Otp.class);
                i.putExtra("abc",ccp.getFullNumberWithPlus());
                startActivity(i);
            }
        });
    }
}