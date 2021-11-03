package com.example.mamito;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView back;
    TextView email;
    TextView password;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (TextView)findViewById(R.id.b1;
        password = (TextView)findViewById(R.id.password);
        login = (Button)findViewById(R.id.lgn);



    }
}