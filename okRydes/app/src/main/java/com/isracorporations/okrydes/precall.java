package com.isracorporations.okrydes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class precall extends AppCompatActivity {

    Button enter;
    EditText callerId,recipientId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precall);
        if (ContextCompat.checkSelfPermission(precall.this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(precall.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(precall.this,
                    new String[]{android.Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE},
                    1);
        }

        enter=(Button) findViewById(R.id.poy);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), call.class);
                intent.putExtra("callerId", ((EditText) findViewById(R.id.callerId)).getText().toString());
                intent.putExtra("recipientId", ((EditText) findViewById(R.id.recipientId)).getText().toString());
                startActivity(intent);
            }
        });
    }
}