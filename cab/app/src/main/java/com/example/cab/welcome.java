package com.example.cab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class welcome extends AppCompatActivity {
    Button userLogin;
    Button driverLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        driverLogin = (Button) findViewById(R.id.btn1);
        userLogin = (Button) findViewById(R.id.btn2);
        driverLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent loginDriver = new Intent(welcome.this,DriverLoginRegister.class);
             startActivity(loginDriver);
         }
     });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginUser = new Intent(welcome.this,CustomerLogin.class);
                startActivity(loginUser);
            }
        });
    }


}